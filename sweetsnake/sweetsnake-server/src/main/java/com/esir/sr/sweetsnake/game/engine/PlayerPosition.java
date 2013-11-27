package com.esir.sr.sweetsnake.game.engine;

/**
 * This class provides the methods which calculate where to place a player's snake according to the player's number in the game
 * session.
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
public class PlayerPosition
{

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    /** The matrix width */
    private final int width;

    /** The matrix height */
    private final int height;

    /** The player number */
    private final int playerNb;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * Creates a new player position
     * 
     * @param _witdh
     *            The gameboard width
     * @param _height
     *            The gameboard height
     * @param _playerNb
     *            The player's number in the game session
     */
    public PlayerPosition(final int _witdh, final int _height, final int _playerNb) {
        width = _witdh;
        height = _height;
        playerNb = _playerNb;
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    /**
     * This method returns the X position of the player
     * 
     * @return An integer representing the X position of the player
     */
    public int getXPos() {
        switch (playerNb) {
            case 2:
                return width - 1;
            case 3:
                return 0;
            case 4:
                return width - 1;
            default:
                return 0;
        }
    }

    /**
     * This method returns the Y position of the player
     * 
     * @return An integer representing the Y position of the player
     */
    public int getYPos() {
        switch (playerNb) {
            case 2:
                return height - 1;
            case 3:
                return height - 1;
            case 4:
                return 0;
            default:
                return 0;
        }
    }

}
