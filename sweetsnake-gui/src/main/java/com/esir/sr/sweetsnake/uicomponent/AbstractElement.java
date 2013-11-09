package com.esir.sr.sweetsnake.uicomponent;

import java.awt.Graphics;

import javax.swing.JComponent;

import com.esir.sr.sweetsnake.api.IElement;
import com.esir.sr.sweetsnake.constants.GameConstants;
import com.esir.sr.sweetsnake.enumeration.ElementType;
import com.esir.sr.sweetsnake.enumeration.MoveDirection;

/**
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
public abstract class AbstractElement extends JComponent implements IElement
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    /** The serial version UID */
    private static final long serialVersionUID = 3748120944354885599L;

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    /** The element id */
    protected String          id;

    /** The element x position on the game map */
    protected int             x;

    /** The element y position on the game map */
    protected int             y;

    /** The element type */
    protected ElementType     type;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * 
     * @param _id
     * @param _x
     * @param _y
     * @param _type
     */
    protected AbstractElement(final String _id, final int _x, final int _y, final ElementType _type) {
        super();
        id = _id;
        x = _x;
        y = _y;
        type = _type;
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
    public void move(final MoveDirection direction) {
        x = (x + direction.getValue()[0] + GameConstants.GRID_SIZE) % GameConstants.GRID_SIZE;
        y = (y + direction.getValue()[1] + GameConstants.GRID_SIZE) % GameConstants.GRID_SIZE;
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.JComponent#paint(java.awt.Graphics)
     */
    @Override
    public void paint(final Graphics g) {
        drawShape(g, GameConstants.CELL_SIZE / 2 + x * GameConstants.CELL_SIZE, GameConstants.CELL_SIZE / 2 + y * GameConstants.CELL_SIZE, GameConstants.CELL_SIZE, GameConstants.CELL_SIZE);
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
    public ElementType getType() {
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
