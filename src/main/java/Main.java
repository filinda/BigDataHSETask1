import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

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
		
		//System.out.println(String.format("for h="+1+" u="+1+" avg speed = %.2f",2.3d));
		
		Splitter splitter = new Splitter(5);
		
		System.out.println(files.length);
		
		for(int i = 0; i< files.length; i++) {
			int h = Integer.parseInt(files[i].split("\\.")[1].substring(1));
			int u = Integer.parseInt(files[i].split("\\.")[2].substring(1));
			
			if (u==1) {
			
			MinuteMapper mapper = new MinuteMapper();
			AvgMinReducer reducer = new AvgMinReducer();
			
			ResultData result;
			result = splitter.processFile("../data/"+files[i], 10000, mapper, reducer);
			
			ArrayList<Double> x = new ArrayList<Double>();
			ArrayList<Double> y = new ArrayList<Double>();
			
			ArrayList<Double> maxx = new ArrayList<Double>();
			ArrayList<Double> maxy = new ArrayList<Double>();
			
			ArrayList<Double> minx = new ArrayList<Double>();
			ArrayList<Double> miny = new ArrayList<Double>();
			
			result.minuteSummSpeed.forEach(new BiConsumer<Integer, Double>() {
				@Override
				public void accept(Integer t, Double u) {
					// TODO Auto-generated method stub
					x.add((double)t/60 -24);
					y.add(u/result.minuteSummAmount.get(t));
					
					maxx.add((double)t/60 -24);
					maxy.add(result.maxMinuteSpeed.get(t));
					
					minx.add((double)t/60 -24);
					miny.add(result.minMinuteSpeed.get(t));
				}
			});
			
			
			ChartUI demo = new ChartUI("Comparison", "AvgMinuteSpeed h:"+h+" u:"+u,x.toArray(new Double[0]),y.toArray(new Double[0]), maxx.toArray(new Double[0]),maxy.toArray(new Double[0]), minx.toArray(new Double[0]),miny.toArray(new Double[0]));
	        demo.pack();
	        demo.setVisible(true);
			/*
			Stream<String> rows1 = Files.lines(Paths.get("../data/"+files[i]));
			Double multi = rows1
				.filter(new Predicate<String>() {
					@Override
					public boolean test(String t) {
						try {
							Double.parseDouble(t.split(",",3)[2]);
							return true;
						}catch(Exception e){
							return false;
						}
					}
				})
				.map(
						new Function<String, Double>() {
							public Double apply(String t) {
								return Double.parseDouble(t.split(",",3)[2]);
							};
						}
				)
				.reduce(new BinaryOperator<Double>() {
					@Override
					public Double apply(Double t, Double u) {
						return (t+u)/2;
					}
				}).get();
			System.out.println(String.format("%02d %02d %015.04f",h,u,multi));
			rows1.close();*/
			}
		}
	
		
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
