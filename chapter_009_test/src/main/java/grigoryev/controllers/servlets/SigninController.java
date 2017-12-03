package grigoryev.controllers.servlets;

import grigoryev.dao.DatabaseDAOException;
import grigoryev.dao.implement.UserDataBaseDao;
import grigoryev.dao.repository.implement.GetUserByLoginSpecification;
import grigoryev.models.Role;
import grigoryev.models.User;
import org.slf4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Provides user's authentication.
 *
 * @author vgrigoryev
 * @version 1
 * @since 16.11.2017
 */
public class SigninController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/LoginView.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        Logger logger = (Logger) req.getServletContext().getAttribute("appLogger");
        try {
            if (UserDataBaseDao.getInstance().isCredentional(login, password)) {
                req.getSession().setAttribute("login", login);
                req.getSession().setAttribute("role",
                        (UserDataBaseDao.getInstance().query(new GetUserByLoginSpecification(login))).get(0).getRole());
                Role role
                        = (UserDataBaseDao.getInstance().query(new GetUserByLoginSpecification(login))).get(0).getRole();


                resp.sendRedirect(String.format("%s/", req.getContextPath()));
            } else {
                req.setAttribute("error", "Credentional invalid");
                doGet(req, resp);
            }
        } catch (DatabaseDAOException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
