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
 * Provides selection of user by login from database.
 * After that forwards request to servlet which
 * forms result respond.
 *
 * @author vgrigoryev
 * @since 08.11.2017
 * @version 1
 */
public class SelectByLoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getAttribute("user");
        Logger logger = (Logger) this.getServletContext().getAttribute("appLogger");

        User findUser = null;
        try {
            findUser = UserStorage.USER_STORAGE.selectByLogin(user.getLogin());
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }
        StringBuilder builder = new StringBuilder();
        builder.append("<table style=\"width:100%\">");
        builder.append("<tr>"
                + "    <th>name</th>" + "    <th>login</th>"
                + "    <th>email</th>" + "    <th>create date</th>" + "  </tr>");
        if (findUser != null) {
            builder.append("<tr>"
                    + "    <th>" + findUser.getName() + "</th>" + "<th>" + findUser.getLogin() + "</th>"
                    + "    <th>" + findUser.getEmail() + "</th>" + "<th>" + findUser.getCreateDate() + "</th>" + "  </tr>");
            builder.append("<br><br><br>");
        } else {
            builder.append("User is not found");
        }

        req.setAttribute("operationResult", builder.toString());

        RequestDispatcher dispatcher = req.getRequestDispatcher("/ClientResponse");
        dispatcher.forward(req, resp);
    }
}
