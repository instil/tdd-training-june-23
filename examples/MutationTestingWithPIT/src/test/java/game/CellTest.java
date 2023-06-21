package game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static game.GameState.ALIVE;
import static game.GameState.DEAD;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CellTest {
    Cell[] neighbours;
    Cell target;

    @BeforeEach
    public void setUp() {
        neighbours = new Cell[8];
        for (int x = 0; x < 8; x++) {
            neighbours[x] = new Cell(new Cell[8]);
        }
        target = new Cell(neighbours);
    }

    @Test
    public void isDeadByDefault() {
        assertEquals(DEAD, target.state());
    }

    @Test
    public void canBeMadeAlive() {
        target.makeAlive();
        assertEquals(ALIVE, target.state());
    }

    @Test
    public void canBeMadeDead() {
        target.makeAlive();
        target.makeDead();
        assertEquals(DEAD, target.state());
    }

    @Test
    public void becomesAliveThroughReproduction() {
        neighbours[0].makeAlive();
        neighbours[1].makeAlive();
        neighbours[2].makeAlive();
        target.changeState();
        assertEquals(ALIVE, target.state());
    }

    @Test
    public void survivesWithTwoLivingNeighbours() {
        target.makeAlive();
        neighbours[0].makeAlive();
        neighbours[1].makeAlive();

        target.changeState();

        assertEquals(ALIVE, target.state());
    }

    @Test
    public void diesWithOneLivingNeighbours() {
        target.makeAlive();
        neighbours[0].makeAlive();

        target.changeState();

        assertEquals(DEAD, target.state());
    }

    @Test
    public void diesWhenFirstFourNeighboursAreAlive() {
        target.makeAlive();
        neighbours[0].makeAlive();
        neighbours[1].makeAlive();
        neighbours[2].makeAlive();
        neighbours[3].makeAlive();

        target.changeState();

        assertEquals(DEAD, target.state());
    }

//    @Test
//    public void diesWhenLastFourNeighboursAreAlive() {
//        target.makeAlive();
//        neighbours[4].makeAlive();
//        neighbours[5].makeAlive();
//        neighbours[6].makeAlive();
//        neighbours[7].makeAlive();
//
//        target.changeState();
//
//        assertEquals(DEAD, target.state());
//    }


}
