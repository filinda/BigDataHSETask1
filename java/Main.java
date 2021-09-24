import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.BiConsumer;

import org.jfree.data.xy.XYSeries;

public class Main {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		printRamStat();
		
		FilenameFilter filter = new FilenameFilter() {
	        @Override
	        public boolean accept(File f, String name) {
	            return name.startsWith("user");
	        }
	    };
		
		File f = new File("../data/");
		String[] files = f.list(filter);
		
		Splitter splitter = new Splitter(10);
		Splitter splitter_2 = new Splitter(10);
		
		System.out.println(files.length);
		
		ArrayList<XYSeries> series = new ArrayList<XYSeries>();
		
		HashMap<Integer, Double[][]> dailyAvgs = new HashMap<Integer, Double[][]>();
				
		System.out.print("process: [");
		for(int z=0; z <files.length; z++) {
			System.out.print(" ");
		}
		System.out.print("]");
		System.out.println("");
		System.out.print("          ");
		
		for(int i = 0; i< files.length; i++) {
			
			System.out.print("*");
			
			int h = Integer.parseInt(files[i].split("\\.")[1].substring(1));
			int user = Integer.parseInt(files[i].split("\\.")[2].substring(1));
			
			if ((h==31||h==55||h==80||h==86)) {
			
			if (!dailyAvgs.containsKey(h)) {
				dailyAvgs.put(h, new Double[2][24*60*8]);
				for(int z=0; z< 24*60*8; z++) {
					dailyAvgs.get(h)[0][z]=0d;
					dailyAvgs.get(h)[1][z]=0d;
				}
			}
			
			MinuteMapper mapper = new MinuteMapper();
			AvgMinReducer reducer = new AvgMinReducer();
			AvgMoreMaxReducer reducer_2 = new AvgMoreMaxReducer();
			
			ResultData result1, result2;
			splitter.result = new ResultData();
			result1 = splitter.processFile("../data/"+files[i], 10000, mapper, reducer);
			
			
			
			splitter_2.result = result1;
			result2 = splitter_2.processFile("../data/"+files[i], 10000, mapper, reducer_2);
					
			result2.avgMoreMaxMinuteSpeed.forEach(new BiConsumer<Integer, Double>() {
				@Override
				public void accept(Integer t, Double u) {
					dailyAvgs.get(h)[0][t/60/24]+=1d;
					dailyAvgs.get(h)[1][t/60/24]+=u/result1.minuteSummAmount.get(t);
				}
			});
					
			
			}
		}
		
		dailyAvgs.forEach(new BiConsumer<Integer, Double[][]>() {

			@Override
			public void accept(Integer t, Double[][] u) {
				XYSeries curr_series2 = new XYSeries("h: "+t);
				for(int z=0; z< 60*24*8; z++) {
					if(u[0][z] != 0d) {
						curr_series2.add(z, u[1][z]/u[0][z]);
					}
				}
				series.add(curr_series2);
			}
			
		});
		
		ChartUI2 demo = new ChartUI2("Internet speed", "AvgMinuteSpeed ",series);
        demo.pack();
        demo.setVisible(true);
	
		
	}
	
	public static void printRamStat() {
		Runtime runtime = Runtime.getRuntime();

		NumberFormat format = NumberFormat.getInstance();

		StringBuilder sb = new StringBuilder();
		long maxMemory = runtime.maxMemory();
		long allocatedMemory = runtime.totalMemory();
		long freeMemory = runtime.freeMemory();

		System.out.println("free memory: " + format.format(freeMemory / 1024));
		System.out.println("allocated memory: " + format.format(allocatedMemory / 1024));
		System.out.println("max memory: " + format.format(maxMemory / 1024));
		System.out.println("total free memory: " + format.format((freeMemory + (maxMemory - allocatedMemory)) / 1024));
		System.out.println("cores: "+runtime.availableProcessors());
	}

}
