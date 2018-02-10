package grigoryev.dao.repository.implement;

import grigoryev.dao.repository.SqlSpecification;
import grigoryev.models.Address;

/**
 * Provides specification for searching
 * user by address.
 *
 * @author vgrigoryev
 * @version 1
 * @since 29.11.2017
 */
public class GetUsersByAddressSpecification implements SqlSpecification {
    /**
     * Address.
     */
    Address address;

    /**
     * Constructor.
     * @param address address
     */
    public GetUsersByAddressSpecification(Address address) {
        this.address = address;
    }
    @Override
    public String toSqlClause() {
        String sql = "SELECT DISTINCT u.id, u.name AS user_name, u.login, u.email, r.name AS role_name, "
                + " a.id AS addr_id, a.street_name, a.house_no, a.apartment_no "
                + " FROM users AS u "
                + " JOIN roles AS r ON u.role_id = r.id "
                + " JOIN addresses AS a ON u.address_id = a.id"
                + " JOIN users_music_types AS umt ON u.id = umt.user_id "
                + " JOIN music_types AS mt ON umt.music_type_id = mt.id "
                + " WHERE a.street_name = '" +  this.address.getStreetName() + "' AND a.house_no = '"
                +  this.address.getHouseNo() + "' AND a.apartment_no = '" + this.address.getApartmentNo() + "'";
        return sql;
    }
}
