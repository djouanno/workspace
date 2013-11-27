package com.esir.sr.sweetsnake.constants;

/**
 * This class contains all the game constants.
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
public class GameConstants
{

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * Private constructor to prevent instantiation of the game constants
     */
    private GameConstants() {
    }

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    /** The grid size */
    public static final int GRID_SIZE             = 20;

    /** The cell size */
    public static final int CELL_SIZE             = 20;

    /** The starting number of sweets */
    public static final int NUMBER_OF_SWEETS      = 1; // 20;

    /** The maximum number of players per session */
    public static final int MAX_NUMBER_OF_PLAYERS = 4;

    /** The sweet score value */
    public static final int SWEET_SCORE_VALUE     = 10;

    /** The minimum time between 2 moves */
    public static final int TIME_BETWEEN_2_MOVES  = 50;

}
