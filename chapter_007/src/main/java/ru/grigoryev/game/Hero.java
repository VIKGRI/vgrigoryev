package ru.grigoryev.game;

import ru.grigoryev.threads.Pair;

/**
 * Abstract class represents hero in the game
 * from which concrete characters inherit.
 *
 * @author vgrigoryev
 * @version 1
 * @since 14.10.2017
 */
public abstract class Hero {
    /**
     * Game's hero type and name.
     */
    private String heroName;
    /**
     * Board hero to move on.
     */
    private final GameBoard board;
    /**
     * Current location.
     */
    private final Pair<Integer, Integer> location;
    /**
     * Time to keep trying to locate the new cell.
     */
    private final int delayTime;
    /**
     * Hero of the game.
     * @param board board hero where to move
     * @param heroName hero type and name
     * @param row specified row
     * @param column specified column
     * @param delayTime time to keep trying to locate the new cell
     */
    public Hero(GameBoard board, String heroName, int row, int column, int delayTime) {
        this.board = board;
        this.heroName = heroName;
        this.location = new Pair<>(row, column);
        this.delayTime = delayTime;
    }

    /**
     * Moves hero one cell up if it's possible.
     * @return true if the movement succeeds.
     */
    public boolean moveUp() {
        int row = this.location.getFirst();
        int column = this.location.getSecond();
        if (row - 1 >= 0) {
            if (this.board.tryLockCell(row - 1, column, this.delayTime)) {
                this.board.unlockCell(row, column);
                this.location.setFirst(row - 1);
                this.gameObserver("up", row, column);
                return true;
            }
        }
        System.out.println("Immpossible to move");
        return false;
    }

    /**
     * Moves hero one cell down if it's possible.
     * @return true if the movement succeeds.
     */
    public boolean moveDown() {
        int row = this.location.getFirst();
        int column = this.location.getSecond();
        if (row + 1 < this.board.getBoardSize()) {
            if (this.board.tryLockCell(row + 1, column, this.delayTime)) {
                this.board.unlockCell(row, column);
                this.location.setFirst(row + 1);
                this.gameObserver("down", row, column);
                return true;
            }
        }
        System.out.println("Immpossible to move");
        return false;
    }

    /**
     * Moves hero one cell left if it's possible.
     * @return true if the movement succeeds.
     */
    public boolean moveLeft() {
        int row = this.location.getFirst();
        int column = this.location.getSecond();
        if (column - 1 >= 0) {
            if (this.board.tryLockCell(row, column - 1, this.delayTime)) {
                this.board.unlockCell(row, column);
                this.location.setSecond(column - 1);
                this.gameObserver("left", row, column);
                return true;
            }
        }
        System.out.println("Immpossible to move");
        return false;
    }

    /**
     * Moves hero one cell right if it's possible.
     * @return true if the movement succeeds.
     */
    public boolean moveRight() {
        int row = this.location.getFirst();
        int column = this.location.getSecond();
        if (column + 1 < this.board.getBoardSize()) {
            if (this.board.tryLockCell(row, column + 1, this.delayTime)) {
                this.board.unlockCell(row, column);
                this.location.setSecond(column + 1);
                this.gameObserver("right", row, column);
                return true;
            }
        }
        System.out.println("Immpossible to move");
        return false;
    }

    /**
     * Shows the process of game in the console.
     * @param operation operation performed
     * @param oldRow row before operation
     * @param oldColumn column before operation
     */
    private void gameObserver(String operation, int oldRow, int oldColumn) {
        System.out.println(this.heroName + " moved " + operation + " from " + "row: " + oldRow + " column: " + oldColumn
                + " to " + "row: " + this.location.getFirst() + " column: " + this.location.getSecond());
    }
}
