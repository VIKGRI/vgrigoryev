package grigoryev.servlets;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Provides operations for database
 * which stores users.
 *
 * @author vgrigoryev
 * @since 08.11.2017
 * @version 1
 */
public class UserStorage {
    /**
     * Provides creation of only one instance per app.
     * Implements singleton pattern.
     */
    public static final UserStorage USER_STORAGE = new UserStorage(setupDataSourceConnection());

    /**
     * Request for inserting user in the database.
     */
    private static final String INSERT_USER =
            "INSERT INTO users (name, login, email, create_time, password, role) "
                    + "VALUES ((?), (?), (?), (?), (?), (?)) ON CONFLICT DO NOTHING";
    /**
     * Request for updating user in the database.
     */
    private static final String UPDATE_USER =
            "UPDATE users SET name = (?), email = (?), password = (?), role = (?) WHERE login = (?)";
    /**
     * Request for deleting user in the database.
     */
    private static final String DELETE_USER =
            "DELETE FROM users WHERE login = (?)";
    /**
     * Request for selecting user which matches specified login in the database.
     */
    private static final String SELECT_BY_LOGIN = "SELECT name, login, email, create_time, role FROM users WHERE login = (?)";
    /**
     * Request for selecting all users in the database.
     */
    private static final String SELECT_ALL_USERS = "SELECT name, login, email, create_time, role FROM users";

    /**
     * Datasource which provides connection pooling.
     */
    private  DataSource dataSource;
    /**
     * Logging DAO layer exceptions.
     */
    private static final Logger DAO_LOGGER = LoggerFactory.getLogger(UserStorage.class);

    /**
     * Constructor.
     * @param ds datasource which provides connection pooling
     */
    private UserStorage(DataSource ds) {
        dataSource = ds;
    }

    /**
     * Sets up data source pooling connection.
     * @return data source
     */
    private static DataSource setupDataSourceConnection() {
        PoolProperties properties = new PoolProperties();
        properties.setUrl(
                UsersServletContextListener.getUrl());
        properties.setDriverClassName(
                UsersServletContextListener.getDriver());
        properties.setUsername(
                UsersServletContextListener.getUsername());
        properties.setPassword(
                UsersServletContextListener.getPassword());

        properties.setTestOnBorrow(true);
        properties.setValidationQuery("SELECT 1");
        properties.setMaxActive(20);
        properties.setInitialSize(10);
        properties.setMaxWait(10000);
        properties.setRemoveAbandonedTimeout(60);
        properties.setMinEvictableIdleTimeMillis(30000);
        properties.setMinIdle(20);
        properties.setLogAbandoned(true);
        properties.setRemoveAbandoned(true);

        DataSource dataSource = new DataSource();
        dataSource.setPoolProperties(properties);

        return dataSource;
    }

    /**
     * Creates database if it is not exists.
     *
     * @throws UserStorageDAOException thrown if problems with database connection occur
     */
    public void createTables() throws UserStorageDAOException {
        try (Connection connection = dataSource.getConnection()) {
            try (Statement createTable = connection.createStatement()) {
                String createRoles = "CREATE TABLE IF NOT EXISTS roles"
                        + "(role_name VARCHAR(20) PRIMARY KEY, description VARCHAR(150))";
                createTable.execute(createRoles);

                String createPrivilegies = "CREATE TABLE IF NOT EXISTS privilegies"
                        + "(privilege_name VARCHAR(20) PRIMARY KEY, description VARCHAR(150))";
                createTable.execute(createPrivilegies);

                String createRolesAndPrivilegies = "CREATE TABLE IF NOT EXISTS roles_and_privilegies"
                        + "(role_name VARCHAR(20) REFERENCES roles(role_name) NOT NULL,"
                        + " privilege VARCHAR(20) REFERENCES privilegies(privilege_name) NOT NULL, "
                        + "PRIMARY KEY (role_name, privilege))";
                createTable.execute(createRolesAndPrivilegies);

                String createUsers = "CREATE TABLE IF NOT EXISTS users"
                        + "(name VARCHAR(50), login VARCHAR(50) PRIMARY KEY NOT NULL,"
                        + "email VARCHAR(50), create_time TIMESTAMP, password VARCHAR(20), "
                        + "role VARCHAR(20) REFERENCES roles(role_name) NOT NULL)";
                createTable.execute(createUsers);
            }
        } catch (SQLException e) {
            DAO_LOGGER.error("Create table exception", e);
            throw new UserStorageDAOException("Create table exception", e);
        }
    }

    /**
     * Inserts user in the database.
     *
     * @param user specified user
     * @return information whether operation succeeds or not
     * @throws UserStorageDAOException thrown if problems with database occur
     */
    public String insertUser(User user) throws UserStorageDAOException {
                try (Connection connection = dataSource.getConnection()) {
                    try (PreparedStatement insert =
                                 connection.prepareStatement(INSERT_USER)) {
                        insert.setString(1, user.getName());
                        insert.setString(2, user.getLogin());
                        insert.setString(3, user.getEmail());
                        insert.setTimestamp(4, new Timestamp(user.getCreateDate()));
                        insert.setString(5, user.getPassword());
                        insert.setString(6, user.getRole().getName());
                int result = insert.executeUpdate();
                String operationResult;
                if (result != 0) {
                    operationResult = "User with login " + user.getLogin() + " is added";
                } else {
                    operationResult = "User with login " + user.getLogin() + " is not added";
                }
                return operationResult;
            }
        } catch (SQLException e) {
            DAO_LOGGER.error("Insert user exception", e);
            throw new UserStorageDAOException("Insert user exception", e);
        }
    }

    /**
     * Updates user in the database.
     *
     * @param user specified user
     * @return information whether operation succeeds or not
     * @throws UserStorageDAOException thrown if problems with database occur
     */
    public String updateUser(User user) throws UserStorageDAOException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement update =
                         connection.prepareStatement(UPDATE_USER)) {
                update.setString(1, user.getName());
                update.setString(2, user.getEmail());
                update.setString(3, user.getPassword());
                update.setString(4, user.getRole().getName());
                update.setString(5, user.getLogin());
                int result = update.executeUpdate();
                String operationResult;
                if (result != 0) {
                    operationResult = "User with login " + user.getLogin() + " is updated";
                } else {
                    operationResult = "User with login " + user.getLogin() + " is not found";
                }
                return operationResult;
            }
        } catch (SQLException e) {
            DAO_LOGGER.error("Update user exception", e);
            throw new UserStorageDAOException("Update user exception", e);
        }
    }

    /**
     * Deletes user from database.
     *
     * @param user specified item
     * @return information whether operation succeeds or not
     * @throws UserStorageDAOException thrown if problems with database occur
     */
    public String deleteUser(User user) throws UserStorageDAOException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement delete =
                         connection.prepareStatement(DELETE_USER)) {
                delete.setString(1, user.getLogin());
                int result = delete.executeUpdate();
                String operationResult;
                if (result != 0) {
                    operationResult = "User with login " + user.getLogin() + " is deleted";
                } else {
                    operationResult = "User with login " + user.getLogin() + " is not found";
                }
                return operationResult;
            }
        } catch (SQLException e) {
            DAO_LOGGER.error("Delete user exception", e);
            throw new UserStorageDAOException("Delete user exception", e);
        }
    }

    /**
     * Selects user with specified login.
     *
     * @param login specified login.
     * @return User with specified login or null if he doesn't exist
     * @throws UserStorageDAOException thrown if problems with database occur
     */
    public User selectByLogin(String login) throws UserStorageDAOException {
        User currentUser = null;
        Roles roles = Roles.getInstance();
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement selectByName =
                         connection.prepareStatement(SELECT_BY_LOGIN)) {
                selectByName.setString(1, login);
                ResultSet users = selectByName.executeQuery();
                while (users.next()) {
                    currentUser = new User(
                            users.getString("name"),
                            login,
                            users.getString("email"),
                            users.getTimestamp("create_time").getTime(),
                            null, roles.getRole(users.getString("role"))
                    );
                    break;
                }
            }
        } catch (SQLException e) {
            DAO_LOGGER.error("Select user by login exception", e);
            throw new UserStorageDAOException("Select user by login exception", e);
        }
        return currentUser;
    }

    /**
     * Selects all users from database.
     *
     * @return list of all users
     * @throws UserStorageDAOException thrown if problems with database occur
     */
    public List<User> selectAll() throws UserStorageDAOException {
        User currentUser;
        List<User> result = new ArrayList<>();
        Roles roles = Roles.getInstance();
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement selectAll =
                         connection.prepareStatement(SELECT_ALL_USERS);) {
                ResultSet users = selectAll.executeQuery();
                while (users.next()) {
                    currentUser = new User(
                            users.getString("name"),
                            users.getString("login"),
                            users.getString("email"),
                            users.getTimestamp("create_time").getTime(),
                            null, roles.getRole(users.getString("role"))
                    );
                    result.add(currentUser);
                }
            }
        } catch (SQLException e) {
            DAO_LOGGER.error("Select all users exception", e);
            throw new UserStorageDAOException("Select all users exception", e);
        }
        return result;
    }

    /**
     * Confirms whether user is authenticated or not.
     *
     * @param login user's login
     * @param password user's password
     * @return true if user is authenticated, otherwise false
     * @throws UserStorageDAOException thrown if problems with database occur
     */
    public boolean isCredentional(String login, String password) throws UserStorageDAOException {
        String sql = "SELECT login, password FROM users WHERE login = (?)";
        boolean isConfirmed = false;
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement =
                         connection.prepareStatement(sql);) {
                statement.setString(1, login);
                ResultSet user = statement.executeQuery();
                if (user.next()) {
                    if (user.getString("password").equals(password)) {
                        isConfirmed = true;
                    }
                }
            }
        } catch (SQLException e) {
            DAO_LOGGER.error("Select all users exception", e);
            throw new UserStorageDAOException("Select all users exception", e);
        }
        return isConfirmed;
    }

    /**
     * Inserts role in the database.
     *
     * @param roleName role's name.
     * @param action action available for this role
     * @return information whether operation succeeds or not
     * @throws UserStorageDAOException thrown if problems with database occur
     */
    public String bindRoleAndPrivilege(String roleName, Action action) throws UserStorageDAOException {

        String sql = "INSERT INTO roles_and_privilegies (role_name, privilege) VALUES ((?), (?)) ON CONFLICT DO NOTHING";

        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement insert =
                         connection.prepareStatement(sql)) {
                insert.setString(1, roleName.toLowerCase());
                insert.setString(2, action.toString().toLowerCase());
                int result = insert.executeUpdate();
                String operationResult;
                if (result != 0) {
                    operationResult = "Privilege " + action.toString() + " is added to role";
                } else {
                    operationResult = "Privilege " + action.toString() + " is not added to role";
                }
                return operationResult;
            }
        } catch (SQLException e) {
            DAO_LOGGER.error("Binding role and privilege exception", e);
            throw new UserStorageDAOException("Binding role and privilege exception", e);
        }
    }

    public Map<String, Role> selectRoles() throws UserStorageDAOException {
        try (Connection connection = dataSource.getConnection()) {
            Map<String, Role> roles = new HashMap<>();
            String sql = "SELECT * FROM roles_and_privilegies";
            try (Statement statement =
                          connection.createStatement()) {
                ResultSet result = statement.executeQuery(sql);
                while (result.next()) {
                    String roleName = result.getString("role_name");
                    String privilege = result.getString("privilege");
                    Role role = new Role(roleName);
                    role.addPrivileges(Model.toAction(privilege));
                    if (roles.putIfAbsent(roleName, role) != null) {
                        roles.get(roleName).addPrivileges(Model.toAction(privilege));
                    }
                }
            }
            return roles;
        } catch (SQLException e) {
            DAO_LOGGER.error("Insert role exception", e);
            throw new UserStorageDAOException("Insert role exception", e);
        }
    }

    public String addRole(String roleName, String description) throws UserStorageDAOException {
        String sql = "INSERT INTO roles (role_name, description) VALUES ((?), (?)) ON CONFLICT DO NOTHING";

        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement insert =
                         connection.prepareStatement(sql)) {
                insert.setString(1, roleName);
                insert.setString(2, description);
                int result = insert.executeUpdate();
                String operationResult;
                if (result != 0) {
                    operationResult = "Role " + roleName + " is added";
                } else {
                    operationResult = "Role " + roleName + " is not added";
                }
                return operationResult;
            }
        } catch (SQLException e) {
            DAO_LOGGER.error("Insert role exception", e);
            throw new UserStorageDAOException("Insert role exception", e);
        }
    }

    public String addPrivilege(String privilegeName, String description) throws UserStorageDAOException {
        String sql = "INSERT INTO privilegies (privilege_name, description) VALUES ((?), (?)) ON CONFLICT DO NOTHING";

        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement insert =
                         connection.prepareStatement(sql)) {
                insert.setString(1, privilegeName.toLowerCase());
                insert.setString(2, description);
                int result = insert.executeUpdate();
                String operationResult;
                if (result != 0) {
                    operationResult = "Privilege " + privilegeName + " is added";
                } else {
                    operationResult = "Privilege " + privilegeName + " is not added";
                }
                return operationResult;
            }
        } catch (SQLException e) {
            DAO_LOGGER.error("Insert privilege exception", e);
            throw new UserStorageDAOException("Insert privilege exception", e);
        }
    }
}
