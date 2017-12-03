package grigoryev.dao.implement;

import grigoryev.controllers.ContextListener;
import grigoryev.dao.AddressDao;
import grigoryev.dao.DatabaseDAOException;
import grigoryev.models.Address;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides operations for database
 * which stores addresses. Implemented using
 * singleton pattern.
 *
 * @author vgrigoryev
 * @version 1
 * @since 29.11.2017
 */
public class AddressDatabaseDao implements AddressDao {
    /**
     * Provides creation of only one instance per app.
     * Implements singleton pattern.
     */
    private static final AddressDatabaseDao INSTANCE
            = new AddressDatabaseDao(ContextListener.getDataSource());
    /**
     * Datasource which provides connection pooling.
     */
    private DataSource dataSource;
    /**
     * Logger.
     */
    private static final Logger ADDRESS_DAO_LOGGER = LoggerFactory.getLogger(AddressDatabaseDao.class);

    /**
     * Constructor.
     *
     * @param ds datasource which provides connection pooling
     */
    private AddressDatabaseDao(DataSource ds) {
        this.dataSource = ds;
    }

    /**
     * Gets instance of AddressDatabaseDao.
     *
     * @return AddressDatabaseDao object
     */
    public static AddressDatabaseDao getInstance() {
        return INSTANCE;
    }

    @Override
    public Address getById(Integer id) throws DatabaseDAOException {
        String sql = "SELECT * FROM addresses WHERE id = (?)";
        Address address = null;
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement =
                         connection.prepareStatement(sql)) {
                statement.setInt(1, id);
                ResultSet addresses = statement.executeQuery();
                if (addresses.next()) {
                    address = new Address(id);
                    address.setStreetName(addresses.getString("street_name"));
                    address.setHouseNo(addresses.getString("house_no"));
                    address.setApartmentNo(addresses.getString("apartment_no"));
                }
            }
            return address;
        } catch (SQLException e) {
            ADDRESS_DAO_LOGGER.error("Select adress by id exception", e);
            throw new DatabaseDAOException("Select adress by id exception", e);
        }
    }

    @Override
    public List<Address> selectAll() throws DatabaseDAOException {
        String sql = "SELECT * FROM addresses";
        List<Address> addresses = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            try (Statement statement =
                         connection.createStatement()) {
                ResultSet result = statement.executeQuery(sql);
                while (result.next()) {
                    Integer id = result.getInt("id");
                    String streetName = result.getString("street_name");
                    String houseNo = result.getString("house_no");
                    String apartmentNo = result.getString("description");
                    Address address = new Address(id);
                    address.setStreetName(streetName);
                    address.setHouseNo(houseNo);
                    address.setApartmentNo(apartmentNo);
                    addresses.add(address);
                }
                return addresses;
            }
        } catch (SQLException e) {
            ADDRESS_DAO_LOGGER.error("Select all addresses exception", e);
            throw new DatabaseDAOException("Select all addresses exception", e);
        }
    }

    @Override
    public Address insert(Address item) throws DatabaseDAOException {
        String sql = "INSERT INTO addresses (street_name, house_no, apartment_no) VALUES ((?), (?), (?)) RETURNING id";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            Address address = null;
            statement.setString(1, item.getStreetName());
            statement.setString(2, item.getHouseNo());
            statement.setString(3, item.getApartmentNo());
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Integer id = result.getInt(1);
                address = item;
                address.setId(id);
            }
            return address;
        } catch (SQLException e) {
            ADDRESS_DAO_LOGGER.error("Insert address exception", e);
            throw new DatabaseDAOException("Insert address exception", e);
        }
    }

    @Override
    public void update(Address item) throws DatabaseDAOException {
        String sql = "UPDATE addresses SET  streeе_name = (?) house_no = (?) apartment_no = (?) WHERE id = (?)";
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement =
                         connection.prepareStatement(sql)) {
                statement.setString(1, item.getStreetName());
                statement.setString(2, item.getHouseNo());
                statement.setString(3, item.getApartmentNo());
                statement.setInt(4, item.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            ADDRESS_DAO_LOGGER.error("Update address exception", e);
            throw new DatabaseDAOException("Update address exception", e);
        }
    }

    @Override
    public void delete(Address item) throws DatabaseDAOException {
        String sql = "DELETE FROM addresses WHERE id = (?)";
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement =
                         connection.prepareStatement(sql)) {
                statement.setInt(1, item.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            ADDRESS_DAO_LOGGER.error("Delete address exception", e);
            throw new DatabaseDAOException("Delete address exception", e);
        }
    }
}
