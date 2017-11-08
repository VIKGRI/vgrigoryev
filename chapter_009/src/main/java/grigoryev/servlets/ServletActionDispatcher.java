package grigoryev.servlets;

import org.slf4j.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * Provides insertion of user from database.
 * After that forwards request to servlet which
 * forms result respond.
 *
 * @author vgrigoryev
 * @since 08.11.2017
 * @version 1
 */
public class ServletActionDispatcher {

    /**
     * Operations which corresponds to different actions.
     */
    private final Map<Action, BiConsumer<HttpServletRequest, HttpServletResponse>> dispatch = new HashMap<>();
    /**
     * Request object.
     */
    private HttpServletRequest request;
    /**
     * Response object.
     */
    private HttpServletResponse response;
    /**
     * Logging.
     */
    private final Logger logger;

    /**
     * Constructor
     *
     * @param request request passed on from servlet
     * @param response response passed on from servlet
     * @param logger logger
     */
    public ServletActionDispatcher(HttpServletRequest request, HttpServletResponse response, Logger logger) {
        this.request = request;
        this.response = response;
        this.logger = logger;
    }

    /**
     * Inits dispatcher.
     * Loads actions and corresponding handlers.
     *
     * @return initialized dispatcher
     */
    public ServletActionDispatcher init() {
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
    public void load(Action action, BiConsumer<HttpServletRequest, HttpServletResponse> handle) {
        this.dispatch.put(action, handle);
    }

    /**
     * Runs handler determined
     * by action.
     *
     * @param action specified action
     */
    public void perform(Action action) {
        BiConsumer<HttpServletRequest, HttpServletResponse> userOperation = this.dispatch.get(action);
        if (userOperation != null) {
            userOperation.accept(this.request, this.response);
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
        String act = firstLetter +
                restLetters;
        return Action.valueOf(act);
    }

    /**
     * Adds user in the database.
     *
     * @return function which returns information about addition operation
     * whether it succeeds or not
     */
    public BiConsumer<HttpServletRequest, HttpServletResponse> insertAction() {
        return (request, response) -> {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/InsertUser");
            try {
                dispatcher.forward(request, response);
            } catch (ServletException | IOException e) {
                logger.error(e.getMessage(), e);
            }
        };
    }
    /**
     * Deletes user in the database.
     *
     * @return function which returns information about deletion operation
     * whether it succeeds or not
     */
    public BiConsumer<HttpServletRequest, HttpServletResponse> deleteAction() {
        return (request, response) -> {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/DeleteUser");
            try {
                dispatcher.forward(request, response);
            } catch (ServletException | IOException e) {
                logger.error(e.getMessage(), e);
            }
        };
    }
    /**
     * Updates user in the database.
     *
     * @return function which returns information about update operation
     * whether it succeeds not
     */
    public BiConsumer<HttpServletRequest, HttpServletResponse> updateAction() {
        return (request, response) -> {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/UpdateUser");
            try {
                dispatcher.forward(request, response);
            } catch (ServletException | IOException e) {
                logger.error(e.getMessage(), e);
            }
        };
    }
    /**
     * Selects user from the database.
     *
     * @return function which returns information about user if he exists
     */
    public BiConsumer<HttpServletRequest, HttpServletResponse> selectAction() {
        return (request, response) -> {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/SelectUser");
            try {
                dispatcher.forward(request, response);
            } catch (ServletException | IOException e) {
                logger.error(e.getMessage(), e);
            }
        };
    }
    /**
     * Selects all users from the database.
     *
     * @return function which returns information
     * about all users if any
     */
    public BiConsumer<HttpServletRequest, HttpServletResponse> selectAllAction() {
        return (request, response) -> {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/SelectAll");
            try {
                dispatcher.forward(request, response);
            } catch (ServletException | IOException e) {
                logger.error(e.getMessage(), e);
            }
        };
    }
}
