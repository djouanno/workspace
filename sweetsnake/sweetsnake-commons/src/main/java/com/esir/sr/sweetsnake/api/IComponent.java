package com.esir.sr.sweetsnake.api;

import com.esir.sr.sweetsnake.enumeration.ComponentType;
import com.esir.sr.sweetsnake.enumeration.MoveDirection;

/**
 * This interface represents which methods a game component must be able to provide to a server or a client.<br />
 * All the methods below are intented to be called by the server or the client according to the events it processed.
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
public interface IComponent
{

    /**
     * This method returns the id of the component
     * 
     * @return A string representing the id of the component
     */
    String getId();

    /**
     * This methods is called to move the component on the game board
     * 
     * @param direction
     *            The direction where to move the component
     */
    void move(MoveDirection direction);

    /**
     * This method returns the X position of the component
     * 
     * @return An integer representing the X position of the component
     */
    int getXPos();

    /**
     * This method returns the Y position of the component
     * 
     * @return An integer representing the Y position of the component
     */
    int getYPos();

    /**
     * This method is called to set the new X position of the component
     * 
     * @param x
     *            The new X position
     */
    void setXPos(int x);

    /**
     * This method is called to set the new Y position of the component
     * 
     * @param y
     *            The new Y position
     */
    void setYPos(int y);

    /**
     * This method is called to set both the new X and Y position of the component
     * 
     * @param x
     *            The X position
     * @param y
     *            The Y position
     */
    void setXYPos(int x, int y);

    /**
     * This method returns the type of the component
     * 
     * @return The type of the component
     */
    ComponentType getType();

}
