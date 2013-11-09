package com.esir.sr.sweetsnake.game.map;

import java.util.Random;

import com.esir.sr.sweetsnake.game.element.Sweet;

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
            int j, k;
            do {
                j = random.nextInt(height);
                k = random.nextInt(width);
            } while (board.hasElement(j, k));

            final Sweet sweet = new Sweet();
            sweet.setXY(j, k);
            board.setElement(sweet);
        }

        return board;
    }

}
