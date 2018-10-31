package bomberman.movement;

import bomberman.Board;
import bomberman.Cell;

public class MovementUnits extends Thread {
    final Board board;
    enum DIRECTIONS { LEFT, RIGHT, TOP, BUTTON };
    private DIRECTIONS direction = DIRECTIONS.RIGHT;
    boolean firstLock = false;

    public MovementUnits(Board board) {
        this.board = board;
    }

    public Cell moveTo() {
        int x = board.getHero().getCell().getX();
        int y = board.getHero().getCell().getY();
        checkBounds(x, y);
        if (this.direction == DIRECTIONS.LEFT) {
            x--;
        } else if (this.direction == DIRECTIONS.RIGHT) {
            x++;
        } else if (this.direction == DIRECTIONS.TOP) {
            y++;
        } else if (this.direction == DIRECTIONS.BUTTON) {
            y--;
        }
        return new Cell(x, y);
    }

    private void checkBounds(int x, int y) {
        if (x >= board.getWidth() - 1 && this.direction == DIRECTIONS.RIGHT
                || x <= 0 &&  this.direction == DIRECTIONS.LEFT
                || y <= 0 && this.direction == DIRECTIONS.BUTTON
                || y >= board.getHeight() - 1 && this.direction == DIRECTIONS.TOP) {
            changeDirection();
        }
    }

    public void changeDirection() {
        if (this.direction == DIRECTIONS.RIGHT) {
            this.direction = DIRECTIONS.LEFT;
        } else if (this.direction == DIRECTIONS.LEFT) {
            this.direction = DIRECTIONS.RIGHT;
        } else if (this.direction == DIRECTIONS.BUTTON) {
            this.direction = DIRECTIONS.TOP;
        } else if (this.direction == DIRECTIONS.TOP) {
            this.direction = DIRECTIONS.BUTTON;
        }
    }

    public void directionLeft() {
        this.direction = DIRECTIONS.LEFT;
    }

    public void directionRight() {
        this.direction = DIRECTIONS.RIGHT;
    }

    public void directionTop() {
        this.direction = DIRECTIONS.TOP;
    }

    public void directionButton() {
        this.direction = DIRECTIONS.BUTTON;
    }
}
