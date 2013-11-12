package com.esir.sr.sweetsnake.game.map;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.esir.sr.sweetsnake.api.IComponent;
import com.esir.sr.sweetsnake.enumeration.ComponentType;
import com.esir.sr.sweetsnake.enumeration.RefreshAction;
import com.esir.sr.sweetsnake.utils.Pair;

/**
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
public class GameBoard
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    /** The logger */
    private static final Logger                         log = LoggerFactory.getLogger(GameBoard.class);

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    /** The game map */
    private final IComponent[][]                        gameMap;

    /** The map width */
    private final int                                   width;

    /** The map height */
    private final int                                   height;

    /** The number of sweets */
    private int                                         nbSweets;

    /** The components to refresh */
    private final List<Pair<IComponent, RefreshAction>> componentsToRefresh;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * 
     * @param _width
     * @param _height
     */
    public GameBoard(final int _width, final int _height) {
        log.info("Initializing a new gameboard with dimensions {}x{}", _width, _height);
        width = _width;
        height = _height;
        gameMap = new IComponent[width][height];
        componentsToRefresh = new LinkedList<Pair<IComponent, RefreshAction>>();
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    /**
     * 
     * @param element
     */
    public void setElement(final IComponent element) {
        log.debug("Setting element {} on the map", element);
        if (element.getType() == ComponentType.SWEET) {
            nbSweets++;
        }
        gameMap[element.getXPos()][element.getYPos()] = element;
        componentsToRefresh.add(new Pair<IComponent, RefreshAction>(element, RefreshAction.SET));
    }

    /**
     * 
     * @param element
     */
    public void removeElement(final IComponent element) {
        log.debug("Removing element {} from the map", element);
        if (element.getType() == ComponentType.SWEET) {
            nbSweets--;
        }
        gameMap[element.getXPos()][element.getYPos()] = null;
        componentsToRefresh.add(new Pair<IComponent, RefreshAction>(element, RefreshAction.REMOVE));
    }

    /**
     * 
     * @param x
     * @param y
     * @return
     */
    public IComponent getElement(final int x, final int y) {
        return gameMap[x][y];
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

    /**
     * 
     */
    public void clearComponentsToRefresh() {
        componentsToRefresh.clear();
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
    public IComponent[][] getGameMap() {
        return gameMap;
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
    public List<Pair<IComponent, RefreshAction>> getComponentsToRefresh() {
        return componentsToRefresh;
    }

}
