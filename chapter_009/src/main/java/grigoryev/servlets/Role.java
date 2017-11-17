package grigoryev.servlets;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents user roles in the system.
 * Different roles have different privileges.
 *
 * @author vgrigoryev
 * @version 1
 * @since 16.11.2017
 */
public class Role {
    /**
     * Role's name.
     */
    private String name;
    /**
     * Available privileges for this role.
     */
    private Set<Action> privileges;

    /**
     * Constructor.
     * @param name role's name.
     */
    public Role(String name) {
        this.name = name.toLowerCase();
        this.privileges = new HashSet<>();
    }

    /**
     * Gets role's name.
     * @return role's name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets all privileges available for this role.
     * @return all available privileges
     */
    public Set<Action> getPrivileges() {
        return this.privileges;
    }

    /**
     * Sets role's name.
     * @param name role's name
     */
    public void setName(String name) {
        this.name = name.toLowerCase();
    }

    /**
     * Adds privilege to the role
     * @param privilege new privilege
     * @return true if it's added
     */
    public boolean addPrivileges(Action privilege) {
        return this.privileges.add(privilege);
    }

    /**
     * Removes privilege from the role.
     * @param privilege specified privilege
     * @return true if it's deleted
     */
    public boolean removePrivileges(Action privilege) {
        return this.privileges.remove(privilege);
    }

    /**
     * Determines whether this action (which is a privilege)
     * is available for this role or not.
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
