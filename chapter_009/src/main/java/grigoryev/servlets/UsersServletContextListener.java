package grigoryev.servlets;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.SQLException;

/**
 * Servlet context listener. Initializes resources when
 * application is created and releases it when application ie
 * destroyed.
 *
 * @author vgrigoryev
 * @since 08.11.2017
 * @version 1
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
     * @return url
     */
    public static String getUrl() {
        return url;
    }

    /**
     * Gets driver.
     * @return driver
     */
    public static String getDriver() {
        return driver;
    }

    /**
     * Gets username.
     * @return username
     */
    public static String getUsername() {
        return username;
    }

    /**
     * Gets password.
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
            UserStorage.USER_STORAGE.createTable();
        } catch(SQLException e) {
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
