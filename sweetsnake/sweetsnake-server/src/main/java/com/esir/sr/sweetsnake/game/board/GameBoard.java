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
 * This class represents a gameboard and handles both the component position and the refreshes to make after the gameboard has
 * changed.
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
     * Creates a new gameboard
     * 
     * @param _width
     *            The gameboard width
     * @param _height
     *            The gameboard height
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
     * This method adds a component to the gameboard
     * 
     * @param component
     *            The component to add
     */
    public void addComponent(final IComponent component) {
        log.debug("Adding component {} to the map", component);
        if (component.getType() == ComponentType.SWEET) {
            nbSweets++;
        }
        gameMap[component.getXPos()][component.getYPos()] = component;
        refreshes.add(new GameBoardRefresh(component, RefreshAction.ADD));
    }

    /**
     * This method moves a component on the gameboard
     * 
     * @param component
     *            The component to move
     * @param direction
     *            The direction where to move
     */
    public void moveComponent(final IComponent component, final MoveDirection direction) {
        final IComponent oldComponent = getComponentById(component.getId());
        if (oldComponent != null) {
            gameMap[oldComponent.getXPos()][oldComponent.getYPos()] = null;
            component.move(direction);
            gameMap[component.getXPos()][component.getYPos()] = component;
            refreshes.add(new GameBoardRefresh(component, RefreshAction.MOVE));
        }
    }

    /**
     * This method removes a component from the gameboard
     * 
     * @param component
     *            The component to remove
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
     * This method returns a component according to its coordinates
     * 
     * @param x
     *            The X position of the component
     * @param y
     *            The Y position of the component
     * @return The component at the specified coordinates if it was found, null otherwise
     */
    public IComponent getComponent(final int x, final int y) {
        return gameMap[x][y];
    }

    /**
     * This method returns a component according to its id
     * 
     * @param id
     *            The component id
     * @return The component with the specified id if it was found, null otherwise
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
     * This method checks if a component is set on the gameboard at the specified coordinates
     * 
     * @param x
     *            The X coordinate
     * @param y
     *            The Y coordinate
     * @return True if a component exists at the specified coordinates, false otherwise
     */
    public boolean hasComponent(final int x, final int y) {
        return getComponent(x, y) != null;
    }

    /**
     * This method clears all the refreshes recorded since the last clear, according to the actions done on the gameboard
     */
    public void clearRefreshes() {
        refreshes.clear();
    }

    /**********************************************************************************************
     * [BLOCK] GETTERS
     **********************************************************************************************/

    /**
     * This method returns the width of the gameboard
     * 
     * @return An integer representing the width of the gameboard
     */
    public int getWidth() {
        return width;
    }

    /**
     * This method returns the height of the gameboard
     * 
     * @return An integer representing the height of the gameboard
     */
    public int getHeight() {
        return height;
    }

    /**
     * This method returns the number of sweets remaining on the gameboard
     * 
     * @return An integer representing the number of sweets remaining on the gameboard
     */
    public int getNbSweets() {
        return nbSweets;
    }

    /**
     * This method returns all the refresh recorded since the last clear
     * 
     * @return A list containing all the refresh recorded since the last clear
     */
    public List<GameBoardRefresh> getRefreshes() {
        return refreshes;
    }

}
