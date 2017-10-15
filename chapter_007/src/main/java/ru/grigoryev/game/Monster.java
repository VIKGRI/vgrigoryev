package ru.grigoryev.game;

/**
 * Monster - bomberman's enemy in the game.
 *
 * @author vgrigoryev
 * @version 1
 * @since 15.10.2017
 */
public class Monster extends Hero implements RandomMove, Runnable {
    /**
     * Bomberman's executing thread in the current game.
     */
    private final Thread bomberman;
    /**
     * Time to keep trying to locate the new cell.
     */
    private static final int KEEP_TRYING_TIME = 5000;
    /**
     * Time to delay before moving further.
     */
    private static final int DELAY = 1000;
    /**
     * Number of created monster.
     */
    private static int monsterNum = 0;

    /**
     * Hero of the game.
     *
     * @param board board hero where to move.
     * @param bomberman bomberman in the current game
     * @param row specified row
     * @param column specified column
     */
    public Monster(GameBoard board, Thread bomberman, int row, int column) {
        super(board, "Monster #" + Monster.getNumber(), row, column, KEEP_TRYING_TIME);
        this.bomberman = bomberman;
    }

    /**
     * Gets the number of monster created.
     * @return number of monster created
     */
    private static synchronized int getNumber() {
        return monsterNum++;
    }

    @Override
    public void run() {
        while (this.bomberman.isAlive()) {
            this.move();
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
