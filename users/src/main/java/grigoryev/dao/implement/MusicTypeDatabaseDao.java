package grigoryev.dao.implement;

import grigoryev.controllers.ContextListener;
import grigoryev.controllers.DataSourceHolder;
import grigoryev.dao.DatabaseDAOException;
import grigoryev.dao.MusicTypeDao;
import grigoryev.models.MusicType;
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
 * which stores music types. Implemented using
 * singleton pattern.
 *
 * @author vgrigoryev
 * @version 1
 * @since 29.11.2017
 */
public class MusicTypeDatabaseDao implements MusicTypeDao {
    /**
     * Provides creation of only one instance per app.
     * Implements singleton pattern.
     */
    private static final MusicTypeDatabaseDao INSTANCE
            = new MusicTypeDatabaseDao(DataSourceHolder.getInstance());
    /**
     * Datasource which provides connection pooling.
     */
    private DataSource dataSource;
    /**
     * Logger.
     */
    private static final Logger MUSIC_TYPE_DAO_LOGGER = LoggerFactory.getLogger(MusicTypeDatabaseDao.class);

    /**
     * Constructor.
     *
     * @param ds datasource which provides connection pooling
     */
    private MusicTypeDatabaseDao(DataSource ds) {
        this.dataSource = ds;
    }

    /**
     * Gets instance of MusicTypeDatabaseDao.
     *
     * @return MusicTypeDatabaseDao object
     */
    public static MusicTypeDatabaseDao getInstance() {
        return INSTANCE;
    }

    @Override
    public MusicType getById(Integer id) throws DatabaseDAOException {
        String sql = "SELECT * FROM music_types WHERE id = (?)";
        MusicType musicType = null;
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement =
                         connection.prepareStatement(sql)) {
                statement.setInt(1, id);
                ResultSet musicTypes = statement.executeQuery();
                if (musicTypes.next()) {
                    musicType = new MusicType(id);
                    musicType.setTitle(musicTypes.getString("title"));
                    musicType.setDescription(musicTypes.getString("description"));
                }
            }
        } catch (SQLException e) {
            MUSIC_TYPE_DAO_LOGGER.error("Select music type by id exception", e);
            throw new DatabaseDAOException("Select music type by id exception", e);
        }
        return musicType;
    }

    @Override
    public List<MusicType> selectAll() throws DatabaseDAOException {
        String sql = "SELECT * FROM music_types";
        List<MusicType> musicTypes = new ArrayList<>();
        try (Connection connection = dataSource.getConnection()) {
            try (Statement statement =
                         connection.createStatement()) {
                ResultSet result = statement.executeQuery(sql);
                while (result.next()) {
                    Integer id = result.getInt("id");
                    String title = result.getString("title");
                    String description = result.getString("description");
                    MusicType musicType = new MusicType(id);
                    musicType.setTitle(title);
                    musicType.setDescription(description);
                    musicTypes.add(musicType);
                }
            }
        } catch (SQLException e) {
            MUSIC_TYPE_DAO_LOGGER.error("Select all music types exception", e);
            throw new DatabaseDAOException("Select all music types exception", e);
        }
        return musicTypes;
    }

    @Override
    public MusicType insert(MusicType item) throws DatabaseDAOException {
        String sql = "INSERT INTO music_types (title, description) VALUES ((?), (?))";
        MusicType musicType = null;
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement =
                         connection.prepareStatement(sql)) {
                statement.setString(1, item.getTitle());
                statement.setString(2, item.getDescription());
                statement.executeUpdate();
                // Gets result set with generated id
                ResultSet genId = statement.getGeneratedKeys();
                if (genId.next()) {
                    Integer id = genId.getInt("id");
                    musicType = item;
                    musicType.setId(id);
                }
            }
        } catch (SQLException e) {
            MUSIC_TYPE_DAO_LOGGER.error("Insert music type exception", e);
            throw new DatabaseDAOException("Insert music type exception", e);
        }
        return musicType;
    }

    @Override
    public void update(MusicType item) throws DatabaseDAOException {
        String sql = "UPDATE music_types SET  title = (?) description = (?) WHERE id = (?)";
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement =
                         connection.prepareStatement(sql)) {
                statement.setString(1, item.getTitle());
                statement.setString(2, item.getDescription());
                statement.setInt(3, item.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            MUSIC_TYPE_DAO_LOGGER.error("Update music type exception", e);
            throw new DatabaseDAOException("Update music type exception", e);
        }
    }

    @Override
    public void delete(MusicType item) throws DatabaseDAOException {
        String sql = "DELETE FROM music_types WHERE id = (?)";
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement =
                         connection.prepareStatement(sql)) {
                statement.setInt(1, item.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            MUSIC_TYPE_DAO_LOGGER.error("Delete music type exception", e);
            throw new DatabaseDAOException("Delete music type exception", e);
        }
    }
}
