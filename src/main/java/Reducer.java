import java.util.ArrayList;
import java.util.HashMap;

public interface Reducer {
	HashMap reduce(ArrayList<HashMap> maped, ResultData data); 
}
