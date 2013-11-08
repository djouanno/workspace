package com.esir.sr.sweetsnake.api;

import com.esir.sr.sweetsnake.enumeration.ElementType;
import com.esir.sr.sweetsnake.enumeration.MoveDirection;

/**
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
public interface IElement
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
    int getX();

    /**
     * 
     * @return
     */
    int getY();

    /**
     * 
     * @param x
     */
    void setX(int x);

    /**
     * 
     * @param y
     */
    void setY(int y);

    /**
     * 
     * @param x
     * @param y
     */
    void setXY(int x, int y);

    /**
     * 
     * @return
     */
    ElementType getType();

}
