package com.esir.sr.sweetsnake.api;

import com.esir.sr.sweetsnake.enumeration.SweetSnakeDirection;
import com.esir.sr.sweetsnake.enumeration.SweetSnakeElementType;

public interface ISweetSnakeElement
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
    void move(SweetSnakeDirection direction);

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
    SweetSnakeElementType getType();

}
