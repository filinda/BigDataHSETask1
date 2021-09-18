import java.util.ArrayList;
import java.util.HashMap;

public class AvgMinReducer implements Reducer{

	@Override
	synchronized public HashMap reduce(ArrayList<HashMap> maped, ResultData data) {
		for(int i=0; i< maped.size(); i++) {
			if (data.minuteSummSpeed.containsKey((int)maped.get(i).get("minute"))) {
				Double currValSpeed = data.minuteSummSpeed.get((int)maped.get(i).get("minute"));
				Integer currValAmount = data.minuteSummAmount.get((int)maped.get(i).get("minute"));
				
				data.minuteSummSpeed.put((int)maped.get(i).get("minute"),currValSpeed+(Double)maped.get(i).get("speed"));
				data.minuteSummAmount.put((int)maped.get(i).get("minute"),currValAmount+1);
				
				if((Double)maped.get(i).get("speed")>data.maxMinuteSpeed.get((int)maped.get(i).get("minute"))) {
					data.maxMinuteSpeed.put((int)maped.get(i).get("minute"),(Double)maped.get(i).get("speed"));
				}
				if((Double)maped.get(i).get("speed")<data.minMinuteSpeed.get((int)maped.get(i).get("minute"))) {
					data.minMinuteSpeed.put((int)maped.get(i).get("minute"),(Double)maped.get(i).get("speed"));
				}
				
			}else {
				data.minuteSummSpeed.put((int)maped.get(i).get("minute"),(Double)maped.get(i).get("speed"));
				data.minuteSummAmount.put((int)maped.get(i).get("minute"),1);
				data.maxMinuteSpeed.put((int)maped.get(i).get("minute"),(Double)maped.get(i).get("speed"));
				data.minMinuteSpeed.put((int)maped.get(i).get("minute"),(Double)maped.get(i).get("speed"));
			}
		}
		
		return null;
	}

}
