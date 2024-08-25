import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MonthlyReport {
    private static Map<String, ArrayList<String>> map = new HashMap<>();

    public static void put(String m , ArrayList<String> str){
        map.put(m,str);
    }
    public static ArrayList<String> get(int m){
        return map.get(m);
    }
    public static Map<String,ArrayList<String>> getMap(){
        return map;
    }

}
