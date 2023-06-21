package game.of.life.quick.theories;

import game.of.life.Cell;
import game.of.life.CellPair;
import game.of.life.CellUtils;
import org.junit.jupiter.api.Test;
import org.quicktheories.core.Profile;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.quicktheories.QuickTheory.qt;
import static org.quicktheories.generators.Generate.range;
import static org.quicktheories.generators.SourceDSL.lists;

public class CellTest {

    public static final int MAX_NEIGHBOURS = 8;

    static {
        Profile.registerDefaultProfile(
                CellTest.class,
                s -> s.withGenerateAttempts(10000)
                        .withExamples(100));
    }

    @Test
    public void throwsWhenItHasTooManyNeighbours() {
        CellPair pair = CellUtils.buildCellWithNeighbours();
        assertThrows(
                IllegalStateException.class,
                () -> pair.getCell().addNeighbour(new Cell(new ArrayList<>()))
        );
    }

    @Test
    public void isDeadByDefault() {
        CellPair pair = CellUtils.buildCellWithNeighbours();
        assertTrue(pair.getCell().isDead());
    }

    @Test
    public void canBeMadeAlive() {
        CellPair pair = CellUtils.buildCellWithNeighbours();
        Cell cell = pair.getCell();

        cell.makeAlive();
        assertTrue(cell.isAlive());
    }

    @Test
    public void canBeMadeDead() {
        CellPair pair = CellUtils.buildCellWithNeighbours();
        Cell cell = pair.getCell();

        cell.makeAlive();
        assertTrue(cell.isAlive());
        cell.makeDead();
        assertTrue(cell.isDead());
    }

    @Test
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

    @Test
    public void starvesWhenOnlyOneNeighbourIsAlive() {
        qt().withRegisteredProfiles(CellTest.class)
                .forAll(range(0, 7))
                .check(num -> {
                    CellPair pair = CellUtils.buildCellWithLiveNeighbours(List.of(num));
                    Cell cell = pair.getCell();

                    cell.changeState();
                    return cell.isDead();
                });
    }

    @Test
    public void becomesAliveWhenThreeNeighboursAreAlive() {
        qt().withRegisteredProfiles(CellTest.class)
                .forAll(lists().of(range(0, 7)).ofSize(3))
                .assuming(this::hasNoDuplicates)
                .check(list -> {
                    CellPair pair = CellUtils.buildCellWithLiveNeighbours(list);
                    Cell cell = pair.getCell();

                    cell.changeState();
                    return cell.isAlive();
                });
    }

    @Test
    public void isOvercrowdedWhenMoreThanThreeNeighboursAreAlive() {
        qt().withRegisteredProfiles(CellTest.class)
                .forAll(lists().of(range(0, 7)).ofSizeBetween(4,8))
                .assuming(this::hasNoDuplicates)
                .check(list -> {
                    CellPair pair = CellUtils.buildCellWithLiveNeighbours(list);
                    Cell cell = pair.getCell();

                    cell.changeState();
                    return cell.isDead();
                });
    }

    private <T> boolean hasNoDuplicates(List<T> items) {
        return items.size() == new HashSet<T>(items).size();
    }
}
