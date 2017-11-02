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
import java.util.ArrayList;
import java.util.List;

/**
 * Servlet which provides methods
 * for interacting with database which
 * contains users.
 *
 *@author vgrigoryev
 *@since 02.11.2017
 *@version 1
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

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String actionType = req.getParameter("actionType");
        List<User> result = null;
        try {
            if (actionType.equals("selectAll")) {
                result = this.dataManager.selectAll();
            } else {
                User user = this.dataManager.selectByLogin(login);
                if (user != null) {
                    result = new ArrayList<>();
                    result.add(user);
                }
            }
            resp.setContentType("text/html");
            PrintWriter writer = resp.getWriter();
            if (result != null) {
                writer.println("<table style=\"width:100%\">");
                writer.println("<tr>"
                        + "    <th>name</th>" + "    <th>login</th>"
                        + "    <th>email</th>" + "    <th>create date</th>" + "  </tr>");
                for (User item : result) {
                    writer.println("<tr>"
                            + "    <th>" + item.getName() + "</th>" + "<th>" + item.getLogin() + "</th>"
                            + "    <th>" + item.getEmail() + "</th>" + "<th>" + item.getCreateDate() + "</th>" + "  </tr>");
                }
            } else {
                writer.println("User is not found");
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String login = req.getParameter("login");
        String email = req.getParameter("email");
        String actionType = req.getParameter("actionType");
        try {
            switch (actionType) {
                case "insert":
                    this.dataManager.insertUser(new User(name, login, email, System.currentTimeMillis()));
                    resp.setContentType("text/html");
                    PrintWriter writer = resp.getWriter();
                    writer.println("User is added");
                    break;
                case "update":
                    this.doPut(req, resp);
                    break;
                case "delete":
                    this.doDelete(req, resp);
                    break;
                case "select":
                case "selectAll":
                    this.doGet(req, resp);
                    break;
                default:
                    LOG.info("No action!");
                    break;
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String login = req.getParameter("login");
        String email = req.getParameter("email");
        try {
            this.dataManager.updateUser(new User(name, login, email, 0L));
            resp.setContentType("text/html");
            PrintWriter writer = resp.getWriter();
            writer.println("User " + login + " is updated");
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        try {
            this.dataManager.deleteUser(new User("name", login, "email", 0L));
            resp.setContentType("text/html");
            PrintWriter writer = resp.getWriter();
            writer.println("User " + login + " is deleted");
        } catch (SQLException e) {
            LOG.error(e.getMessage(), e);
        }
    }
}
