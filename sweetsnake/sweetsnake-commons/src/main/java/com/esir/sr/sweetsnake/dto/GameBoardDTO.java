package com.esir.sr.sweetsnake.dto;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

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
    private static final long               serialVersionUID = 7019697058656634742L;

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    /** The map width */
    private final int                       width;

    /** The map height */
    private final int                       height;

    /** The number of sweets */
    private final int                       nbSweets;

    /** The components to refresh */
    private final List<GameBoardRefreshDTO> refreshes;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
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

    /**
     * 
     * @return
     */
    public List<GameBoardRefreshDTO> getComponentsToRefresh() {
        return Collections.unmodifiableList(refreshes);
    }

}
