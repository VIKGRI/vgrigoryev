package grigoryev.dao.repository.implement;

import grigoryev.dao.repository.SqlSpecification;

/**
 * Provides specification for searching
 * user by login.
 *
 * @author vgrigoryev
 * @version 1
 * @since 29.11.2017
 */
public class GetUserByLoginSpecification implements SqlSpecification {
    /**
     * Login.
     */
    String login;

    /**
     * Constructor.
     * @param login login
     */
    public GetUserByLoginSpecification(String login) {
        this.login = login;
    }
    @Override
    public String toSqlClause() {
        String sql = "SELECT DISTINCT u.id, u.name AS user_name, u.login, u.email, r.name AS role_name, "
                + " a.id AS addr_id, a.street_name, a.house_no, a.apartment_no "
                + " FROM users AS u "
                + " JOIN roles AS r ON u.role_id = r.id "
                + " JOIN addresses AS a ON u.address_id = a.id "
                + " JOIN users_music_types AS umt ON u.id = umt.user_id "
                + " JOIN music_types AS mt ON umt.music_type_id = mt.id "
                + " WHERE u.login = '" + this.login + "'";
        return sql;
    }
}
