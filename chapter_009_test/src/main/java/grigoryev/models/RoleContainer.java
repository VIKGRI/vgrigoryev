package grigoryev.models;

import grigoryev.dao.DatabaseDAOException;
import grigoryev.dao.implement.RoleDatabaseDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Container holds single object
 * for each role.
 *
 * @author vgrigoryev
 * @version 1
 * @since 01.12.2017
 */
public class RoleContainer {
    /**
     * Single instance.
     */
    private static final RoleContainer INSTANCE = new RoleContainer();
    /**
     * Logger.
     */
    private static final Logger ROLE_CONTAINER_LOGGER = LoggerFactory.getLogger(RoleContainer.class);
    /**
     * All roles available in the app.
     */
    private Map<String, Role> roles = new HashMap<>();

    /**
     * Constructor.
     */
    private RoleContainer() {
        try {
            for (Role role: RoleDatabaseDao.getInstance().selectAll()) {
                roles.put(role.getName().toLowerCase(), role);
            }
        } catch (DatabaseDAOException e) {
            ROLE_CONTAINER_LOGGER.error(e.getMessage(), e);
        }
    }

    /**
     * Checks whether new roles've been added in the database before retrieve the instance.
     */
    private void updateRolesState() {
        try {
            for (Role role: RoleDatabaseDao.getInstance().selectAll()) {
                roles.putIfAbsent(role.getName().toLowerCase(), role);
            }
        } catch (DatabaseDAOException e) {
            ROLE_CONTAINER_LOGGER.error(e.getMessage(), e);
        }
    }
    /**
     * Gets instance.
     * @return instance of RoleContainer
     */
    public static RoleContainer getInstance() {
        //INSTANCE.updateRolesState();
        return INSTANCE;
    }

    /**
     * Gets role by it's name if such a role exists in the app.
     * @param roleName specified role name
     * @return Role with the specified name or null
     */
    public Role getRole(String roleName) {
        return this.roles.get(roleName.toLowerCase());
    }

    /**
     * Gets names of all roles in the app.
     * @return names of all roles
     */
    public List<String> getRoleNames() {
        return new ArrayList<>(this.roles.keySet());
    }
}
