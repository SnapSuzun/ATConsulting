import javax.json.JsonObject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Created by Snap on 25.11.2015.
 */

@WebServlet("/get_records")
public class GetRecordsFromBase extends HttpServlet {

    public void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException,ServletException
    {
        try {
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();

            DatabaseHelper databaseHelper = new DatabaseHelper();
            List users = databaseHelper.getAllRecords();

            JSONArray ar = new JSONArray();
            JSONObject resultJson = new JSONObject();

            for (Iterator iterator = users.iterator(); iterator.hasNext(); ) {
                MyTable record = (MyTable) iterator.next();

                JSONObject obj = new JSONObject();
                obj.put("id", record.getId());
                obj.put("fio", record.getLast_name() + " " + record.getFirst_name());
                obj.put("gender", record.getGender());
                obj.put("birthDate", record.getBirthDate());
                obj.put("phone", record.getPhone());

                ar.add(obj);

            }

            resultJson.put("documents", ar);

            out.println(resultJson.toJSONString());
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*private JSONObject createJSONObjectForRecord(MyTable record)
    {
        JSONObject obj = new JSONObject();
        obj.put("id", record.getId());
        obj.put("fio", record.getLast_name() + " " + record.getFirst_name());
        obj.put("gender", record.getGender());
        obj.put("birthDate", record.getBirthDate());
        obj.put("phone", record.getPhone());
        return obj;
    }
    */
}