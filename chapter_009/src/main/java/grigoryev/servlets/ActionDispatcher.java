package grigoryev.servlets;

import org.slf4j.Logger;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Class provides actions dispatching.
 * @author vgrigoryev
 * @since 08.11.2017
 * @version 1
 */
public class ActionDispatcher {
    /**
     * Operations which corresponds to different actions.
     */
    private final Map<Action, Function<User, String>> dispatch = new HashMap<>();
    /**
     * Provides operations with database.
     */
    private final UserDataBaseManager dataBaseManager;
    /**
     * Logging.
     */
    private final Logger logger;

    /**
     * Constructor.
     *
     * @param dataBaseManager database
     * @param logger logger
     */
    public ActionDispatcher(UserDataBaseManager dataBaseManager, Logger logger) {
        this.dataBaseManager = dataBaseManager;
        this.logger = logger;
    }

    /**
     * Inits dispatcher.
     * Loads actions and corresponding handlers.
     *
     * @return initialized dispatcher
     */
    public ActionDispatcher init() {
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
     * @param user specified user
     * @return operation result
     */
    public String perform(Action action, User user) {
        Function<User, String> userOperation = this.dispatch.get(action);
        String operationResult;
        if (userOperation != null) {
            operationResult = userOperation.apply(user);
        } else {
            throw new IllegalStateException("Could not find a handle for action");
        }
        return operationResult;
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
        StringBuilder act = new StringBuilder();
        act.append(firstLetter);
        act.append(restLetters);
        return Action.valueOf(act.toString());
    }

    /**
     * Adds user in the database.
     *
     * @return function which takes one parameter User
     * and returns information about addition operation
     * whether it succeeds or does not
     */
    public Function<User, String> insertAction() {
        return user -> {
            String result = "Insertion error";
            try {
                result = this.dataBaseManager.insertUser(user);
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
            }
            return result;
        };
    }
    /**
     * Deletes user in the database.
     *
     * @return function which takes one parameter User
     * and returns information about deletion operation
     * whether it succeeds or does not
     */
    public Function<User, String> deleteAction() {
        return user -> {
            String result = "Deletion error";
            try {
                result = this.dataBaseManager.deleteUser(user);
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
            }
            return result;
        };
    }
    /**
     * Updates user in the database.
     *
     * @return function which takes one parameter User
     * and returns information about update operation
     * whether it succeeds or does not
     */
    public Function<User, String> updateAction() {
        return user -> {
            String result = "Update error";
            try {
                result = this.dataBaseManager.updateUser(user);
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
            }
            return result;
        };
    }
    /**
     * Selects user from the database.
     *
     * @return function which takes one parameter User
     * and returns information about user if he exists
     */
    public Function<User, String> selectAction() {
        return user -> {
            User findUser = null;
            try {
                findUser = this.dataBaseManager.selectByLogin(user.getLogin());
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
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
        return user -> {
            StringBuilder builder = new StringBuilder();
            try {
                List<User> users = this.dataBaseManager.selectAll();
                builder.append("<table style=\"width:100%\">");
                builder.append("<tr>"
                        + "    <th>name</th>" + "    <th>login</th>"
                        + "    <th>email</th>" + "    <th>create date</th>" + "  </tr>");
                for (User item : users) {
                    builder.append("<tr>"
                            + "    <th>" + item.getName() + "</th>" + "<th>" + item.getLogin() + "</th>"
                            + "    <th>" + item.getEmail() + "</th>" + "<th>" + item.getCreateDate() + "</th>" + "  </tr>");
                }
                if (users.size() == 0) {
                    builder.append("There is no users in the database");
                }
            } catch (SQLException e) {
                logger.error(e.getMessage(), e);
            }
            return builder.toString();
        };
    }
}