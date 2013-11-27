package com.esir.sr.sweetsnake.game.board;

import java.util.Random;

import com.esir.sr.sweetsnake.game.component.Sweet;

/**
 * This class provides static methods to generate a gameboard with randomly placed sweets.
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
     * Private constructor to prevent instantiation of the gameboard generator
     */
    private GameBoardGenerator() {
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC STATIC METHODS
     **********************************************************************************************/

    /**
     * This method generates a new gameboard with the specified parameters
     * 
     * @param width
     *            The gameboard width
     * @param height
     *            The gameboard height
     * @param nbSweets
     *            The number of sweets to randomly place
     * @return The generated gameboard
     */
    public static GameBoard generateBoard(final int width, final int height, final int nbSweets) {
        final GameBoard board = new GameBoard(width, height);

        final Random random = new Random();
        for (int i = 0; i < nbSweets; i++) {
            int x, y;
            do {
                x = random.nextInt(height);
                y = random.nextInt(width);
            } while (board.hasComponent(x, y) || isForbiddenPosition(width, height, x, y));

            final Sweet sweet = new Sweet();
            sweet.setXYPos(x, y);
            board.addComponent(sweet);
        }

        return board;
    }

    /**********************************************************************************************
     * [BLOCK] PRIVATE STATIC METHODS
     **********************************************************************************************/

    /**
     * This method checks whether the x and y position on the gameboard is a forbidden position to randomly place a sweet
     * 
     * @param width
     *            The gameboard width
     * @param height
     *            The gameboard height
     * @param x
     *            The X position
     * @param y
     *            The Y position
     * @return True if the position is allowed, false otherwise
     */
    private static boolean isForbiddenPosition(final int width, final int height, final int x, final int y) {
        return x == 0 && y == 0 || x == width - 1 && y == 0 || x == width - 1 && y == height - 1 || x == 0 && y == height - 1;
    }

}
