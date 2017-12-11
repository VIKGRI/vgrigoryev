package grigoryev.dao.implement;

import grigoryev.controllers.Action;
import grigoryev.controllers.ActionDispatcher;
import grigoryev.controllers.ContextListener;
import grigoryev.controllers.DataSourceHolder;
import grigoryev.dao.DatabaseDAOException;
import grigoryev.dao.RoleDao;
import grigoryev.models.Role;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.*;

/**
 * Provides operations for database
 * which stores roles. Implemented using
 * singleton pattern.
 *
 * @author vgrigoryev
 * @version 1
 * @since 29.11.2017
 */
public class RoleDatabaseDao implements RoleDao {
    /**
     * Provides creation of only one instance per app.
     * Implements singleton pattern.
     */
    private static final RoleDatabaseDao INSTANCE
            = new RoleDatabaseDao(DataSourceHolder.getInstance());
    /**
     * Datasource which provides connection pooling.
     */
    private DataSource dataSource;
    /**
     * Logger.
     */
    private static final Logger ROLE_DAO_LOGGER = LoggerFactory.getLogger(RoleDatabaseDao.class);

    /**
     * Constructor.
     *
     * @param ds datasource which provides connection pooling
     */
    private RoleDatabaseDao(DataSource ds) {
        this.dataSource = ds;
    }

    /**
     * Gets instance of RoleDatabaseDao.
     *
     * @return RoleDatabaseDao object
     */
    public static RoleDatabaseDao getInstance() {
        return INSTANCE;
    }

    @Override
    public Role getById(Integer id) throws DatabaseDAOException {
        String sql = "SELECT * FROM roles WHERE id = (?)";
        Role role = null;
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement =
                         connection.prepareStatement(sql)) {
                statement.setInt(1, id);
                ResultSet roles = statement.executeQuery();
                if (roles.next()) {
                    role = new Role(id);
                    role.setName(roles.getString("name"));
                    role.setDescription(roles.getString("description"));
                }
            }
        } catch (SQLException e) {
            ROLE_DAO_LOGGER.error("Select role by id exception", e);
            throw new DatabaseDAOException("Select role by id exception", e);
        }
        return role;
    }

    @Override
    public List<Role> selectAll() throws DatabaseDAOException {
        String sql = "SELECT r.id, r.name, r.description, p.privilege_name "
                + " FROM roles AS r "
                + " JOIN roles_and_privileges AS rap ON rap.role_id = r.id "
                + " JOIN privileges AS p ON p.id = rap.privilege_id ORDER BY r.id";
        Map<String, Role> roles = new HashMap<>();
        try (Connection connection = dataSource.getConnection()) {
            try (Statement statement =
                         connection.createStatement()) {
                ResultSet result = statement.executeQuery(sql);
                while (result.next()) {
                    Integer id = result.getInt("id");
                    String name = result.getString("name");
                    String description = result.getString("description");
                    String privilege = result.getString("privilege_name");
                    Role role = new Role(id);
                    role.setName(name);
                    role.setDescription(description);
                    Action action = ActionDispatcher.toAction(privilege);
                    role.addPrivileges(ActionDispatcher.toAction(privilege));

                    if (roles.containsKey(name)) {
                        roles.get(name).addPrivileges(action);
                    } else {
                        role.addPrivileges(action);
                        roles.put(name, role);
                    }
                }
            }
        } catch (SQLException e) {
            ROLE_DAO_LOGGER.error("Select all roles exception", e);
            throw new DatabaseDAOException("Select all roles exception", e);
        }
        return new ArrayList<>(roles.values());
    }

    @Override
    public Role insert(Role item) throws DatabaseDAOException {
        String sql = "INSERT INTO roles (name, description) VALUES ((?), (?))";
        Role role = null;
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement =
                         connection.prepareStatement(sql)) {
                statement.setString(1, item.getName());
                statement.setString(2, item.getDescription());
                statement.executeUpdate();
                // Gets result set with generated id
                ResultSet genId = statement.getGeneratedKeys();
                if (genId.next()) {
                    Integer id = genId.getInt("id");
                    role = item;
                    role.setId(id);
                }
            }
        } catch (SQLException e) {
           ROLE_DAO_LOGGER.error("Insert role exception", e);
            throw new DatabaseDAOException("Insert role exception", e);
        }
        return role;
    }

    @Override
    public void update(Role item) throws DatabaseDAOException {
        String sql = "UPDATE roles SET  name = (?) description = (?) WHERE id = (?)";
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement =
                         connection.prepareStatement(sql)) {
                statement.setString(1, item.getName());
                statement.setString(2, item.getDescription());
                statement.setInt(3, item.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            ROLE_DAO_LOGGER.error("Update role exception", e);
            throw new DatabaseDAOException("Update role exception", e);
        }
    }

    @Override
    public void delete(Role item) throws DatabaseDAOException {
        String sql = "DELETE FROM roles WHERE id = (?)";
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement =
                         connection.prepareStatement(sql)) {
                statement.setInt(1, item.getId());
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            ROLE_DAO_LOGGER.error("Delete role exception", e);
            throw new DatabaseDAOException("Delete role exception", e);
        }
    }
}
