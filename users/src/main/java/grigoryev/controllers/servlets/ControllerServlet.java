package grigoryev.controllers.servlets;

import grigoryev.controllers.ActionDispatcher;
import grigoryev.dao.DatabaseDAOException;
import grigoryev.dao.implement.UserDataBaseDao;
import grigoryev.models.*;
import org.slf4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
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
        try {
            List<User> users = UserDataBaseDao.getInstance().selectAll();
            req.setAttribute("users", users);
            RequestDispatcher view = req.getRequestDispatcher("/WEB-INF/views/usersView.jsp");
            view.forward(req, resp);
        } catch (DatabaseDAOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Logger logger = (Logger) req.getServletContext().getAttribute("appLogger");
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String login = req.getParameter("login");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String actionType = req.getParameter("actionType");
        String role = req.getParameter("role").trim().toLowerCase();
        String streetName = req.getParameter("street_name").trim().toLowerCase();
        String houseNumber = req.getParameter("house_no").trim().toLowerCase();
        String apartmentNumber = req.getParameter("apartment_no").trim().toLowerCase();

        User user = new User();
        if (id != "") {
                user.setId(Integer.valueOf(id));
        }
        user.setName(name);
        user.setLogin(login);
        user.setEmail(email);
        user.setPassword(password);
        RoleContainer roles = RoleContainer.getInstance();
        user.setRole(roles.getRole(role));

        Address address = new Address();
        address.setStreetName(streetName);
        address.setHouseNo(houseNumber);
        address.setApartmentNo(apartmentNumber);
        user.setAddress(address);

        List<MusicType> musicTypes = new ArrayList<>();
        List<String> musicTypeNames = MusicTypeContainer.getInstance().getMusicTypeNames();
        for (String type : musicTypeNames) {
            String userChoice = req.getParameter(String.format("%s", type));
            if (userChoice != null) {
                musicTypes.add(MusicTypeContainer.getInstance().getMusicType(userChoice));
            }
        }

        user.setMusicTypes(musicTypes);

        Role signedInUserRole = (Role) req.getSession().getAttribute("role");

        List<User> users =
                new ActionDispatcher(logger, user, signedInUserRole).init().perform(ActionDispatcher.toAction(actionType));

        req.setAttribute("users", users);

        RequestDispatcher view = req.getRequestDispatcher("/WEB-INF/views/usersView.jsp");

        view.forward(req, resp);
    }
}
