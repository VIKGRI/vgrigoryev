package grigoryev.controllers;

/**
 * Represents action type. Action type
 * corresponds to action performed in the
 * database.
 */
public enum Action {
    INSERT, DELETE, UPDATE, SELECT_BY_ID, SELECT_BY_LOGIN, SELECT_BY_ROLE,
    SELECT_BY_MUSIC_TYPE, SELECT_BY_ADDRESS, SELECT_ALL
}
