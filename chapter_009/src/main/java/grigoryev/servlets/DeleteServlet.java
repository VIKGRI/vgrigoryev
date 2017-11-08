package grigoryev.servlets;

import org.slf4j.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Provides deletion of user from database.
 * After that forwards request to servlet which
 * forms result respond.
 *
 * @author vgrigoryev
 * @since 08.11.2017
 * @version 1
 */
public class DeleteServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getAttribute("user");
        Logger logger = (Logger) this.getServletContext().getAttribute("appLogger");
        String result = "Deletion error";
        try {
            result = UserStorage.USER_STORAGE.deleteUser(user);
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        req.setAttribute("operationResult", result);

        RequestDispatcher dispatcher = req.getRequestDispatcher("/ClientResponse");
        dispatcher.forward(req, resp);
    }
}
