package com.esir.sr.sweetsnake.game;

import org.apache.commons.lang3.RandomStringUtils;

import com.esir.sr.sweetsnake.api.IElement;
import com.esir.sr.sweetsnake.constants.PropertiesConstants;
import com.esir.sr.sweetsnake.enumeration.MoveDirection;
import com.esir.sr.sweetsnake.enumeration.ElementType;

public abstract class AbstractElement implements IElement
{

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    protected String                      id;
    protected int                         x, y;
    protected final ElementType type;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * 
     * @param _type
     */
    protected AbstractElement(final ElementType _type) {
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
    public int getX() {
        return x;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeElement#getY()
     */
    @Override
    public int getY() {
        return y;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeElement#getType()
     */
    @Override
    public ElementType getType() {
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
    public void setX(final int _x) {
        x = _x;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeElement#setY(int)
     */
    @Override
    public void setY(final int _y) {
        y = _y;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeElement#setXY(int, int)
     */
    @Override
    public void setXY(final int _x, final int _y) {
        x = _x;
        y = _y;
    }

}
