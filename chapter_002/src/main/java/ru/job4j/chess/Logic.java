package ru.job4j.chess;

import ru.job4j.chess.firuges.Cell;
import ru.job4j.chess.firuges.Figure;

import java.util.Optional;

/**
 * //TODO add comments.
 *
 * @author Petr Arsentev (parsentev@yandex.ru)
 * @version $Id$
 * @since 0.1
 */
public class Logic {
    private final Figure[] figures = new Figure[32];
    private int index = 0;

    public void add(Figure figure) {
        this.figures[this.index++] = figure;
    }

    public boolean move(Cell source, Cell dest) {
        boolean rst = false;
        int index = this.findBy(source);
        if (index != -1 && !source.equals(dest)) {
            Cell[] steps = this.figures[index].way(source, dest);
            if (correctStep(steps, dest)) {
                rst = true;
                this.figures[index] = this.figures[index].copy(dest);
            }
        }
        return rst;
    }

    public void clean() {
        for (int position = 0; position != this.figures.length; position++) {
            this.figures[position] = null;
        }
        this.index = 0;
    }

    private boolean correctStep(Cell[] steps, Cell dest) {
        boolean result = true;
        if (steps.length > 0 && steps[steps.length - 1].equals(dest)) {
            for (Cell step : steps) {
                for (Figure figure: this.figures) {
                    if (figure != null && step.equals(figure.position())) {
                        result = false;
                        break;
                    }
                }
                if (!result) {
                    break;
                }
            }
        } else {
            result = false;
        }
        return result;
    }

    public Figure[] getFigures() {
        return figures;
    }

    private int findBy(Cell cell) {
        int rst = -1;
        for (int index = 0; index != this.figures.length; index++) {
            if (this.figures[index] != null && this.figures[index].position().equals(cell)) {
                rst = index;
                break;
            }
        }
        return rst;
    }
}
