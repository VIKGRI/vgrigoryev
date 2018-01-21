package grigoryev.dao;

/**
 * Exception which is thrown when
 * problems with database operations occur.
 *
 * @author vgrigoryev
 * @since 29.11.2017
 * @version 1
 */
public class DatabaseDAOException extends Exception {
    /**
     * Constructor.
     */
    public DatabaseDAOException() {
        super();
    }

    /**
     * Constructor.
     *
     * @param message brief description
     * @param cause initial exception
     */
    public DatabaseDAOException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor.
     *
     * @param message brief description
     */
    public DatabaseDAOException(String message) {
        super(message);
    }
}
