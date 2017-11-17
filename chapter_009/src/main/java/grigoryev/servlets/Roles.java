package grigoryev.servlets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents all user roles in the system
 * which are available in the application.
 * Implemented using singleton pattern.
 *
 * @author vgrigoryev
 * @version 1
 * @since 16.11.2017
 */
class Roles {
    /**
     * Single instance.
     */
    private static final Roles INSTANCE = new Roles();
    /**
     * Logger.
     */
    private static final Logger ROLE_LOGGER = LoggerFactory.getLogger(Roles.class);
    /**
     * All roles available in the app.
     */
    private Map<String, Role> roles = new HashMap<>();

    /**
     * Constructor.
     */
    private Roles() {
        try {
            roles = UserStorage.USER_STORAGE.selectRoles();
        } catch (UserStorageDAOException e) {
            ROLE_LOGGER.error(e.getMessage(), e);
        }
    }

    /**
     * Gets instance.
     * @return instance of Roles
     */
    static Roles getInstance() {
        return INSTANCE;
    }

    /**
     * Adds role and corresponding privilege. If there's no role
     * with specified name then adds new Role with specified action
     * as privilege. Otherwise add privilege to present role.
     *
     * @param role specified role
     * @param action specified action
     * @return true if operation succeeds
     */
    boolean addRoleAndPrivilege(Role role, Action action) {
        boolean isAdded = false;
        try {
            if (UserStorage.USER_STORAGE.bindRoleAndPrivilege(role.getName(), action).endsWith("is added to role")) {
                isAdded = this.roles.putIfAbsent(role.getName(), role) == null;
            } else {
                isAdded = this.roles.get(role.getName()).addPrivileges(action);
            }
        } catch (UserStorageDAOException e) {
            ROLE_LOGGER.error(e.getMessage(), e);
        }
        return isAdded;
    }

    /**
     * Gets role by it's name if such a role exists in the app.
     * @param roleName specified role name
     * @return Role with the specified name or null
     */
    Role getRole(String roleName) {
        return this.roles.get(roleName.toLowerCase());
    }

    /**
     * Gets names of all roles in the app.
     * @return names of all roles
     */
    List<String> getRoleNames() {
        return new ArrayList<>(this.roles.keySet());
    }
}
