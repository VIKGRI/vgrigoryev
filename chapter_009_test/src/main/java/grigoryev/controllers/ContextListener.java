package grigoryev.controllers;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import grigoryev.models.MusicTypeContainer;
import grigoryev.models.RoleContainer;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

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
     * Properties for data source which are specified in web.xml
     */
    private static Properties dataSourceProperties = new Properties();

    /**
     * Gets data source properties
     * @return data source properties
     */
    public static Properties getDataSourceProperties() {
        return dataSourceProperties;
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Logger appLogger = LoggerFactory.getLogger(ContextListener.class);
        dataSourceProperties.setProperty("url", sce.getServletContext().getInitParameter("url"));
        dataSourceProperties.setProperty("username", sce.getServletContext().getInitParameter("username"));
        dataSourceProperties.setProperty("driver", sce.getServletContext().getInitParameter("driver"));
        dataSourceProperties.setProperty("password", sce.getServletContext().getInitParameter("password"));

        sce.getServletContext().setAttribute("appLogger", appLogger);
        sce.getServletContext().setAttribute("roleList", RoleContainer.getInstance().getRoleNames());
        sce.getServletContext().setAttribute("musicTypeList", MusicTypeContainer.getInstance().getMusicTypeNames());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (DataSourceHolder.getInstance() != null) {
            DataSourceHolder.getInstance().close();
        }
    }
}
