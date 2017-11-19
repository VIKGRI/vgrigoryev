package grigoryev.servlets;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static org.junit.Assert.*;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Provides testing for ControllerServlet's methods
 * to operate on users in a database.
 *
 * @author vgrigoryev
 * @version 1
 * @since 19.11.2017
 */
public class ControllerServletTest {
    /**
     * Initialize data source. In application to perform data base connection
     * ServletContextListener is used. So to test ControllerServlet directly it's needed
     * to supply data base connection.
     */
    private void initApp() {
        Logger appLogger = LoggerFactory.getLogger(UsersServletContextListener.class);

        try {
            Roles roles = Roles.getInstance();
            UserStorage.USER_STORAGE_TEST.insertUser(new User("root", "root", "root@mail.ru",
                    System.currentTimeMillis(), "root", roles.getRole("admin")));
        } catch (UserStorageDAOException e) {
            appLogger.error(e.getMessage(), e);
        }
    }

    /**
     * Test adding user to the database.
     * @throws ServletException servlet exception
     * @throws IOException io exception
     * @throws UserStorageDAOException thrown if problems with database occur
     */
    @Test
    public void addUser() throws ServletException, IOException, UserStorageDAOException {
        this.initApp();
        ControllerServlet controller = new ControllerServlet();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getParameter("name")).thenReturn("Victor");
        when(request.getParameter("login")).thenReturn("Vic");
        when(request.getParameter("email")).thenReturn("Victor@mail.ru");
        when(request.getParameter("password")).thenReturn("123");
        when(request.getParameter("actionType")).thenReturn("insert");
        when(request.getParameter("role")).thenReturn("user");

        controller.doPost(request, response);

        User user = UserStorage.USER_STORAGE_TEST.selectByLogin("Vic");
        assertThat(user.getName(), is("Victor"));
    }
    /**
     * Test deleting user to the database.
     * @throws ServletException servlet exception
     * @throws IOException servlet exception
     * @throws UserStorageDAOException thrown if problems with database occur
     */
    @Test
    public void deleteUser() throws ServletException, IOException, UserStorageDAOException {
        this.initApp();
        ControllerServlet controller = new ControllerServlet();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getParameter("login")).thenReturn("Vic");
        when(request.getParameter("actionType")).thenReturn("delete");
        when(request.getParameter("role")).thenReturn("user");

        controller.doPost(request, response);

        User user = UserStorage.USER_STORAGE_TEST.selectByLogin("Vic");
        User expected = null;
        assertThat(user, is(expected));
    }
    /**
     * Test updating user to the database.
     * @throws ServletException servlet exception
     * @throws IOException servlet exception
     * @throws UserStorageDAOException thrown if problems with database occur
     */
    @Test
    public void updateUser() throws ServletException, IOException, UserStorageDAOException {
        this.initApp();
        ControllerServlet controller = new ControllerServlet();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);

        when(request.getParameter("name")).thenReturn("Victor");
        when(request.getParameter("login")).thenReturn("Vic");
        when(request.getParameter("email")).thenReturn("Victor@mail.ru");
        when(request.getParameter("password")).thenReturn("123");
        when(request.getParameter("actionType")).thenReturn("insert");
        when(request.getParameter("role")).thenReturn("user");

        controller.doPost(request, response);

        when(request.getParameter("name")).thenReturn("Vasily");
        when(request.getParameter("login")).thenReturn("Vic");
        when(request.getParameter("email")).thenReturn("Victor@mail.ru");
        when(request.getParameter("password")).thenReturn("123");
        when(request.getParameter("actionType")).thenReturn("update");
        when(request.getParameter("role")).thenReturn("user");

        controller.doPost(request, response);

        User user = UserStorage.USER_STORAGE_TEST.selectByLogin("Vic");
        assertThat(user.getName(), is("Vasily"));
    }
}