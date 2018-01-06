package com.grigoryev.controllers;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.grigoryev.services.HibernateUtil;
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
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Logger appLogger = LoggerFactory.getLogger(ContextListener.class);
        sce.getServletContext().setAttribute("appLogger", appLogger);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (HibernateUtil.getSessionFactory() != null) {
            HibernateUtil.getSessionFactory().close();
        }
    }
}
