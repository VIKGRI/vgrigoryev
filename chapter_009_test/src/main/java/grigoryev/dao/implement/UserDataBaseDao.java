package grigoryev.dao.implement;

import grigoryev.controllers.ContextListener;
import grigoryev.dao.DatabaseDAOException;
import grigoryev.dao.repository.SqlSpecification;
import grigoryev.dao.UserRepositoryDao;
import grigoryev.models.*;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Provides operations for database
 * which stores users. Implemented using
 * singleton pattern.
 *
 * @author vgrigoryev
 * @version 1
 * @since 29.11.2017
 */
public class UserDataBaseDao implements UserRepositoryDao {
    /**
     * Provides creation of only one instance per app.
     * Implements singleton pattern.
     */
    private static final UserDataBaseDao INSTANCE
            = new UserDataBaseDao(ContextListener.getDataSource());
    /**
     * Datasource which provides connection pooling.
     */
    private DataSource dataSource;
    /**
     * Logger.
     */
    private static final Logger USER_DAO_LOGGER = LoggerFactory.getLogger(UserDataBaseDao.class);

    /**
     * Constructor.
     *
     * @param ds datasource which provides connection pooling
     */
    private UserDataBaseDao(DataSource ds) {
        this.dataSource = ds;
    }

    /**
     * Gets instance of UserDataBaseDao.
     *
     * @return UserDataBaseDao object
     */
    public static UserDataBaseDao getInstance() {
        return INSTANCE;
    }

    @Override
    public User getById(Integer id) throws DatabaseDAOException {
        String sql = "SELECT u.name AS user_name, u.login, u.email, r.name AS role_name,"
                + " a.id AS addr_id, a.street_name, a.house_no, a.apartment_no"
                + " FROM users AS u JOIN roles AS r ON u.role_id = r.id "
                + "JOIN addresses AS a ON u.address_id = a.id WHERE u.id =" + id;
        User user = null;
        try (Connection connection = dataSource.getConnection()) {
            try (Statement statement =
                         connection.createStatement()) {
                ResultSet result = statement.executeQuery(sql);
                if (result.next()) {
                    user = new User(id);
                    user.setName(result.getString("user_name"));
                    user.setLogin(result.getString("login"));
                    user.setPassword(null);
                    user.setEmail(result.getString("email"));

                    user.setRole(RoleContainer.getInstance().getRole(result.getString("role_name")));

                    Address address = new Address(result.getInt("addr_id"));
                    address.setStreetName(result.getString("street_name"));
                    address.setHouseNo(result.getString("house_no"));
                    address.setApartmentNo(result.getString("apartment_no"));
                    user.setAddress(address);

                    user.setMusicTypes(this.getMusicTypes(connection, id));
                }
            }
        } catch (SQLException e) {
            USER_DAO_LOGGER.error("Select user by id exception", e);
            throw new DatabaseDAOException("Select user by id exception", e);
        }
        return user;
    }

    @Override
    public List<User> selectAll() throws DatabaseDAOException {
        String sql = "SELECT DISTINCT u.id, u.name AS user_name, u.login, u.email, r.name AS role_name,"
                + " a.id AS addr_id, a.street_name, a.house_no, a.apartment_no"
                + " FROM users AS u JOIN roles AS r ON u.role_id = r.id "
                + "JOIN addresses AS a ON u.address_id = a.id";
        List<User> users = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            try (Statement statement =
                         connection.createStatement()) {
                ResultSet result = statement.executeQuery(sql);
                while (result.next()) {
                    Integer id = result.getInt("id");
                    User user = new User(id);
                    user.setName(result.getString("user_name"));
                    user.setLogin(result.getString("login"));
                    user.setPassword(null);
                    user.setEmail(result.getString("email"));
                    user.setRole(RoleContainer.getInstance().getRole(result.getString("role_name")));
                    Address address = new Address(result.getInt("addr_id"));
                    address.setStreetName(result.getString("street_name"));
                    address.setHouseNo(result.getString("house_no"));
                    address.setApartmentNo(result.getString("apartment_no"));
                    user.setAddress(address);
                    user.setMusicTypes(this.getMusicTypes(connection, id));
                    users.add(user);
                }
                return users;
            }
        } catch (SQLException e) {
            USER_DAO_LOGGER.error("Select all users exception", e);
            throw new DatabaseDAOException("Select all users exception", e);
        }
    }

    /**
     * Gets all music types user prefers.
     *
     * @param connection available connection
     * @param id         user's id
     * @return list of music types user prefers
     * @throws SQLException thrown if database related problems occur
     */
    private List<MusicType> getMusicTypes(Connection connection, Integer id) throws DatabaseDAOException {
        String sqlGetMusicTypes = "SELECT mt.title AS music_type FROM users AS u "
                + "JOIN users_music_types AS umt ON u.id = umt.user_id "
                + "JOIN music_types AS mt ON umt.music_type_id = mt.id WHERE u.id = " + id;
        List<MusicType> types = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            ResultSet musicTypeQuery = statement.executeQuery(sqlGetMusicTypes);
            while (musicTypeQuery.next()) {
                types.add(MusicTypeContainer.getInstance().getMusicType(
                        musicTypeQuery.getString("music_type")));
            }
            return types;
        } catch (SQLException e) {
            USER_DAO_LOGGER.error("Get music types exception", e);
            throw new DatabaseDAOException("Get music types exception", e);
        }
    }

    @Override
    public User insert(User item) throws DatabaseDAOException {
        String sql = "INSERT INTO users (name, login, password, email, role_id, address_id)"
                + " VALUES ((?), (?), (?), (?), (?), (?)) RETURNING id";
        User user = null;
        try (Connection connection = dataSource.getConnection(); PreparedStatement statement =
                         connection.prepareStatement(sql)) {
                statement.setString(1, item.getName());
                statement.setString(2, item.getLogin());
                statement.setString(3, item.getPassword());
                statement.setString(4, item.getEmail());
                statement.setLong(5, item.getRole().getId());

                Integer addressId = AddressDatabaseDao.getInstance().insert(item.getAddress()).getId();

                statement.setLong(6, addressId);
                ResultSet result = statement.executeQuery();
                // Gets result set with generated id
                // Binding music types with users
                if (result.next()) {
                    Integer id = result.getInt(1);
                    this.setMusicTypesToUser(connection, id, item.getMusicTypes());
                    user = item;
                    user.setId(id);
                }
                return user;
        } catch (SQLException e) {
            USER_DAO_LOGGER.error("Insert user exception", e);
            throw new DatabaseDAOException("Insert user exception", e);
        }
    }

    /**
     * Sets all music types user prefers.
     *
     * @param connection available connection
     * @param id         user's id
     * @param types      music types user prefers
     * @throws DatabaseDAOException thrown if database related problems occur
     */
    private void setMusicTypesToUser(Connection connection, Integer id, List<MusicType> types) throws DatabaseDAOException {
        String sqlAddMusicTypes = "INSERT INTO users_music_types (user_id, music_type_id) VALUES ((?), (?))";
        try {
            boolean autoCommit = connection.getAutoCommit();
            connection.setAutoCommit(false);
            try (PreparedStatement statement =
                         connection.prepareStatement(sqlAddMusicTypes)) {
                for (MusicType type : types) {
                    statement.setLong(1, id);
                    statement.setLong(2, type.getId());
                    statement.addBatch();
                }
                statement.executeBatch();
                connection.setAutoCommit(autoCommit);
            }
        } catch (SQLException e) {
            USER_DAO_LOGGER.error("Binding music types with user exception", e);
            throw new DatabaseDAOException("Binding music types with user exception", e);
        }
    }

    @Override
    public void update(User item) throws DatabaseDAOException {
        String sql = "UPDATE users SET  name = (?) login = (?) password = (?) email = (?) role_id = (?) address_id = (?) WHERE id = (?)";
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement =
                         connection.prepareStatement(sql)) {
                statement.setString(1, item.getName());
                statement.setString(2, item.getLogin());
                statement.setString(3, item.getEmail());
                statement.setString(4, item.getPassword());
                statement.setLong(5, item.getRole().getId());
                statement.setLong(6, item.getAddress().getId());
                statement.setLong(7, item.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            USER_DAO_LOGGER.error("Update user exception", e);
            throw new DatabaseDAOException("Update user exception", e);
        }
    }

    @Override
    public void delete(User item) throws DatabaseDAOException {
        String sql = "DELETE FROM users WHERE id = (?)";
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement =
                         connection.prepareStatement(sql)) {
                statement.setLong(1, item.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            USER_DAO_LOGGER.error("Delete user exception", e);
            throw new DatabaseDAOException("Delete user exception", e);
        }
    }

    @Override
    public List<User> query(SqlSpecification specification) throws DatabaseDAOException {
        String sql = specification.toSqlClause();
        List<User> users = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            try (Statement statement =
                         connection.createStatement()) {
                ResultSet result = statement.executeQuery(sql);
                while (result.next()) {
                    Integer id = result.getInt("id");
                    User user = new User(id);
                    user.setName(result.getString("user_name"));
                    user.setLogin(result.getString("login"));
                    user.setPassword(null);
                    user.setEmail(result.getString("email"));
                    user.setRole(RoleContainer.getInstance().getRole(result.getString("role_name")));

                    Address address = new Address(result.getInt("addr_id"));
                    address.setStreetName(result.getString("street_name"));
                    address.setHouseNo(result.getString("house_no"));
                    address.setApartmentNo(result.getString("apartment_no"));
                    user.setAddress(address);

                    user.setMusicTypes(this.getMusicTypes(connection, id));
                    users.add(user);
                }
                return users;
            }
        } catch (SQLException e) {
            USER_DAO_LOGGER.error("Query users by criteria exception", e);
            throw new DatabaseDAOException("Query users by criteria exception", e);
        }
    }

    @Override
    public boolean isCredentional(String login, String password) throws DatabaseDAOException {
        String sql = "SELECT login, password FROM users WHERE login = (?)";
        boolean isConfirmed = false;
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement =
                         connection.prepareStatement(sql)) {
                statement.setString(1, login);
                ResultSet user = statement.executeQuery();
                if (user.next()) {
                    if (user.getString("password").equals(password)) {
                        isConfirmed = true;
                    }
                }
                return isConfirmed;
            }
        } catch (SQLException e) {
            USER_DAO_LOGGER.error("Sign in exception", e);
            throw new DatabaseDAOException("Sign in exception", e);
        }
    }
}
