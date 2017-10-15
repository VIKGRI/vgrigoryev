package ru.grigoryev.game;

import java.util.ArrayList;

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
     * Game's level. Sets number of monsters.
     */
    private final Level level;
    /**
     * Game board.
     */
    private final GameBoard board;
    /**
     * Hero.
     */
    private final Bomberman hero;
    /**
     * Monsters.
     */
    private ArrayList<Monster> monsters;
    /**
     * Constructor.
     * @param boardSize board size
     * @param level game's level
     */
    public Game(int boardSize, Level level) {
        this.level = level;
        this.board = new GameBoard(boardSize);
        this.hero = new Bomberman(this.board, 0, 0);
        this.monsters = new ArrayList<>();
    }

    /**
     * Starts the game.
     * @param iterations number of iterations hero should move.
     */
    public void start(int iterations) {
        Thread heroThread = new Thread(this.hero);
        this.initGame(iterations, heroThread);
        heroThread.start();
        for (Monster monster : this.monsters) {
            new Thread(monster).start();
        }
        try {
            heroThread.join();
        } catch (InterruptedException ex) {
            System.out.println("Inside start");
        }
    }

    /**
     * Initialization of the game.
     * @param iterations number of iterations hero should move.
     * @param heroThread Hero's executing thread
     */
    private void initGame(int iterations, Thread heroThread) {
        this.createBlockedZones();
        this.hero.setMoveIterations(iterations);
        this.createMonsters(heroThread);
    }

    /**
     * Creates blocked cells.
     */
    private void createBlockedZones() {
        int blockAmount = this.board.getBoardSize() * this.board.getBoardSize() / 10;
        int row;
        int column;
        for (int i = 0; i < blockAmount; i++) {
            row = (int) (Math.random() *  this.board.getBoardSize());
            column = (int) (Math.random() *  this.board.getBoardSize());
            while (!this.board.tryLockCell(row, column, 500)) {
                row = (int) (Math.random() *  this.board.getBoardSize());
                column = (int) (Math.random() *  this.board.getBoardSize());
            }
        }
    }
    /**
     * Creates monsters.
     * @param heroThread Hero's executing thread
     */
    private void createMonsters(Thread heroThread) {
        int monsterCoefficient = (this.level == Level.BEGINNER ? 20 : (this.level == Level.MIDDLE ? 15 : 10));
        int monsterAmount = this.board.getBoardSize() * this.board.getBoardSize() / monsterCoefficient;
        int row;
        int column;
        for (int i = 0; i < monsterAmount; i++) {
            row = (int) (Math.random() *  this.board.getBoardSize());
            column = (int) (Math.random() *  this.board.getBoardSize());
            while (this.board.isCellLocked(row, column)) {
                row = (int) (Math.random() *  this.board.getBoardSize());
                column = (int) (Math.random() *  this.board.getBoardSize());
            }
            this.monsters.add(new Monster(this.board, heroThread, row, column));
        }
    }
    /**
     * API for users to move hero up.
     * @return true if operation succeeds and false otherwise
     */
    public boolean moveUp() {
        return this.hero.moveUp();
    }
    /**
     * API for users to move hero down.
     * @return true if operation succeeds and false otherwise
     */
    public boolean moveDown() {
        return this.hero.moveDown();
    }
    /**
     * API for users to move hero left.
     * @return true if operation succeeds and false otherwise
     */
    public boolean moveLeft() {
        return this.hero.moveUp();
    }
    /**
     * API for users to move hero right.
     * @return true if operation succeeds and false otherwise
     */
    public boolean moveRight() {
        return this.hero.moveRight();
    }
}

/**
 * Levels of the game.
 */
enum Level {
    /**
     * Beginner level.
     */
    BEGINNER,
    /**
     * Middlelevel.
     */
    MIDDLE,
    /**
     * Master level.
     */
    MASTER
}