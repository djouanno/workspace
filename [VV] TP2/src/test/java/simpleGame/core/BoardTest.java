package simpleGame.core;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class BoardTest
{

    @Mock
    private Pawn            currentPawn;

    private ArrayList<Pawn> pawns;

    private Board           board;

    @Before
    public void init() {
        board = new Board(2, 8, 8);
        pawns = new ArrayList<>();
        pawns.add(mock(Pawn.class));
        pawns.add(mock(Pawn.class));
        pawns.add(mock(Pawn.class));

        board.setPawns(pawns);
    }

    @Test
    public void getXSizeTest() {

    }

    @Test
    public void getYSizeTest() {

    }

    @Test
    public void boardTest() {

    }

    @Test
    public void getSquareContentTest() {

    }

    @Test
    public void removePawnTest() {

    }

    @Test
    public void addPawnTest() {

    }

    @Test
    public void isBonusSquareTest() {

    }

    @Test
    public void numberOfPawnsTest() {
        assertEquals(board.numberOfPawns(), pawns.size());
    }

    @Test
    public void maxGoldTest() {
        when(pawns.get(0).getGold()).thenReturn(300);
        when(pawns.get(1).getGold()).thenReturn(500);
        when(pawns.get(2).getGold()).thenReturn(100);

        assertEquals(board.maxGold(), 500);
    }

    @Test
    public void getNextPawnTest() {

    }

    @Test
    public void squareContentSprite() {

    }
}
