package grigoryev.models;

import java.util.List;

/**
 * User model in the database.
 *
 * @author vgrigoryev
 * @version 1
 * @since 01.12.2017
 */
public class User extends Model {

    private static final long serialVersionUID = -7815469742535494172L;
    /**
     * User's name.
     */
    private String name;
    /**
     * User's login.
     */
    private String login;
    /**
     * User's password.
     */
    private String password;
    /**
     * User's email.
     */
    private String email;
    /**
     * User's role.
     */
    private Role role;
    /**
     * User's address.
     */
    private Address address;
    /**
     * Music types user prefers.
     */
    private List<MusicType> musicTypes;

    /**
     * Gets user's address.
     * @return user's address
     */
    public Address getAddress() {
        return address;
    }

    /**
     * Sets user's address.
     * @param address user's address
     */
    public void setAddress(Address address) {
        this.address = address;
    }
    /**
     * Gets user's music types.
     * @return user's music types
     */
    public List<MusicType> getMusicTypes() {
        return musicTypes;
    }

    /**
     * Sets user's music types.
     * @param musicTypes user's music types
     */
    public void setMusicTypes(List<MusicType> musicTypes) {
        this.musicTypes = musicTypes;
    }

    /**
     * Constructor.
     */
    public User() {
        super();
    }

    /**
     * Constructor.
     * @param id user's id.
     */
    public User(Integer id) {
        super(id);
    }

    /**
     * Gets user's name.
     * @return user's name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets user's name.
     * @param name user's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets user's login.
     * @return user's login.
     */
    public String getLogin() {
        return login;
    }

    /**
     * Sets user's login.
     * @param login user's login
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Gets user's password.
     * @return user's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets user's password.
     * @param password user's password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets user's email.
     * @return user's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets user's email.
     * @param email user's email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets user's role.
     * @return user's role
     */
    public Role getRole() {
        return role;
    }

    /**
     * Sets user's role.
     * @param role user's role
     */
    public void setRole(Role role) {
        this.role = role;
    }
}
