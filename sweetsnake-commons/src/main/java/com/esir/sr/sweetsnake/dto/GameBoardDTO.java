package com.esir.sr.sweetsnake.dto;

import java.io.Serializable;

/**
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
public class GameBoardDTO implements Serializable
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    /** The serial version UID */
    private static final long    serialVersionUID = 7019697058656634742L;

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    /** The game map */
    private final ComponentDTO[][] gameMap;

    /** The map width */
    private final int            width;

    /** The map height */
    private final int            height;

    /** The number of sweets */
    private final int            nbSweets;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * 
     * @param _width
     * @param _height
     * @param _gameMap
     * @param _nbSweets
     */
    public GameBoardDTO(final int _width, final int _height, final ComponentDTO[][] _gameMap, final int _nbSweets) {
        width = _width;
        height = _height;
        gameMap = _gameMap;
        nbSweets = _nbSweets;
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    /**
     * 
     * @param x
     * @param y
     * @return
     */
    public ComponentDTO getElement(final int x, final int y) {
        return gameMap[x][y];
    }

    /**
     * 
     * @param id
     * @return
     */
    public ComponentDTO getElementById(final String id) {
        for (int i = 0; i < gameMap.length; i++) {
            for (int j = 0; j < gameMap[i].length; j++) {
                if (getElement(i, j).getId() == id) {
                    return getElement(i, j);
                }
            }
        }

        return null;
    }

    /**
     * 
     * @param x
     * @param y
     * @return
     */
    public boolean hasElement(final int x, final int y) {
        return getElement(x, y) != null;
    }

    /**********************************************************************************************
     * [BLOCK] GETTERS
     **********************************************************************************************/

    /**
     * 
     * @return
     */
    public int getHeight() {
        return height;
    }

    /**
     * 
     * @return
     */
    public int getWidth() {
        return width;
    }

    /**
     * 
     * @return
     */
    public int getNbSweets() {
        return nbSweets;
    }

}
