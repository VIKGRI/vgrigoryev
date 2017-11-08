package grigoryev.servlets;

import org.slf4j.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Provides dispatching to the right servlet
 * depending on kind of action the form supplies.
 *
 * @author vgrigoryev
 * @since 08.11.2017
 * @version 1
 */
public class DispatcherServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String login = req.getParameter("login");
        String email = req.getParameter("email");
        String actionType = req.getParameter("actionType");

        req.setAttribute("user", new User(name, login, email, System.currentTimeMillis()));
        Logger logger = (Logger) req.getServletContext().getAttribute("appLogger");
        new ServletActionDispatcher(req, resp, logger).init().perform(ActionDispatcher.toAction(actionType));
    }
}
