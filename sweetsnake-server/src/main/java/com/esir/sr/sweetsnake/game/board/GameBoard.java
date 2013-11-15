package com.esir.sr.sweetsnake.game.board;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.esir.sr.sweetsnake.api.IComponent;
import com.esir.sr.sweetsnake.enumeration.ComponentType;
import com.esir.sr.sweetsnake.enumeration.MoveDirection;
import com.esir.sr.sweetsnake.enumeration.RefreshAction;

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
    private static final Logger          log = LoggerFactory.getLogger(GameBoard.class);

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    /** The game map */
    private final IComponent[][]         gameMap;

    /** The map width */
    private final int                    width;

    /** The map height */
    private final int                    height;

    /** The number of sweets */
    private int                          nbSweets;

    /** The refreshes to do */
    private final List<GameBoardRefresh> refreshes;

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
        refreshes = new LinkedList<GameBoardRefresh>();
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    /**
     * 
     * @param component
     */
    public void addComponent(final IComponent component) {
        log.debug("Adding component {} on the map", component);
        if (component.getType() == ComponentType.SWEET) {
            nbSweets++;
        }
        gameMap[component.getXPos()][component.getYPos()] = component;
        refreshes.add(new GameBoardRefresh(component, RefreshAction.ADD));
    }

    /**
     * 
     * @param component
     * @param direction
     */
    public void moveComponent(final IComponent component, final MoveDirection direction) {
        final IComponent oldComponent = getComponentById(component.getId());
        log.debug("old component : {}", oldComponent);
        if (oldComponent != null) {
            log.debug("Setting old component matrix to null for x = {} and y = {}", oldComponent.getXPos(), oldComponent.getYPos());
            gameMap[oldComponent.getXPos()][oldComponent.getYPos()] = null;
            component.move(direction);
            log.debug("Moving component {} on the map", component);
            gameMap[component.getXPos()][component.getYPos()] = component;
            refreshes.add(new GameBoardRefresh(component, RefreshAction.MOVE));
        }
    }

    /**
     * 
     * @param component
     */
    public void removeComponent(final IComponent component) {
        log.debug("Removing component {} from the map", component);
        if (component.getType() == ComponentType.SWEET) {
            nbSweets--;
        }
        gameMap[component.getXPos()][component.getYPos()] = null;
        refreshes.add(new GameBoardRefresh(component, RefreshAction.REMOVE));
    }

    /**
     * 
     * @param x
     * @param y
     * @return
     */
    public IComponent getComponent(final int x, final int y) {
        return gameMap[x][y];
    }

    /**
     * 
     * @param id
     * @return
     */
    public IComponent getComponentById(final String id) {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                final IComponent component = gameMap[x][y];
                if (component != null && component.getId().equals(id)) {
                    return component;
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
    public boolean hasComponent(final int x, final int y) {
        return getComponent(x, y) != null;
    }

    /**
     * 
     */
    public void clearRefreshes() {
        log.debug("Clearing the game board refreshes");
        refreshes.clear();
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
    public List<GameBoardRefresh> getRefreshes() {
        return refreshes;
    }

}
