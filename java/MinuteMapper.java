import java.util.ArrayList;
import java.util.HashMap;

public class MinuteMapper implements Mapper{

	@Override
	public ArrayList<HashMap> map(Object dtata) {
		String[] strData = ((String[])dtata);
		ArrayList<HashMap> result = new ArrayList<HashMap>();
		for(int i=0; i < strData.length; i++) {
			HashMap<String, Object> line = new HashMap<String, Object>();
			int minute = (int)(Double.parseDouble(strData[i].split(",",3)[1])/60) + 1440*(Integer.parseInt(strData[i].split(",",3)[0]));
			Double speed = Double.parseDouble(strData[i].split(",",3)[2]);
			line.put("minute", minute);
			line.put("speed", speed);
			result.add(line);
		}
		
		return result;
	}

}
