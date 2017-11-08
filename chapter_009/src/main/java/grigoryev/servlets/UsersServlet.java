package grigoryev.servlets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

/**
 * Servlet which provides methods
 * for interacting with database which
 * contains users.
 *
 * @author vgrigoryev
 * @version 1
 * @since 02.11.2017
 */
public class UsersServlet extends HttpServlet {
    /**
     * Servlet logger.
     */
    private static final Logger LOG = LoggerFactory.getLogger(UsersServlet.class);
    /**
     * Provides managing of user database.
     */
    private UserDataBaseManager dataManager;

    @Override
    public void init() throws ServletException {
        this.dataManager = new UserDataBaseManager();
        try {
            this.dataManager.connectDataBase();
            this.dataManager.createTable();
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        } catch (ClassNotFoundException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    @Override
    public void destroy() {
        try {
            this.dataManager.closeConnection();
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    /**
     * Writes to the HttpResponse html for initial form
     * and result of operation.
     *
     * @param resp HttpResponse
     * @param operationResult operation result
     */
    public void writeResponse(HttpServletResponse resp, String operationResult) {
        try {
            resp.setContentType("text/html");
            PrintWriter writer = resp.getWriter();
            writer.println("<html>" +
                    "<head>" +
                    "    <title>User Managing Page</title>" +
                    "</head>\n" +
                    "<body>\n" +
                    "<h1 align=\"center\">User Managing Page</h1>" +
                    "<form method=\"post\"" +
                    "      action=\"PerformUserRequest\">" +
                    "    Select type of action\n" +
                    "    <p>" +
                    "        Name:<br>\n" +
                    "        <input type=\"text\" name=\"name\"><br>" +
                    "        Login:<br>\n" +
                    "        <input type=\"text\" name=\"login\"><br>" +
                    "        Email:<br>\n" +
                    "        <input type=\"text\" name=\"email\"><br>" +
                    "        Action:\n" +
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
            if (operationResult != null) {
                writer.println(operationResult);
            }
        } catch (IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String login = req.getParameter("login");
        String email = req.getParameter("email");
        String actionType = req.getParameter("actionType");

        String operationResult = new ActionDispatcher(this.dataManager, this.LOG).init().perform(
                ActionDispatcher.toAction(actionType),
                new User(name, login, email, System.currentTimeMillis())
        );
        this.writeResponse(resp, operationResult);
    }
}
