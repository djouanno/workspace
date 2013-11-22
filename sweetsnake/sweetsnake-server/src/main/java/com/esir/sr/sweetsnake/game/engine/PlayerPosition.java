package com.esir.sr.sweetsnake.game.engine;

/**
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
     * 
     * @param _witdh
     * @param _height
     * @param _playerNb
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
     * 
     * @return
     */
    public int getXPos() {
        switch (playerNb) {
            default:
                return 0;
            case 2:
                return width - 1;
            case 3:
                return 0;
            case 4:
                return width - 1;
        }
    }

    /**
     * 
     * @return
     */
    public int getYPos() {
        switch (playerNb) {
            default:
                return 0;
            case 2:
                return height - 1;
            case 3:
                return height - 1;
            case 4:
                return 0;
        }
    }

}
