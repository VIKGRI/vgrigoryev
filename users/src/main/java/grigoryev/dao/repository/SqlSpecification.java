package grigoryev.dao.repository;

/**
 * Interface represents criteria using in repository pattern.
 *
 * @author vgrigoryev
 * @version 1
 * @since 01.12.2017
 */
public interface SqlSpecification {
    /**
     * Determines criteria fo search.
     * @return Query.
     */
    String toSqlClause();
}
