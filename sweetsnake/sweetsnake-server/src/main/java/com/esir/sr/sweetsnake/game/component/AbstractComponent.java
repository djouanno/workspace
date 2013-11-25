package com.esir.sr.sweetsnake.game.component;

import org.apache.commons.lang3.RandomStringUtils;

import com.esir.sr.sweetsnake.api.IComponent;
import com.esir.sr.sweetsnake.constants.PropertiesConstants;
import com.esir.sr.sweetsnake.enumeration.ComponentType;
import com.esir.sr.sweetsnake.enumeration.MoveDirection;

/**
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
public abstract class AbstractComponent implements IComponent
{

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    /** The element id */
    protected String              id;

    /** The element x position on the game map */
    protected int                 x;

    /** The element y position on the game map */
    protected int                 y;

    /** The element type */
    protected final ComponentType type;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * 
     * @param _type
     */
    protected AbstractComponent(final ComponentType _type) {
        id = RandomStringUtils.randomAlphanumeric(PropertiesConstants.GENERATED_ID_LENGTH);
        type = _type;
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeElement#move(com.esir.sr.sweetsnake.enumeration.Direction )
     */
    @Override
    public abstract void move(MoveDirection direction);

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return type + "[id=" + id + ", x=" + x + ", y=" + y + "]";
    }

    /**********************************************************************************************
     * [BLOCK] GETTERS
     **********************************************************************************************/

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeElement#getId()
     */
    @Override
    public String getId() {
        return id;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeElement#getX()
     */
    @Override
    public int getXPos() {
        return x;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeElement#getY()
     */
    @Override
    public int getYPos() {
        return y;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeElement#getType()
     */
    @Override
    public ComponentType getType() {
        return type;
    }

    /**********************************************************************************************
     * [BLOCK] SETTERS
     **********************************************************************************************/

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeElement#setX(int)
     */
    @Override
    public void setXPos(final int _x) {
        x = _x;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeElement#setY(int)
     */
    @Override
    public void setYPos(final int _y) {
        y = _y;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeElement#setXY(int, int)
     */
    @Override
    public void setXYPos(final int _x, final int _y) {
        x = _x;
        y = _y;
    }

}
