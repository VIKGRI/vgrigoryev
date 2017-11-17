package grigoryev.servlets;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Servlet context listener. Initializes resources when
 * application is created and releases it when application ie
 * destroyed.
 *
 * @author vgrigoryev
 * @version 1
 * @since 08.11.2017
 */
public class UsersServletContextListener implements ServletContextListener {
    /**
     * Url to database connection.
     */
    private static String url;
    /**
     * Driver to connect to database.
     */
    private static String driver;
    /**
     * Username.
     */
    private static String username;
    /**
     * Password.
     */
    private static String password;

    /**
     * Gets url.
     *
     * @return url
     */
    public static String getUrl() {
        return url;
    }

    /**
     * Gets driver.
     *
     * @return driver
     */
    public static String getDriver() {
        return driver;
    }

    /**
     * Gets username.
     *
     * @return username
     */
    public static String getUsername() {
        return username;
    }

    /**
     * Gets password.
     *
     * @return password
     */
    public static String getPassword() {
        return password;
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Logger appLogger = LoggerFactory.getLogger(UsersServletContextListener.class);
        sce.getServletContext().setAttribute("appLogger", appLogger);

        url = sce.getServletContext().getInitParameter("url");
        driver = sce.getServletContext().getInitParameter("driver");
        username = sce.getServletContext().getInitParameter("username");
        password = sce.getServletContext().getInitParameter("password");

        try {
            UserStorage.USER_STORAGE.createTables();

            UserStorage.USER_STORAGE.addRole("admin", "manages web resource");
            UserStorage.USER_STORAGE.addRole("user", "uses web resource");

            UserStorage.USER_STORAGE.addPrivilege(Action.Insert.name(), "inserts user in the database");
            UserStorage.USER_STORAGE.addPrivilege(Action.Delete.name(), "deletes user from the database");
            UserStorage.USER_STORAGE.addPrivilege(Action.Update.name(), "updates user in the database");
            UserStorage.USER_STORAGE.addPrivilege(Action.Select.name(), "select user by login from the database");
            UserStorage.USER_STORAGE.addPrivilege(Action.Selectall.name(), "selects all users from the database");

            UserStorage.USER_STORAGE.bindRoleAndPrivilege("admin", Action.Insert);
            UserStorage.USER_STORAGE.bindRoleAndPrivilege("admin", Action.Delete);
            UserStorage.USER_STORAGE.bindRoleAndPrivilege("admin", Action.Update);
            UserStorage.USER_STORAGE.bindRoleAndPrivilege("admin", Action.Select);
            UserStorage.USER_STORAGE.bindRoleAndPrivilege("admin", Action.Selectall);
            UserStorage.USER_STORAGE.bindRoleAndPrivilege("user", Action.Update);
            UserStorage.USER_STORAGE.bindRoleAndPrivilege("user", Action.Select);
            UserStorage.USER_STORAGE.bindRoleAndPrivilege("user", Action.Selectall);

            Roles roles = Roles.getInstance();

            sce.getServletContext().setAttribute("roleList", roles.getRoleNames());

            UserStorage.USER_STORAGE.insertUser(new User("root", "root", "root@mail.ru",
                    System.currentTimeMillis(), "root", roles.getRole("admin")));
        } catch (UserStorageDAOException e) {
            appLogger.error(e.getMessage(), e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        DataSource dataSource = (DataSource) sce.getServletContext().getAttribute("dataSource");
        if (dataSource != null) {
            dataSource.close();
        }
    }
}
