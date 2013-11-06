package com.esir.sr.sweetsnake.component;

import java.awt.Graphics;

import javax.swing.JComponent;

import org.apache.commons.lang3.RandomStringUtils;

import com.esir.sr.sweetsnake.api.ISweetSnakeElement;
import com.esir.sr.sweetsnake.api.ISweetSnakeGui;
import com.esir.sr.sweetsnake.constants.SweetSnakeGameConstants;
import com.esir.sr.sweetsnake.constants.SweetSnakePropertiesConstants;
import com.esir.sr.sweetsnake.enumeration.SweetSnakeDirection;
import com.esir.sr.sweetsnake.enumeration.SweetSnakeElementType;

public abstract class SweetSnakeAbstractElement extends JComponent implements ISweetSnakeElement
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    private static final long       serialVersionUID = 3748120944354885599L;

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    protected ISweetSnakeGui        ihm;
    protected String                id;
    protected int                   x, y;
    protected SweetSnakeElementType type;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * 
     * @param _ihm
     */
    protected SweetSnakeAbstractElement(final ISweetSnakeGui _ihm) {
        super();
        id = RandomStringUtils.randomAlphanumeric(SweetSnakePropertiesConstants.GENERATED_ID_LENGTH);
        ihm = _ihm;
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    /**
     * 
     * @param g
     * @param x
     * @param y
     * @param w
     * @param h
     */
    public abstract void drawShape(Graphics g, int x, int y, int w, int h);

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeElement#move(com.esir.sr.sweetsnake.enumeration.SweetSnakeDirection)
     */
    @Override
    public void move(final SweetSnakeDirection direction) {
        x = (x + direction.getValue()[0] + SweetSnakeGameConstants.GRID_SIZE) % SweetSnakeGameConstants.GRID_SIZE;
        y = (y + direction.getValue()[1] + SweetSnakeGameConstants.GRID_SIZE) % SweetSnakeGameConstants.GRID_SIZE;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.JComponent#paint(java.awt.Graphics)
     */
    @Override
    public void paint(final Graphics g) {
        drawShape(g, SweetSnakeGameConstants.CELL_SIZE / 2 + x * SweetSnakeGameConstants.CELL_SIZE, SweetSnakeGameConstants.CELL_SIZE / 2 + y * SweetSnakeGameConstants.CELL_SIZE, SweetSnakeGameConstants.CELL_SIZE, SweetSnakeGameConstants.CELL_SIZE);
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
     * @see javax.swing.JComponent#getX()
     */
    @Override
    public int getX() {
        return x;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.JComponent#getY()
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
    public SweetSnakeElementType getType() {
        return type;
    }

    /**********************************************************************************************
     * [BLOCK] SETTERS
     **********************************************************************************************/

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

}
