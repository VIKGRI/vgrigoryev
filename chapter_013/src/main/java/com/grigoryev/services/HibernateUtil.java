package com.grigoryev.services;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

/**
 * Provides initialization for
 * session factory and method to get instance.
 *
 * @author vgrigoryev
 * @version 1
 * @since 02.01.2018
 */
public class HibernateUtil {
    /**
     * Session factory.
     */
    private static final SessionFactory SESSION_FACTORY;

    static {
        try {
            StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder().configure().build();
            Metadata metaData = new MetadataSources(standardRegistry).getMetadataBuilder().build();
            SESSION_FACTORY = metaData.getSessionFactoryBuilder().build();
        } catch (Throwable th) {
            System.err.println("Initial SessionFactory creation failed" + th);
            throw new ExceptionInInitializerError(th);
        }
    }

    /**
     * Gets instance of session factory.
     * @return session factory
     */
    public static SessionFactory getSessionFactory() {
        return SESSION_FACTORY;
    }
}