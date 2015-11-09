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
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException,ServletException
    {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        resp.setContentType("text/html; charset=UTF-8");

        PrintWriter out = resp.getWriter();
        if(username.equals("master") && password.equals("master"))
        {
            out.println("Welcome, master!");
        } else
        {
            out.println("Welcome, simple user");
        }
    }
}
