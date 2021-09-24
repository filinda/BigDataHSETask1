import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.function.BiConsumer;

public class Splitter {
	
	NotifyingThread[] treads;
	ResultData result;
	String currentDataChunk;
	private BufferedReader in;
	
	public Splitter(int numOfThreads) {
		treads = new NotifyingThread[numOfThreads];
		for(int i=0; i< treads.length; i++) {
			treads[i] = new NotifyingThread();
		}
	}
	
	public ResultData processFile(String filename, int numOfRowsPerTread, Mapper mapper, Reducer reducer) {
		
		for(int i=0; i< treads.length; i++) {
			treads[i].data = new String[numOfRowsPerTread];
			treads[i].mapper = mapper;
			treads[i].reducer = reducer;
		}
		
		try {
			in = new BufferedReader(new FileReader(filename));
			String currentLine=in.readLine();
			int bufPos = 0;
			boolean eof = false;
			
			while(!eof) {
				
				for(int i=0; i< treads.length; i++) {
					treads[i] = new NotifyingThread();
					treads[i].data = new String[numOfRowsPerTread];
					treads[i].mapper = mapper;
					treads[i].reducer = reducer;
				}
				
				for(int i=0; i< treads.length; i++) {
					bufPos = 0;
					while(bufPos<numOfRowsPerTread) {
						currentLine=in.readLine();
						if(currentLine == null) {
							eof = true;
							((String[])treads[i].data)[bufPos] = "0,0,0";
						}else {
							((String[])treads[i].data)[bufPos] = currentLine;
						}
						bufPos++;
					}
					
					treads[i].start();
				}
				
				for(int i=0; i< treads.length; i++) {
					treads[i].join();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		return result;
	}
	
	public interface ThreadCompleteListener {
	void notifyOfThreadComplete(final Thread thread);
	}
	
	public class NotifyingThread extends Thread {
		private Set<ThreadCompleteListener> listeners = new CopyOnWriteArraySet<ThreadCompleteListener>();

		public void addListener(final ThreadCompleteListener listener) {
			listeners.add(listener);
		}

		public void removeListener(final ThreadCompleteListener listener) {
			listeners.remove(listener);
		}

		private void notifyListeners() {
			for (ThreadCompleteListener listener : listeners) {
				listener.notifyOfThreadComplete(this);
			}
		}
		
		Mapper mapper;
		Reducer reducer;
		Object data;
		
		@Override
		public void run() {
			try {
				ArrayList<HashMap> mapped = mapper.map(data);
				reducer.reduce(mapped, result);
			} finally {
				notifyListeners();
			}
			super.run();
		}
	}
	
}
