import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Snap on 06.11.2015.
 */
@WebServlet("/login")
public class TryLogin extends HttpServlet{
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException,ServletException
    {
        JsonReader reader = Json.createReader(req.getInputStream());
        JsonObject jsonObj = reader.readObject();
        reader.close();

        jsonObj = jsonObj.getJsonObject("auth");

        String username = null;
        String password = null;

        if (jsonObj.containsKey("login"))
        {
            username = jsonObj.getString("login");
        }
        if (jsonObj.containsKey("password"))
        {
            password = jsonObj.getString("password");
        }

        resp.setContentType("text/html; charset=UTF-8");

        PrintWriter out = resp.getWriter();

        DatabaseHelper databaseHelper = new DatabaseHelper();

        out.println(databaseHelper.checkUser(username, password));

        /*if(username.equals("master") && password.equals("master"))
        {
            out.println("Welcome, master!");
        } else
        {
            out.println("Welcome, simple user");
        }
        */
    }
}
