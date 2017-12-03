package grigoryev.controllers;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import grigoryev.models.MusicTypeContainer;
import grigoryev.models.RoleContainer;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Servlet context listener. Initializes resources when
 * application is created and releases it when application is
 * destroyed.
 *
 * @author vgrigoryev
 * @version 1
 * @since 28.11.2017
 */
public class ContextListener implements ServletContextListener {
    /**
     * App data source.
     */
    private static final DataSource DATA_SOURCE = new DataSource();

    /**
     * Gets app's data source.
     * @return data source
     */
    public static DataSource getDataSource() {
        return DATA_SOURCE;
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Logger appLogger = LoggerFactory.getLogger(ContextListener.class);

        this.connectDataSource(sce);

        sce.getServletContext().setAttribute("appLogger", appLogger);

        sce.getServletContext().setAttribute("roleList", RoleContainer.getInstance().getRoleNames());

        sce.getServletContext().setAttribute("musicTypeList", MusicTypeContainer.getInstance().getMusicTypeNames());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (DATA_SOURCE != null) {
            DATA_SOURCE.close();
        }
    }

    /**
     * Establishes connection properties.
     * @param sce ServletContextEvent object
     */
    private void connectDataSource(ServletContextEvent sce) {
        PoolProperties properties = new PoolProperties();
        properties.setUrl(
                sce.getServletContext().getInitParameter("url"));
        properties.setDriverClassName(
                sce.getServletContext().getInitParameter("driver"));
        properties.setUsername(
                sce.getServletContext().getInitParameter("username"));
        properties.setPassword(
                sce.getServletContext().getInitParameter("password"));

        properties.setTestOnBorrow(true);
        properties.setValidationQuery("SELECT 1");
        properties.setMaxActive(20);
        properties.setInitialSize(10);
        properties.setMaxWait(10000);
        properties.setRemoveAbandonedTimeout(60);
        properties.setMinEvictableIdleTimeMillis(30000);
        properties.setMinIdle(20);
        properties.setLogAbandoned(true);
        properties.setRemoveAbandoned(true);

        DATA_SOURCE.setPoolProperties(properties);
    }
}
