package ru.grigoryev.analysis;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Properties;

/**
 * Class represents database which stores information about
 * items in the tracker.
 *
 * @author vgrigoryev
 * @version 1
 * @since 28.10.2017
 */
public class DBManager {
    /**
     * Request for inserting author in the authors table .
     */
    private static final String INSERT_AUTHOR =
            "INSERT INTO authors (nick_name, author_info_ref) VALUES (?, ?) ON CONFLICT DO NOTHING";
    /**
     * Request for inserting vacancy in the job_vacancies.
     */
    private static final String INSERT_VACANCY =
            "INSERT INTO job_vacancies (vacancy_message, vacancy_desc_ref, "
                    + "author, date) VALUES (?, ?, ?, ?) ON CONFLICT DO NOTHING";
    /**
     * Request for getting date and time
     * for the last job posting added in the database.
     */
    private static final String LAST_VACANCY_MESSAGE_DATE =
            "SELECT date FROM job_vacancies WHERE date IN (SELECT MAX(date) FROM job_vacancies)";
    /**
     * Connection to database.
     */
    private Connection connection;

    /**
     * Provides connection to the database and configurating it's
     * properties. Also creates database if it is not exists.
     *
     * @throws IOException  thrown if problems with file reading occur
     * @throws SQLException thrown if problems with database connection occur
     */
    public void connectDataBase() throws SQLException, IOException {
        // Setting connection to database
        this.setDataBaseConnectionProperties();
        //Reading and executing sql create authors request
        this.readAndExecuteSqlScript("createAuthors.sql");
        //Reading and executing sql create table request
        this.readAndExecuteSqlScript("createTable.sql");
    }

    /**
     * Reads file and executes sql script.
     * @param path specified file path
     * @throws SQLException thrown if problems related to reading file occur
     * @throws IOException thrown if problems related to executing query occur
     */
    private void readAndExecuteSqlScript(String path) throws SQLException, IOException {
        StringBuilder script = new StringBuilder();
        try (BufferedReader reader = new BufferedReader((
                new FileReader(path)))) {
            String line;
            while ((line = reader.readLine()) != null) {
                script.append(line);
                if (line.endsWith(";")) {
                    break;
                }
            }
        }
        PreparedStatement statement = connection.prepareStatement(script.toString());
        statement.execute();
    }

    /**
     * Sets connection properties from file.
     * @throws IOException thrown if problems related to reading file occur
     * @throws SQLException thrown if problems related to executing query occur
     */
    private void setDataBaseConnectionProperties() throws IOException, SQLException {
        Properties properties = new Properties();
        try (InputStream in =
                     Files.newInputStream(Paths.get("database.properties"))) {
            properties.load(in);
        }
        String drivers = properties.getProperty("jdbc.drivers");
        if (drivers != null) {
            System.setProperty("jdbc.drivers", drivers);
        }
        String url = properties.getProperty("jdbc.url");
        String username = properties.getProperty("jdbc.username");
        String password = properties.getProperty("jdbc.password");
        this.connection = DriverManager.getConnection(url, username, password);
    }

    /**
     * Closes database connection.
     */
    public void closeConnection() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    /**
     * Adds author to the database.
     *
     * @param nickName author nick name
     * @param authorInfoReference reference to the information about author
     */
    public void insertAuthor(String nickName, String authorInfoReference) throws SQLException {
        try (PreparedStatement insert =
                     connection.prepareStatement(INSERT_AUTHOR);) {
            insert.setString(1, nickName);
            insert.setString(2, authorInfoReference);
            insert.executeUpdate();
        }
    }

    /**
     * Inserts vacancy to the database.
     *
     * @param vacancyMessage job posting message from SQL.ru
     * @param descriptionReference reference to the vacancy description
     * @param author author nick name
     * @param date job posting date
     */
    public void insertVacancy(String vacancyMessage, String descriptionReference,
                                String author, Timestamp date) throws SQLException {
        try (PreparedStatement insert =
                     connection.prepareStatement(INSERT_VACANCY);) {
            insert.setString(1, vacancyMessage);
            insert.setString(2, descriptionReference);
            insert.setString(3, author);
            insert.setTimestamp(4, date);
            insert.executeUpdate();
        }
    }

    /**
     * Executes sql sqript from specified file
     * in order to delete invalid rows.
     * @param filterScript specified file which contains sql script
     * @throws IOException thrown if problems related to reading file occur
     * @throws SQLException thrown if problems related to executing query occur
     */
    public void filterVacancies(String filterScript) throws IOException, SQLException {
        this.readAndExecuteSqlScript(filterScript);
    }

    /**
     * Returns the date which corresponds to the
     * last added vacance.
     * @return date of the last added vacancy
     * @throws SQLException thrown if problems related to executing query occur
     */
    public Timestamp updateLastMessageTime() throws SQLException {
        Timestamp time = null;
        try (PreparedStatement statement =
                connection.prepareStatement(LAST_VACANCY_MESSAGE_DATE)) {
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                time = result.getTimestamp("date");
            }
        }
        return time;
    }
}
