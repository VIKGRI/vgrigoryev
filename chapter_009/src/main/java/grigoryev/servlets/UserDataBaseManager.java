package grigoryev.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Class represents database which is used
 * for storing users. Provides methods for
 * adding, deleting, updating, ect.
 *
 *@author vgrigoryev
 *@since 02.11.2017
 *@version 1
 */
public class UserDataBaseManager {
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
     * Connection to database.
     */
    private Connection connection;

    /**
     * Provides connection to the database and configuring it's
     * properties. Also creates database if it is not exists.
     *
     * @throws IOException  thrown if problems with file reading occur
     * @throws SQLException thrown if problems with database connection occur
     * @throws ClassNotFoundException thrown if problems with driver class searching occur
     */
    public void connectDataBase() throws SQLException, IOException, ClassNotFoundException {
        // Setting connection to database
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Properties properties = new Properties();
        try (InputStream in =
                     classLoader.getResourceAsStream("database.properties")) {
            properties.load(in);
        }
        String drivers = properties.getProperty("jdbc.drivers");
        Class.forName(drivers);
        String url = properties.getProperty("jdbc.url");
        System.out.println(url);
        String username = properties.getProperty("jdbc.username");
        System.out.println(username);
        String password = properties.getProperty("jdbc.password");
        System.out.println(password);
        this.connection = DriverManager.getConnection(url, username, password);
    }

    /**
     * Creates table for storing users
     * if it doesn't exist already.
     *
     * @throws SQLException thrown if problems with database connection occur
     */
    public void createTable() throws SQLException {
        String request = "CREATE TABLE IF NOT EXISTS users"
                + "(name VARCHAR(50), login VARCHAR(50) PRIMARY KEY NOT NULL,"
                + "email VARCHAR(50), create_time TIMESTAMP )";
        PreparedStatement createTable = connection.prepareStatement(request);
        createTable.execute();
    }

    /**
     * Closes database connection.
     */
    public void closeConnection() throws SQLException {
        if (this.connection != null) {
            this.connection.close();
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
        try (PreparedStatement insert =
                     this.connection.prepareStatement(INSERT_USER)) {
            insert.setString(1, user.getName());
            insert.setString(2, user.getLogin());
            insert.setString(3, user.getEmail());
            insert.setTimestamp(4, new Timestamp(user.getCreateDate()));
            int result = insert.executeUpdate();
            String operationResult;
            if(result != 0) {
                operationResult = "User with " + user.getLogin() + " is added";
            } else {
                operationResult = "User with " + user.getLogin() + " is not added";
            }
            return operationResult;
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
        try (PreparedStatement update =
                     connection.prepareStatement(UPDATE_USER)) {
            update.setString(1, user.getName());
            update.setString(2, user.getEmail());
            update.setString(3, user.getLogin());
            int result = update.executeUpdate();
            String operationResult;
            if(result != 0) {
                operationResult = "User with " + user.getLogin() + " is updated";
            } else {
                operationResult = "User with " + user.getLogin() + " is not found";
            }
            return operationResult;
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
        try (PreparedStatement delete =
                     connection.prepareStatement(DELETE_USER)) {
            delete.setString(1, user.getLogin());
            int result = delete.executeUpdate();
            String operationResult;
            if(result != 0) {
                operationResult = "User with " + user.getLogin() + " is deleted";
            } else {
                operationResult = "User with " + user.getLogin() + " is not found";
            }
            return operationResult;
        }
    }

    /**
     * Selects user with specified login.
     *
     * @param login specified login.
     * @return User with specified login or null if he doesn't exist
     */
    public User selectByLogin(String login) throws SQLException {
        User currentUser = null;
        try (PreparedStatement selectByLogin =
                     connection.prepareStatement(SELECT_BY_LOGIN)) {
            selectByLogin.setString(1, login);
            ResultSet users = selectByLogin.executeQuery();
            if (users.next()) {
                currentUser = new User(
                        users.getString("name"),
                        login,
                        users.getString("email"),
                        users.getTimestamp("create_time").getTime()
                );
            }
        }
        return currentUser;
    }

    /**
     * Selects all users from database.
     *
     * @return list of all users
     */
    public List<User> selectAll() throws SQLException {
        User currentUser;
        List<User> result = new ArrayList<>();
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
        return result;
    }
}
