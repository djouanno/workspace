package com.esir.sr.sweetsnake.api;

import com.esir.sr.sweetsnake.enumeration.Direction;
import com.esir.sr.sweetsnake.game.SweetSnakeAbstractElement.Type;

public interface ISweetSnakeElement
{

    /**
     * 
     * @param direction
     */
    void move(Direction direction);

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
    Type getType();

}
