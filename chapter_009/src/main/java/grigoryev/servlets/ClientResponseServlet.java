package grigoryev.servlets;

import org.slf4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Provides result respond to the client.
 * Writes updated database.
 *
 * @author vgrigoryev
 * @version 1
 * @since 08.11.2017
 */
public class ClientResponseServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Logger logger = (Logger) this.getServletContext().getAttribute("appLogger");
        try {
            List<User> users = UserStorage.USER_STORAGE.selectAll();
            req.setAttribute("users", users);
            RequestDispatcher view = req.getRequestDispatcher("/WEB-INF/views/userJSP.jsp");
            view.forward(req, resp);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
