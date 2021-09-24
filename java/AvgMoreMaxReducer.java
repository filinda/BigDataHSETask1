import java.util.ArrayList;
import java.util.HashMap;

public class AvgMoreMaxReducer implements Reducer{

	@Override
	synchronized public HashMap reduce(ArrayList<HashMap> maped, ResultData data) {
		for(int i=0; i< maped.size(); i++) {
			
			Double curr_speed = (Double)maped.get(i).get("speed");
			int curr_minute = (int)maped.get(i).get("minute");
			Double prev_speed_summ = data.minuteSummSpeed.get(curr_minute);
			int prev_speed_amount = data.minuteSummAmount.get(curr_minute);
			Double prev_avg_speed = (data.maxMinuteSpeed.get(curr_minute)+data.minMinuteSpeed.get(curr_minute))/2 + (data.maxMinuteSpeed.get(curr_minute)-data.minMinuteSpeed.get(curr_minute))*0.5d;/*(prev_speed_summ/(double)prev_speed_amount);*/
		
			data.avgMoreMaxMinuteSpeed.get(curr_minute);
			
			if(!(curr_speed<prev_avg_speed)) {
				if (data.avgMoreMaxMinuteSpeed.containsKey(curr_minute)) {
					
					data.avgMoreMaxMinuteSpeed.put(curr_minute, data.avgMoreMaxMinuteSpeed.get(curr_minute)+curr_speed);
					data.avgMoreMaxMinuteAmount.put(curr_minute, data.avgMoreMaxMinuteAmount.get(curr_minute)+1);
					
				}else {
					data.avgMoreMaxMinuteSpeed.put(curr_minute,curr_speed);
					data.avgMoreMaxMinuteAmount.put(curr_minute,1);
				}
			}
			
			
		}
		
		return null;
	}

}
