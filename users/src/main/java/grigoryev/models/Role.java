package grigoryev.models;

import grigoryev.controllers.Action;

import java.util.HashSet;
import java.util.Set;

/**
 * Role model in the database.
 *
 * @author vgrigoryev
 * @version 1
 * @since 01.12.2017
 */
public class Role extends Model {

    private static final long serialVersionUID = 5499386488285619685L;
    /**
     * Role's name.
     */
    private String name;
    /**
     * Role's description.
     */
    private String description;

    /**
     * Available privileges for this role.
     */
    private Set<Action> privileges;

    /**
     * Gets all privileges available for this role.
     *
     * @return all available privileges
     */
    public Set<Action> getPrivileges() {
        return privileges;
    }

    /**
     * Sets privileges available for this role.
     *
     * @return available privileges
     */
    public void setPrivileges(Set<Action> privileges) {
        this.privileges = privileges;
    }

    /**
     * Constructor.
     */
    public Role() {
        this.privileges = new HashSet<>();
    }

    /**
     * Constructor.
     *
     * @param id role's id
     */
    public Role(Integer id) {
        super(id);
        this.privileges = new HashSet<>();
    }

    /**
     * Gets role's name.
     *
     * @return role's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets role's name.
     *
     * @param name role's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets role's description.
     *
     * @return role's description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets role's description.
     *
     * @param description role's description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Adds privilege to the role
     *
     * @param privilege new privilege
     * @return true if it's added
     */
    public boolean addPrivileges(Action privilege) {
        return this.privileges.add(privilege);
    }

    /**
     * Removes privilege from the role.
     *
     * @param privilege specified privilege
     * @return true if it's deleted
     */
    public boolean removePrivileges(Action privilege) {
        return this.privileges.remove(privilege);
    }

    /**
     * Determines whether this action (which is a privilege)
     * is available for this role or not.
     *
     * @param action specified action
     * @return true if it's available
     */
    public boolean isActionAvailable(Action action) {
        return this.privileges.contains(action);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Role role = (Role) o;

        return name != null ? name.equals(role.name) : role.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
