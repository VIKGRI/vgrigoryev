package grigoryev.servlets;

import org.slf4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Provides dispatching to the right servlet
 * depending on kind of action the form supplies.
 *
 * @author vgrigoryev
 * @version 1
 * @since 08.11.2017
 */
public class ControllerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Logger logger = (Logger) req.getServletContext().getAttribute("appLogger");
        req.setAttribute("operationResult", "All users in the database");
        try {
            List<User> users = UserStorage.USER_STORAGE.selectAll();
            req.setAttribute("users", users);
            RequestDispatcher view = req.getRequestDispatcher("/WEB-INF/views/usersView.jsp");
            view.forward(req, resp);
        } catch (UserStorageDAOException e) {
            logger.error(e.getMessage(), e);
        }
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String login = req.getParameter("login");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String actionType = req.getParameter("actionType");
        String role = req.getParameter("role");
        Roles roles = Roles.getInstance();
        User user = new User(name, login, email, System.currentTimeMillis(), password, roles.getRole(role));

        req.setAttribute("user", user);

        Logger logger = (Logger) req.getServletContext().getAttribute("appLogger");

        //new ServletActionDispatcher(req, resp, logger).init().perform(ActionDispatcher.toAction(actionType));
        HttpSession session = req.getSession();
        Role signedInUserRole;
        synchronized (session) {
            signedInUserRole = (Role) session.getAttribute("role");
        }

        String operationResult =
                new Model(logger, user, signedInUserRole).init().perform(ActionDispatcher.toAction(actionType));
        req.setAttribute("operationResult", operationResult);

        try {
            List<User> users = UserStorage.USER_STORAGE.selectAll();
            req.setAttribute("users", users);
            RequestDispatcher view = req.getRequestDispatcher("/WEB-INF/views/usersView.jsp");
            view.forward(req, resp);
        } catch (UserStorageDAOException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
