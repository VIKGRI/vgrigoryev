package grigoryev.servlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Provides selection of all users from database.
 * After that forwards request to servlet which
 * forms result respond.
 *
 * @author vgrigoryev
 * @since 08.11.2017
 * @version 1
 */
public class SelectAllServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setAttribute("operationResult", "All users in the database");

        RequestDispatcher dispatcher = req.getRequestDispatcher("/ClientResponse");
        dispatcher.forward(req, resp);
    }
}
