package grigoryev.servlets;

import org.slf4j.Logger;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Provides dispatching to the right action.
 * Provides business logic of the web application.
 *
 * @author vgrigoryev
 * @version 1
 * @since 09.11.2017
 */
public class Model {
    /**
     * Operations which corresponds to different actions.
     */
    private final Map<Action, Function<User, String>> dispatch = new HashMap<>();
    /**
     * Logging.
     */
    private final Logger logger;
    /**
     * Request object.
     */
    private User user;

    /**
     * Constructor.
     * @param logger logger
     * @param user user
     */
    public Model(Logger logger, User user) {
        this.logger = logger;
        this.user = user;
    }

    /**
     * Inits model.
     * Loads actions and corresponding handlers.
     *
     * @return initialized model
     */
    public Model init() {
        this.load(Action.Insert, this.insertAction());
        this.load(Action.Delete, this.deleteAction());
        this.load(Action.Update, this.updateAction());
        this.load(Action.Select, this.selectAction());
        this.load(Action.Selectall, this.selectAllAction());
        return this;
    }

    /**
     * Loads action and corresponding handler.
     *
     * @param action action
     * @param handle action handler
     */
    public void load(Action action, Function<User, String> handle) {
        this.dispatch.put(action, handle);
    }

    /**
     * Runs handler determined
     * by action.
     *
     * @param action specified action
     * @return description of operation result
     */
    public String perform(Action action) {
        Function<User, String> userOperation = this.dispatch.get(action);
        if (userOperation != null) {
            return userOperation.apply(this.user);
        } else {
            throw new IllegalStateException("Could not find a handle for action");
        }
    }

    /**
     * Converts string to action enum value
     * if matches.
     *
     * @param action specified action in string representation
     * @return action which corresponds to one of the loaded actions
     */
    public static Action toAction(String action) {
        String firstLetter = action.trim().substring(0, 1).toUpperCase();
        String restLetters = action.trim().toLowerCase().substring(1);
        String act = firstLetter
                + restLetters;
        return Action.valueOf(act);
    }

    /**
     * Adds user in the database.
     *
     * @return function which returns information about addition operation
     * whether it succeeds or not
     */
    public Function<User, String> insertAction() {
        return (user) -> {
                String result = "Insertion error";
                try {
                    result = UserStorage.USER_STORAGE.insertUser(user);
                } catch (SQLException e) {
                    this.logger.error(e.getMessage(), e);
                }
                return result;
        };
    }
    /**
     * Deletes user in the database.
     *
     * @return function which returns information about deletion operation
     * whether it succeeds or not
     */
    public Function<User, String> deleteAction() {
        return (user) -> {
            String result = "Deletion error";
            try {
                result = UserStorage.USER_STORAGE.deleteUser(user);
            } catch (SQLException e) {
                this.logger.error(e.getMessage(), e);
            }
            return result;
        };
    }
    /**
     * Updates user in the database.
     *
     * @return function which returns information about update operation
     * whether it succeeds not
     */
    public Function<User, String> updateAction() {
        return (user) -> {
            String result = "Update error";
            try {
                result = UserStorage.USER_STORAGE.updateUser(user);
            } catch (SQLException e) {
                this.logger.error(e.getMessage(), e);
            }
            return result;
        };
    }
    /**
     * Selects user from the database.
     *
     * @return function which returns information about user if he exists
     */
    public Function<User, String> selectAction() {
        return (user) -> {
            User findUser = null;
            try {
                findUser = UserStorage.USER_STORAGE.selectByLogin(user.getLogin());
            } catch (SQLException e) {
                this.logger.error(e.getMessage(), e);
            }
            StringBuilder builder = new StringBuilder();
            builder.append("<table style=\"width:100%\">");
            builder.append("<tr>"
                    + "    <th>name</th>" + "    <th>login</th>"
                    + "    <th>email</th>" + "    <th>create date</th>" + "  </tr>");
            if (findUser != null) {
                builder.append("<tr>"
                        + "    <th>" + findUser.getName() + "</th>" + "<th>" + findUser.getLogin() + "</th>"
                        + "    <th>" + findUser.getEmail() + "</th>" + "<th>" + findUser.getCreateDate() + "</th>" + "  </tr>");
                builder.append("<br><br><br>");
            } else {
                builder.append("User is not found");
            }
            return builder.toString();
        };
    }
    /**
     * Selects all users from the database.
     *
     * @return function which returns information
     * about all users if any
     */
    public Function<User, String> selectAllAction() {
        return (user) -> {
            return "All users in the database";
        };
    }
}
