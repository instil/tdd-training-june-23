package game.of.life.jqwik;

import game.of.life.Cell;
import game.of.life.CellPair;
import game.of.life.CellUtils;
import net.jqwik.api.*;
import net.jqwik.api.constraints.IntRange;
import net.jqwik.api.constraints.Size;
import net.jqwik.api.constraints.UniqueElements;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@PropertyDefaults(tries = 10000, shrinking = ShrinkingMode.OFF)
public class CellTest {
    @Example
    public void throwsWhenItHasTooManyNeighbours() {
        CellPair pair = CellUtils.buildCellWithNeighbours();
        assertThrows(
                IllegalStateException.class,
                () -> pair.getCell().addNeighbour(new Cell(new ArrayList<>()))
        );
    }

    @Example
    public void isDeadByDefault() {
        CellPair pair = CellUtils.buildCellWithNeighbours();
        assertTrue(pair.getCell().isDead());
    }

    @Example
    public void canBeMadeAlive() {
        CellPair pair = CellUtils.buildCellWithNeighbours();
        Cell cell = pair.getCell();

        cell.makeAlive();
        assertTrue(cell.isAlive());
    }

    @Example
    public void canBeMadeDead() {
        CellPair pair = CellUtils.buildCellWithNeighbours();
        Cell cell = pair.getCell();

        cell.makeAlive();
        assertTrue(cell.isAlive());
        cell.makeDead();
        assertTrue(cell.isDead());
    }

    @Example
    public void remainsDeadWithoutThreeLiveNeighbours() {
        CellPair pair = CellUtils.buildCellWithNeighbours();
        Cell cell = pair.getCell();
        List<Cell> neighbours = pair.getNeighbours();

        for (int livingNeighbours = 0; livingNeighbours <= 8; livingNeighbours++) {
            for (int i = 0; i < livingNeighbours; i++) {
                neighbours.get(i).makeAlive();
            }
            cell.changeState();
            if (livingNeighbours != 3) {
                assertTrue(cell.isDead());
            }
        }
    }

    @Property
    public void starvesWhenOnlyOneNeighbourIsAlive(@ForAll @IntRange(max = 7) Integer index) {
        CellPair pair = CellUtils.buildCellWithLiveNeighbours(List.of(index));
        Cell cell = pair.getCell();

        cell.changeState();
        assertTrue(cell.isDead());
    }

    @Property
    public void becomesAliveWhenThreeNeighboursAreAlive(
            @ForAll @Size(3) @UniqueElements List<@IntRange(max = 7) Integer> indexes
    ) {
        CellPair pair = CellUtils.buildCellWithLiveNeighbours(indexes);
        Cell cell = pair.getCell();

        cell.changeState();
        assertTrue(cell.isAlive());
    }

    @Property
    public void isOvercrowdedWhenMoreThanThreeNeighboursAreAlive(
            @ForAll @Size(min = 4, max = 8) @UniqueElements List<@IntRange(max = 7) Integer> indexes
    ) {
        CellPair pair = CellUtils.buildCellWithLiveNeighbours(indexes);
        Cell cell = pair.getCell();

        cell.changeState();
        assertTrue(cell.isDead());
    }
}
