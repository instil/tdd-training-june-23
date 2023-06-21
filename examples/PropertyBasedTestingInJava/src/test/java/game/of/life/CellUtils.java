package game.of.life;

import game.of.life.quick.theories.CellTest;

import java.util.ArrayList;
import java.util.List;

public class CellUtils {
    public static CellPair buildCellWithNeighbours() {
        List<Cell> neighbours = new ArrayList<Cell>();
        for (int x = 0; x < CellTest.MAX_NEIGHBOURS; x++) {
            neighbours.add(new Cell(new ArrayList<>()));
        }
        Cell cell = new Cell(neighbours);
        return new CellPair(cell, neighbours);
    }

    public static CellPair buildCellWithLiveNeighbours(List<Integer> numbers) {
        CellPair pair = buildCellWithNeighbours();

        List<Cell> neighbours = pair.getNeighbours();
        numbers.forEach(num -> neighbours.get(num).makeAlive());

        return pair;
    }
}
