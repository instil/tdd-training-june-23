package game.of.life;

import java.util.List;

public class CellPair {
    private final Cell cell;
    private final List<Cell> neighbours;

    public CellPair(Cell cell, List<Cell> neighbours) {
        this.cell = cell;
        this.neighbours = neighbours;
    }

    public Cell getCell() {
        return cell;
    }

    public List<Cell> getNeighbours() {
        return neighbours;
    }
}
