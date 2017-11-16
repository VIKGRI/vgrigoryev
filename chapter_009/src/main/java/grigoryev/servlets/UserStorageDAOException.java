package grigoryev.servlets;

/**
 * Exception which is thrown when
 * problems with database operations occur.
 *
 * @author vgrigoryev
 * @since 16.11.2017
 * @version 1
 */
public class UserStorageDAOException extends Exception {
    /**
     * Constructor.
     */
    public UserStorageDAOException() {
        super();
    }

    /**
     * Constructor.
     *
     * @param message brief description
     * @param cause initial exception
     */
    public UserStorageDAOException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor.
     *
     * @param message brief description
     */
    public UserStorageDAOException(String message) {
        super(message);
    }
}
