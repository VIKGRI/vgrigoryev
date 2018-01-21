package grigoryev.dao;

import grigoryev.models.Model;

import java.util.List;

/**
 * Interface for item DAO.
 *
 * @author vgrigoryev
 * @version 1
 * @since 01.12.2017
 */
public interface ItemDao<T extends Model> {
    /**
     * Gets item by id.
     * @param id item's id.
     * @return item
     * @throws DatabaseDAOException thrown if problems within DAO layer occur
     */
    T getById(Integer id) throws DatabaseDAOException;

    /**
     * Select all items.
     * @return list of all items
     * @throws DatabaseDAOException thrown if problems within DAO layer occur
     */
    List<T> selectAll() throws DatabaseDAOException;

    /**
     * Inserts item.
     * @param item item
     * @return item with id.
     * @throws DatabaseDAOException thrown if problems within DAO layer occur
     */
    T insert(T item) throws DatabaseDAOException;

    /**
     * Updates item.
     * @param item item
     * @throws DatabaseDAOException thrown if problems within DAO layer occur
     */
    void update(T item) throws DatabaseDAOException;

    /**
     * Deletes item.
     * @param item item
     * @throws DatabaseDAOException thrown if problems within DAO layer occur
     */
    void delete(T item) throws DatabaseDAOException;
}
