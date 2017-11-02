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
     * Constructor.
     *
     * @param name user's name
     * @param login user's login
     * @param email user's email
     * @param createTime date and time when user was created
     */
    public User(String name, String login, String email, long createTime) {
        this.name = name;
        this.login = login;
        this.email = email;
        this.createTime = createTime;
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
}
