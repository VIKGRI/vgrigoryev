package ru.grigoryev.game;

/**
 * Provides game with game board and hero
 * moving in separate thread.
 *
 * @author vgrigoryev
 * @version 1
 * @since 14.10.2017
 */
public class Game {
    /**
     * Game board.
     */
    private final GameBoard board;
    /**
     * Hero.
     */
    private final Bomberman hero;

    /**
     * Constructor.
     * @param boardSize board size
     */
    public Game(int boardSize) {
        this.board = new GameBoard(boardSize);
        this.hero = new Bomberman(this.board, 0, 0);
    }

    /**
     * Starts the game.
     * @param iterations number of iterations hero should move.
     */
    public void start(int iterations) {
        this.hero.setMoveIterations(iterations);
        Thread heroThread = new Thread(this.hero);
        heroThread.start();
        try {
            heroThread.join();
        } catch (InterruptedException ex) {
            System.out.println("Inside start");
        }
    }
}
