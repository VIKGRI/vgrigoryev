package grigoryev.controllers;

import grigoryev.dao.DatabaseDAOException;
import grigoryev.dao.implement.UserDataBaseDao;
import grigoryev.dao.repository.implement.GetUserByLoginSpecification;
import grigoryev.dao.repository.implement.GetUserByRoleSpecification;
import grigoryev.dao.repository.implement.GetUsersByAddressSpecification;
import grigoryev.dao.repository.implement.GetUsersByMusicTypeSpecification;
import grigoryev.models.Role;
import grigoryev.models.User;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
public class ActionDispatcher {
    /**
     * Operations which corresponds to different actions.
     */
    private final Map<Action, Function<User, List<User>>> dispatch = new HashMap<>();
    /**
     * Logging.
     */
    private final Logger logger;
    /**
     * Request object.
     */
    private User user;
    /**
     * Currently using the program user's role.
     */
    private Role onlineUserRole;

    /**
     * Constructor.
     *
     * @param logger logger
     * @param user   user
     */
    public ActionDispatcher(Logger logger, User user, Role onlineUserRole) {
        this.logger = logger;
        this.user = user;
        this.onlineUserRole = onlineUserRole;
    }

    /**
     * Initializes action dispatcher.
     * Loads actions and corresponding handlers.
     *
     * @return initialized action dispatcher
     */
    public ActionDispatcher init() {
        this.load(Action.Insert, this.insertAction());
        this.load(Action.Delete, this.deleteAction());
        this.load(Action.Update, this.updateAction());
        this.load(Action.Select_by_id, this.selectByIdAction());
        this.load(Action.Select_by_login, this.selectByLoginAction());
        this.load(Action.Select_by_role, this.selectByRoleAction());
        this.load(Action.Select_by_music_type, this.selectByMusicTypeAction());
        this.load(Action.Select_by_address, this.selectByAddressTypeAction());
        this.load(Action.Select_all, this.selectAllAction());
        return this;
    }

    /**
     * Loads action and corresponding handler.
     *
     * @param action action
     * @param handle action handler
     */
    void load(Action action, Function<User, List<User>> handle) {
        this.dispatch.put(action, handle);
    }

    /**
     * Runs handler determined
     * by action.
     *
     * @param action specified action
     * @return list of users as a result of action performed
     */
    public List<User> perform(Action action) {
        Function<User, List<User>> userOperation = this.dispatch.get(action);
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
     * @return function which performs insertion
     */
    Function<User, List<User>> insertAction() {
        return (user) -> {
            List<User> users = null;
            try {
                if (this.onlineUserRole.isActionAvailable(Action.Insert)) {
                    User userWithId = UserDataBaseDao.getInstance().insert(user);
                    users = new ArrayList<>();
                    users.add(userWithId);
                }
            } catch (DatabaseDAOException e) {
                this.logger.error(e.getMessage(), e);
            }
            return users;
        };
    }

    /**
     * Deletes user in the database.
     *
     * @return function which performs deletion
     */
    public Function<User, List<User>> deleteAction() {
        return (user) -> {
            List<User> users = null;
            try {
                if (this.onlineUserRole.isActionAvailable(Action.Delete)) {
                    UserDataBaseDao.getInstance().delete(user);
                    users = new ArrayList<>();
                    users.add(user);
                }
            } catch (DatabaseDAOException e) {
                this.logger.error(e.getMessage(), e);
            }
            return users;
        };
    }

    /**
     * Updates user in the database.
     *
     * @return function which updates user data
     */
    public Function<User, List<User>> updateAction() {
        return (user) -> {
            List<User> users = null;
            try {
                if (this.onlineUserRole.isActionAvailable(Action.Update) && this.onlineUserRole.equals(user.getRole())
                        || this.onlineUserRole.getName().equals("admin") || this.onlineUserRole.getName().equals("mandator")) {
                    UserDataBaseDao.getInstance().update(user);
                    users = UserDataBaseDao.getInstance().selectAll();
                }
            } catch (DatabaseDAOException e) {
                this.logger.error(e.getMessage(), e);
            }
            return users;
        };
    }

    /**
     * Selects user from the database.
     *
     * @return function which returns information about user by id
     */
    public Function<User, List<User>> selectByIdAction() {
        return (user) -> {
            List<User> users = null;
            try {
                User findUser = UserDataBaseDao.getInstance().getById(user.getId());
                users = new ArrayList<>();
                users.add(findUser);

            } catch (DatabaseDAOException e) {
                this.logger.error(e.getMessage(), e);
            }
            return users;
        };
    }

    /**
     * Selects user from the database.
     *
     * @return function which returns information about user by login
     */
    public Function<User, List<User>> selectByLoginAction() {
        return (user) -> {
            List<User> users = null;
            try {
                users = UserDataBaseDao.getInstance().query(new GetUserByLoginSpecification(user.getLogin()));
            } catch (DatabaseDAOException e) {
                this.logger.error(e.getMessage(), e);
            }
            return users;
        };
    }

    /**
     * Selects user from the database.
     *
     * @return function which returns information about user by role
     */
    public Function<User, List<User>> selectByRoleAction() {
        return (user) -> {
            List<User> users = null;
            try {
                users = UserDataBaseDao.getInstance().query(new GetUserByRoleSpecification(user.getRole()));
            } catch (DatabaseDAOException e) {
                this.logger.error(e.getMessage(), e);
            }
            return users;
        };
    }

    /**
     * Selects user from the database.
     *
     * @return function which returns information about user by music type
     */
    public Function<User, List<User>> selectByMusicTypeAction() {
        return (user) -> {
            List<User> users = null;
            try {
                users = UserDataBaseDao.getInstance().query(
                                new GetUsersByMusicTypeSpecification(user.getMusicTypes().get(0).getTitle())
                        );
            } catch (DatabaseDAOException e) {
                this.logger.error(e.getMessage(), e);
            }
            return users;
        };
    }

    /**
     * Selects user from the database.
     *
     * @return function which returns information about user by address
     */
    public Function<User, List<User>> selectByAddressTypeAction() {
        return (user) -> {
            List<User> users = null;
            try {
                users = UserDataBaseDao.getInstance().query(
                                new GetUsersByAddressSpecification(user.getAddress())
                        );
            } catch (DatabaseDAOException e) {
                this.logger.error(e.getMessage(), e);
            }
            return users;
        };
    }

    /**
     * Selects all users from the database.
     *
     * @return function which returns all users
     */
    public Function<User, List<User>> selectAllAction() {
        return (user) -> {
            List<User> users = null;
            try {
                users = UserDataBaseDao.getInstance().selectAll();
            } catch (DatabaseDAOException e) {
                this.logger.error(e.getMessage(), e);
            }
            return users;
        };
    }
}