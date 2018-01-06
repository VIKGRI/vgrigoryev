package com.grigoryev.dao.impl;

import com.grigoryev.dao.DatabaseDAOException;
import com.grigoryev.dao.ItemDao;
import com.grigoryev.models.Item;
import com.grigoryev.services.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Provides operations for database
 * which stores items. Implemented using
 * singleton pattern.
 *
 * @author vgrigoryev
 * @version 1
 * @since 02.01.2018
 */
public class ItemDataBaseDao implements ItemDao<Item> {
    /**
     * Provides creation of only one instance per app.
     * Implements singleton pattern.
     */
    private static final ItemDataBaseDao INSTANCE
            = new ItemDataBaseDao();
    /**
     *  SessionFactory which provides connection services.
     */
    //private SessionFactory sessionFactory;
    /**
     * Logger.
     */
    private static final Logger ITEM_DAO_LOGGER = LoggerFactory.getLogger(ItemDataBaseDao.class);

    /**
     * Constructor.
     *
     *
     */
    private ItemDataBaseDao() {

    }

    /**
     * Gets instance of ItemDataBaseDao.
     *
     * @return ItemDataBaseDao object
     */
    public static ItemDataBaseDao getInstance() {
        return INSTANCE;
    }

    @Override
    public Item getById(Integer id) throws DatabaseDAOException {
        Item item = null;
        if (id != null) {
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.openSession();
            try {
                session.beginTransaction();
                item = session.get(Item.class, id);
                session.getTransaction().commit();
            } catch (Exception ex) {
                ITEM_DAO_LOGGER.error(ex.getMessage());
                throw new DatabaseDAOException(ex.getMessage());
            } finally {
                session.close();
            }
        }
        return item;
    }

    @Override
    public List<Item> selectAll() throws DatabaseDAOException {
        List items = null;
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            items = session.createQuery("FROM Item").list();
            session.getTransaction().commit();
        } catch (Exception ex) {
            ITEM_DAO_LOGGER.error(ex.getMessage());
            throw new DatabaseDAOException(ex.getMessage());
        } finally {
            session.close();
        }
        return items;
    }

    @Override
    public Item insert(Item item) throws DatabaseDAOException {
        if (item != null) {
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.openSession();
            try {
                session.beginTransaction();
                session.save(item);
                session.getTransaction().commit();
            } catch (Exception ex) {
                session.getTransaction().rollback();
                ITEM_DAO_LOGGER.error(ex.getMessage(), ex);
                throw new DatabaseDAOException(ex.getMessage());
            } finally {
                session.close();
            }
        }
        return item;
    }

    @Override
    public void delete(Item item) throws DatabaseDAOException {
        if (item != null && item.getId() != null) {
            SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
            Session session = sessionFactory.openSession();
            try {
                session.beginTransaction();
                session.delete(item);
                session.getTransaction().commit();
            } catch (Exception ex) {
                session.getTransaction().rollback();
                ITEM_DAO_LOGGER.error(ex.getMessage(), ex);
                throw new DatabaseDAOException(ex.getMessage());
            } finally {
                session.close();
            }
        }
    }

    /**
     * Gets all items which have not been completed yet.
     * @return list of undone items
     * @throws DatabaseDAOException thrown if problems within DAO layer occur
     */
    public List<Item> selectAllUndone() throws DatabaseDAOException {
        List items = null;
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        try {
            session.beginTransaction();
            items = session.createQuery("FROM Item I WHERE I.done = false").list();
            session.getTransaction().commit();
        } catch (Exception ex) {
            ITEM_DAO_LOGGER.error(ex.getMessage());
            throw new DatabaseDAOException(ex.getMessage());
        } finally {
            session.close();
        }
        return items;
    }
}
