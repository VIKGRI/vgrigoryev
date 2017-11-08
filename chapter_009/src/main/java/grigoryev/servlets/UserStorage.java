package grigoryev.servlets;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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
            "INSERT INTO users (name, login, email, create_time) VALUES ((?), (?), (?), (?))";
    /**
     * Request for updating user in the database.
     */
    private static final String UPDATE_USER =
            "UPDATE users SET name = (?), email = (?) WHERE login = (?)";
    /**
     * Request for deleting user in the database.
     */
    private static final String DELETE_USER =
            "DELETE FROM users WHERE login = (?)";
    /**
     * Request for selecting user which matches specified login in the database.
     */
    private static final String SELECT_BY_LOGIN = "SELECT * FROM users WHERE login = (?)";
    /**
     * Request for selecting all users in the database.
     */
    private static final String SELECT_ALL_USERS = "SELECT * FROM users";
    /**
     * Datasource which provides connection pooling.
     */
    private  DataSource dataSource;

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
        properties.setMinIdle(20);//???
        properties.setLogAbandoned(true);
        properties.setRemoveAbandoned(true);

        DataSource dataSource = new DataSource();
        dataSource.setPoolProperties(properties);

        return dataSource;
    }

    /**
     * Creates database if it is not exists.
     *
     * @throws SQLException thrown if problems with database connection occur
     */
    public void createTable() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            String request = "CREATE TABLE IF NOT EXISTS users"
                    + "(name VARCHAR(50), login VARCHAR(50) PRIMARY KEY NOT NULL,"
                    + "email VARCHAR(50), create_time TIMESTAMP )";
            PreparedStatement createTable = connection.prepareStatement(request);
            createTable.execute();
        }
    }

    /**
     * Inserts user in the database.
     *
     * @param user specified user
     * @return information whether operation succeeds or not
     * @throws SQLException thrown if problems with database connection occur
     */
    public String insertUser(User user) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement insert =
                         connection.prepareStatement(INSERT_USER)) {
                insert.setString(1, user.getName());
                insert.setString(2, user.getLogin());
                insert.setString(3, user.getEmail());
                insert.setTimestamp(4, new Timestamp(user.getCreateDate()));
                int result = insert.executeUpdate();
                String operationResult;
                if(result != 0) {
                    operationResult = "User with login " + user.getLogin() + " is added";
                } else {
                    operationResult = "User with login " + user.getLogin() + " is not added";
                }
                return operationResult;
            }
        }
    }

    /**
     * Updates user in the database.
     *
     * @param user specified user
     * @return information whether operation succeeds or not
     * @throws SQLException thrown if problems with database connection occur
     */
    public String updateUser(User user) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement update =
                         connection.prepareStatement(UPDATE_USER)) {
                update.setString(1, user.getName());
                update.setString(2, user.getEmail());
                update.setString(3, user.getLogin());
                int result = update.executeUpdate();
                String operationResult;
                if(result != 0) {
                    operationResult = "User with login " + user.getLogin() + " is updated";
                } else {
                    operationResult = "User with login " + user.getLogin() + " is not found";
                }
                return operationResult;
            }
        }
    }

    /**
     * Deletes user from database.
     *
     * @param user specified item
     * @return information whether operation succeeds or not
     * @throws SQLException thrown if problems with database connection occur
     */
    public String deleteUser(User user) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement delete =
                         connection.prepareStatement(DELETE_USER)) {
                delete.setString(1, user.getLogin());
                int result = delete.executeUpdate();
                String operationResult;
                if(result != 0) {
                    operationResult = "User with login " + user.getLogin() + " is deleted";
                } else {
                    operationResult = "User with login " + user.getLogin() + " is not found";
                }
                return operationResult;
            }
        }
    }

    /**
     * Selects user with specified login.
     *
     * @param login specified login.
     * @return User with specified login or null if he doesn't exist
     * @throws SQLException thrown if problems with database connection occur
     */
    public User selectByLogin(String login) throws SQLException {
        User currentUser = null;
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
                            users.getTimestamp("create_time").getTime()
                    );
                    break;
                }
            }
        }
        return currentUser;
    }

    /**
     * Selects all users from database.
     *
     * @return list of all users
     * @throws SQLException thrown if problems with database connection occur
     */
    public List<User> selectAll() throws SQLException {
        User currentUser;
        List<User> result = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement selectAll =
                         connection.prepareStatement(SELECT_ALL_USERS);) {
                ResultSet users = selectAll.executeQuery();
                while (users.next()) {
                    currentUser = new User(
                            users.getString("name"),
                            users.getString("login"),
                            users.getString("email"),
                            users.getTimestamp("create_time").getTime()
                    );
                    result.add(currentUser);
                }
            }
        }
        return result;
    }
}
