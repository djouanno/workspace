package com.esir.sr.sweetsnake.game.map;

import java.util.Random;

import com.esir.sr.sweetsnake.game.component.Sweet;

/**
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
public class GameBoardGenerator
{

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * 
     */
    private GameBoardGenerator() {
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC STATIC METHODS
     **********************************************************************************************/

    /**
     * 
     * @param width
     * @param height
     * @param nbSweets
     * @return
     */
    public static GameBoard generateBoard(final int width, final int height, final int nbSweets) {
        final GameBoard board = new GameBoard(width, height);

        final Random random = new Random();
        for (int i = 0; i < nbSweets; i++) {
            int x, y;
            do {
                x = random.nextInt(height);
                y = random.nextInt(width);
            } while (board.hasComponent(x, y) || x == 0 && y == 0 || x == width - 1 && y == 0 || x == width - 1 && y == height - 1 || x == 0 && y == height - 1);

            final Sweet sweet = new Sweet();
            sweet.setXYPos(x, y);
            board.setComponent(sweet);
        }

        return board;
    }

}
