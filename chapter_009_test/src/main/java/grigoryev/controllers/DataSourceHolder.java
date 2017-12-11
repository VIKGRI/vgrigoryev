package grigoryev.controllers;

import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.apache.tomcat.jdbc.pool.DataSource;

import java.util.Properties;

/**
 * Represents data source. Implemented
 * using singleton pattern.
 *
 * @author vgrigoryev
 * @version 1
 * @since 11.12.2017
 */
public class DataSourceHolder {
    /**
     * App data source.
     */
    private static final DataSource INSTANCE;

    /**
     * Static initialization.
     */
    static {
        INSTANCE = new DataSource();
        setDataSourceProperties(ContextListener.getDataSourceProperties());
    }

    /**
     * Gets app's data source.
     * @return data source
     */
    public static DataSource getInstance() {
        return INSTANCE;
    }

    /**
     * Constructor.
     */
    private DataSourceHolder() {
    }

    /**
     * Establishes connection properties.
     * @param dataSourceProperties connection properties
     */
    public static void setDataSourceProperties(Properties dataSourceProperties) {
        PoolProperties properties = new PoolProperties();
        properties.setUrl(dataSourceProperties.getProperty("url"));
        properties.setDriverClassName(dataSourceProperties.getProperty("driver"));
        properties.setUsername(dataSourceProperties.getProperty("username"));
        properties.setPassword(dataSourceProperties.getProperty("password"));
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

        INSTANCE.setPoolProperties(properties);
    }
}
