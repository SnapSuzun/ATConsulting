import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Snap on 16.11.2015.
 */
public class Main {
    public static void main(String[] args){
        DatabaseHelper databaseHelper = new DatabaseHelper();

        System.out.println(databaseHelper.checkUser("demonlord32", "1234"));
    }
}
