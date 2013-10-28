package com.esir.sr.sweetsnake.game;

import org.slf4j.LoggerFactory;

import com.esir.sr.sweetsnake.api.ISweetSnakeElement;
import com.esir.sr.sweetsnake.enumeration.Direction;

public abstract class SweetSnakeAbstractElement implements ISweetSnakeElement
{
    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(SweetSnakeAbstractElement.class);

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    protected final Type                  type;
    protected int                         x, y;

    public enum Type
    {
        SNAKE, SWEET;
    }

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    public SweetSnakeAbstractElement(final Type _type) {
        type = _type;
    }

    /**********************************************************************************************
     * [BLOCK] PRIVATE METHODS
     **********************************************************************************************/



    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    @Override
    public abstract void move(Direction direction);

    /**********************************************************************************************
     * [BLOCK] GETTERS
     **********************************************************************************************/

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Type getType() {
        return type;
    }

    /**********************************************************************************************
     * [BLOCK] SETTERS
     **********************************************************************************************/

    @Override
    public void setX(final int _x) {
        x = _x;
    }

    @Override
    public void setY(final int _y) {
        y = _y;
    }

    @Override
    public void setXY(final int _x, final int _y) {
        x = _x;
        y = _y;
    }

}
