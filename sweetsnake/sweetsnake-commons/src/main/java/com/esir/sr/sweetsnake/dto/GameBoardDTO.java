package com.esir.sr.sweetsnake.dto;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * This class is a Data Transfert Object representing a GameBoard.
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
    private static final long               serialVersionUID = 7019697058656634742L;

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    /** The gameboard width */
    private final int                       width;

    /** The gameboard height */
    private final int                       height;

    /** The number of sweets */
    private final int                       nbSweets;

    /** The components to refresh */
    private final List<GameBoardRefreshDTO> refreshes;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * Creates a new gameboard DTO
     * 
     * @param _width
     * @param _height
     * @param _nbSweets
     * @param _componentsToRefresh
     */
    public GameBoardDTO(final int _width, final int _height, final int _nbSweets, final List<GameBoardRefreshDTO> _componentsToRefresh) {
        width = _width;
        height = _height;
        nbSweets = _nbSweets;
        refreshes = _componentsToRefresh;
    }

    /**********************************************************************************************
     * [BLOCK] GETTERS
     **********************************************************************************************/

    /**
     * This method returns the gameboard width
     * 
     * @return An integer representing the gameboard width
     */
    public int getWidth() {
        return width;
    }

    /**
     * This method return the gameboard height
     * 
     * @return An integer representing the gameboard height
     */
    public int getHeight() {
        return height;
    }

    /**
     * This method returns the number of sweets present on the gameboard
     * 
     * @return An integer representing the number of sweets present on the gameboard
     */
    public int getNbSweets() {
        return nbSweets;
    }

    /**
     * This method returns all the gameboard refresh DTO to perform
     * 
     * @return A list containing the DTO representing all the gameboard refresh to perform
     */
    public List<GameBoardRefreshDTO> getComponentsToRefresh() {
        return Collections.unmodifiableList(refreshes);
    }

}
