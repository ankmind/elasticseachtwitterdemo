package utils;

import com.google.gson.Gson;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

/**
 * Created by ganku on 15/03/17.
 */
public class JSONHelper {


    //list to JSON
    static Gson gson=new Gson();
    public static <T> String getJsonFromList(List<T> list){
        return gson.toJson(list);
    }


    public static String getIfPresent(JSONObject jsonObject,String key){
        try {
           return jsonObject.getString(key);
        }
        catch (Exception e){
            //System.out.println(e.toString());
            return null;
        }
    }
}
