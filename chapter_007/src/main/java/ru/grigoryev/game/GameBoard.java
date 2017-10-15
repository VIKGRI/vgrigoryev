package ru.grigoryev.game;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Game board where hero moves in.
 *
 * @author vgrigoryev
 * @version 1
 * @since 14.10.2017
 */
public class GameBoard {
    /**
     * Stores board size.
     */
    private final int boardSize;
    /**
     * The game board.
     */
    private final ReentrantLock[][] board;

    /**
     * Constructor.
     * @param size size of board
     */
    public GameBoard(int size) {
        this.boardSize = size;
        this.board = new ReentrantLock[this.boardSize][];
        for (int i = 0; i < this.boardSize; i++) {
            this.board[i] = new ReentrantLock[this.boardSize];
            for (int j = 0; j < this.boardSize; j++) {
                this.board[i][j] = new ReentrantLock();
            }
        }
    }

    /**
     * Locks the cell specified by row and column.
     * @param row specified cell row
     * @param column specified cell column
     */
    public void lockCell(int row, int column) {
        this.checkBounbs(row, column);
        this.board[row][column].lock();
    }

    /**
     * Unlocks the cell specified by row and column
     * if it's locked.
     * @param row specified cell row
     * @param column specified cell column
     */
    public void unlockCell(int row, int column) {
        this.checkBounbs(row, column);
        if (this.board[row][column].isLocked()) {
            this.board[row][column].unlock();
        }
    }

    /**
     * Tries to acquire lock on the cell
     * specified by row and column within specified time.
     * @param row specified cell row
     * @param column specified cell column
     * @param time specified time
     * @return true if lock is aquired and false otherwise
     */
    public boolean tryLockCell(int row, int column, long time) {
        this.checkBounbs(row, column);
        boolean isSucceeded = false;
        long start = System.currentTimeMillis();
        while (!isSucceeded
                && System.currentTimeMillis() - start < time) {
            isSucceeded = this.board[row][column].tryLock();
        }
        return  isSucceeded;
    }

    /**
     * Checks whether the operation violates the bounds of the board.
     * @param row specified cell row
     * @param column specified cell column
     * @throws OutOfBoardException thrown if the bounds are violated.
     */
    private void checkBounbs(int row, int column)
            throws OutOfBoardException {
        if (row < 0 || row >= this.boardSize) {
            throw new OutOfBoardException("Out Of Board");
        }
        if (column < 0 || column >= this.boardSize) {
            throw new OutOfBoardException("Out Of Board");
        }
    }

    /**
     * Gets board size.
     * @return board size.
     */
    public int getBoardSize() {
        return this.boardSize;
    }
}
