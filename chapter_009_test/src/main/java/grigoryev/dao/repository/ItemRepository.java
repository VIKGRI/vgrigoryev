package grigoryev.dao.repository;

import grigoryev.dao.DatabaseDAOException;
import grigoryev.models.Model;

import java.util.List;

/**
 * Interface represents repository pattern.
 *
 * @author vgrigoryev
 * @version 1
 * @since 01.12.2017
 */
public interface ItemRepository<T extends Model> {
    /**
     * Queries list of users depending on specification.
     * @param specification describes criteria
     * @return list of appropriate items
     * @throws DatabaseDAOException thrown if problems within DAO layer occur
     */
    List<T> query(SqlSpecification specification) throws DatabaseDAOException;
}
