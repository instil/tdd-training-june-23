package game;

import static game.GameState.*;

public class Cell {
    private GameState state;
    private final Cell[] neighbours;

    @Override
    public String toString() {
        return "A cell which is " + state;
    }

    public Cell(Cell[] neighbours) {
        this.neighbours = neighbours;
        this.state = DEAD;
    }

    public void makeDead() {
        this.state = DEAD;
    }

    public void makeAlive() {
        this.state = ALIVE;
    }

    private int countLivingNeighbours() {
        int count = 0;
        for (int i = 0; i < neighbours.length; i++) {
            if (neighbours[i].state() == ALIVE) {
                count += 1;
            }
        }
        return count;
    }

    public GameState state() {
        return this.state;
    }

    public void changeState() {
        int living = countLivingNeighbours();
        if (state() == DEAD) {
            if (living == 3) {
                makeAlive();
            }
        } else if (living > 3 || living < 2) {
            makeDead();
        }
    }
}
