package grigoryev.servlets;

import org.slf4j.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

/**
 * Provides result respond to the client.
 * Writes updated database.
 *
 * @author vgrigoryev
 * @since 08.11.2017
 * @version 1
 */
public class ClientResponseServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Logger logger = (Logger) this.getServletContext().getAttribute("appLogger");
        List<User> users = null;
        try {
            users = UserStorage.USER_STORAGE.selectAll();
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        PrintWriter writer = resp.getWriter();
        resp.setContentType("text/html");
        String operationResult = (String) req.getAttribute("operationResult");
        writer.println("<html>" +
                "<head>" +
                "    <title>User Managing Page</title>" +
                "</head>" +
                "<body>" +
                "<h1 align=\"center\">User Managing Page</h1>" +
                "<form method=\"post\"" +
                "      action=\"Dispatch\">" +
                "    Select type of action" +
                "    <p>" +
                "        Name:<br>" +
                "        <input type=\"text\" name=\"name\"><br>" +
                "        Login:<br>" +
                "        <input type=\"text\" name=\"login\"><br>" +
                "        Email:<br>" +
                "        <input type=\"text\" name=\"email\"><br>" +
                "        Action:" +
                "        <select name=\"actionType\" size=\"1\">" +
                "            <option value=\"insert\">INSERT</option>" +
                "            <option value=\"update\">UPDATE</option>" +
                "            <option value=\"delete\">DELETE</option>" +
                "            <option value=\"select\">SELECT</option>" +
                "            <option value=\"selectAll\">SELECT ALL</option>" +
                "        </select>" +
                "        <br><br>" +
                "    <div style=\"text-align: center;\">" +
                "        <input type=\"submit\">" +
                "    </div>" +
                "    </p>" +
                "</form>" +
                "</body>" +
                "</html>");
        writer.println(operationResult + "<br><br>");

        if (users != null) {
            writer.println("<table style=\"width:100%\">");
            writer.println("<tr>"
                    + "    <th>name</th>" + "    <th>login</th>"
                    + "    <th>email</th>" + "    <th>create date</th>" + "  </tr>");
            for (User item : users) {
                writer.println("<tr>"
                        + "    <th>" + item.getName() + "</th>" + "<th>" + item.getLogin() + "</th>"
                        + "    <th>" + item.getEmail() + "</th>" + "<th>" + item.getCreateDate() + "</th>" + "  </tr>");
            }
        } else {
            writer.println("No users");
        }
    }
}
