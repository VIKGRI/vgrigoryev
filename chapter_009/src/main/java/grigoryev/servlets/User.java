package grigoryev.servlets;

/**
 *Class represent the user.
 *@author vgrigoryev
 *@since 02.11.2017
 *@version 1
 */
public class User {
    /**
     * User's name.
     */
    private String name;
    /**
     * User's login.
     */
    private String login;
    /**
     * User's email.
     */
    private String email;
    /**
     * User's create date.
     */
    private long createTime;
    /**
     * User's password.
     */
    private String password;
    /**
     * User's city.
     */
    private String city;

    /**
     * User's country.
     */
    private String country;
    /**
     * User's role.
     */
    private Role role;

    /**
     * Constructor.
     *
     * @param name user's name
     * @param login user's login
     * @param email user's email
     * @param createTime date and time when user was created
     * @param password user's password
     * @param role user's role
     */
    public User(String name, String login, String email, long createTime, String password, Role role) {
        this.name = name;
        this.login = login;
        this.email = email;
        this.createTime = createTime;
        this.password = password;
        this.role = role;
    }

    /**
     * Gets user's name.
     * @return user's name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets user's login.
     * @return user's login
     */
    public String getLogin() {
        return login;
    }

    /**
     * Gets user's email.
     * @return user's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Gets user's create time.
     * @return user's create time
     */
    public long getCreateDate() {
        return createTime;
    }

    /**
     * Sets user's name.
     * @param name user's name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets user's login.
     * @param login user's login
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Sets user's email.
     * @param email user's email
     */
    public void setEmail(String email) {
        this.email = email;
    }
    /**
     * Sets user's create time.
     * @return user's create time
     */
    public void setCreateTime(long createTime) {
        this.createTime = createTime;
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
     * Gets user's role.
     *
     * @return user's role
     */
    public Role getRole() {
        return role;
    }

    /**
     * Sets user role.
     *
     * @param role user's role
     */
    public void setRole(Role role) {
        this.role = role;
    }


    /**
     * Gets user's city.
     * @return user's city
     */
    public String getCity() {
        return city;
    }

    /**
     * Gets user's country.
     * @return user's country
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets user's city.
     * @param city user's city
     */
    public void setCity(String city) {

        this.city = city;
    }

    /**
     * Sets user's country.
     * @param country user's country
     */
    public void setCountry(String country) {
        this.country = country;
    }
}
