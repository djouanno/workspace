package com.esir.sr.sweetsnake.api;

import com.esir.sr.sweetsnake.enumeration.ComponentType;
import com.esir.sr.sweetsnake.enumeration.MoveDirection;

/**
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
public interface IComponent
{

    /**
     * 
     * @return
     */
    String getId();

    /**
     * 
     * @param direction
     */
    void move(MoveDirection direction);

    /**
     * 
     * @return
     */
    int getXPos();

    /**
     * 
     * @return
     */
    int getYPos();

    /**
     * 
     * @param x
     */
    void setXPos(int x);

    /**
     * 
     * @param y
     */
    void setYPos(int y);

    /**
     * 
     * @param x
     * @param y
     */
    void setXYPos(int x, int y);

    /**
     * 
     * @return
     */
    ComponentType getType();

}
