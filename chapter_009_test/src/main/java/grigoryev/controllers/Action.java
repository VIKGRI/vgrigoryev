package grigoryev.controllers;

/**
 * Represents action type. Action type
 * corresponds to action performed in the
 * database.
 */
public enum Action {
    Insert, Delete, Update, Select_by_id, Select_by_login, Select_by_role,
    Select_by_music_type, Select_by_address, Select_all
}
