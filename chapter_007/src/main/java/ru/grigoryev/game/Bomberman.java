package ru.grigoryev.game;

/**
 * Bomberman - hero in the game.
 *
 * @author vgrigoryev
 * @version 1
 * @since 14.10.2017
 */
public class Bomberman extends Hero implements RandomMove, Runnable {
    /**
     * Time to delay before moving further.
     */
    private static final int DELAY = 1000;
    /**
     * Number of movings.
     */
    private int moveIterations = 50;

    /**
     * Sets number of movings.
     * @param moveIterations number of movings
     */
    public void setMoveIterations(int moveIterations) {
        if (moveIterations >= 0) {
            this.moveIterations = moveIterations;
        }
    }

    /**
     * Hero of the game.
     *
     * @param board  board hero where to move.
     * @param row specified row
     * @param column specified column
     */
    public Bomberman(GameBoard board, int row, int column) {
        super(board, row, column);
    }

    @Override
    public void run() {
        while (this.moveIterations > 0) {
            this.move();
            this.moveIterations--;
            try {
                Thread.sleep(DELAY);
            } catch (InterruptedException ex) {
                System.out.println("Inside run");
            }
        }
    }

    @Override
    public void move() {
        int operation;
        boolean isSucceeded = false;
        while (!isSucceeded) {
            operation = (int) (Math.random() * 4);
            switch (operation) {
                case 0:
                    isSucceeded = this.moveUp();
                    break;
                case 1:
                    isSucceeded = this.moveDown();
                    break;
                case 2:
                    isSucceeded = this.moveLeft();
                    break;
                case 3:
                    isSucceeded = this.moveRight();
                    break;
                 default:
                     System.out.println("Unknown operation");
            }
        }
    }
}
