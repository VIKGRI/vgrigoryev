package ru.grigoryev.game;

import org.junit.Test;

/**
 * Testing for game test class.
 *
 * @author vgrigoryev
 * @since 14.10.2017
 */
public class GameTest {
    /**
     * Testing hero's movings.
     */
    @Test
    public void whenStartGameThenHeroMoves() {
        Game game = new Game(10);
        game.start(60);
    }
}