package bomberman.movement;

import bomberman.Board;
import bomberman.Cell;

public class MovementUnits extends Thread {
    final Board board;
    enum directions {LEFT, RIGHT, TOP, BUTTON};
    private directions direction = directions.RIGHT;
    boolean firstLock = false;

    public MovementUnits(Board board) {
        this.board = board;
    }

    public Cell moveTo() {
        int x = board.getHero().getCell().getX();
        int y = board.getHero().getCell().getY();
        checkBounds(x, y);
        if (this.direction == directions.LEFT) {
            x--;
        } else if (this.direction == directions.RIGHT) {
            x++;
        } else if (this.direction == directions.TOP) {
            y++;
        } else if (this.direction == directions.BUTTON) {
            y--;
        }
        return new Cell(x, y);
    }

    private void checkBounds(int x, int y) {
        if (x >= board.getWidth() - 1 && this.direction == directions.RIGHT
                || x <= 0 &&  this.direction == directions.LEFT
                || y <= 0 && this.direction == directions.BUTTON
                || y >= board.getHeight() - 1 && this.direction == directions.TOP) {
            changeDirection();
        }
    }

    public void changeDirection() {
        if (this.direction == directions.RIGHT) {
            this.direction = directions.LEFT;
        } else if (this.direction == directions.LEFT) {
            this.direction = directions.RIGHT;
        } else if (this.direction == directions.BUTTON) {
            this.direction = directions.TOP;
        } else if (this.direction == directions.TOP) {
            this.direction = directions.BUTTON;
        }
    }

    public void directionLeft() {
        this.direction = directions.LEFT;
    }

    public void directionRight() {
        this.direction = directions.RIGHT;
    }

    public void directionTop() {
        this.direction = directions.TOP;
    }

    public void directionButton() {
        this.direction = directions.BUTTON;
    }
}
