package grigoryev.dao;

import grigoryev.dao.repository.ItemRepository;
import grigoryev.models.User;

/**
 * Interface for user DAO.
 *
 * @author vgrigoryev
 * @version 1
 * @since 01.12.2017
 */
public interface UserRepositoryDao extends ItemDao<User>, ItemRepository<User> {
    boolean isCredentional(String login, String password) throws DatabaseDAOException;
}
