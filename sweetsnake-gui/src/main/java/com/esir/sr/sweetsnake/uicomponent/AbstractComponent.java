package com.esir.sr.sweetsnake.uicomponent;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.esir.sr.sweetsnake.api.IElement;
import com.esir.sr.sweetsnake.constants.GameConstants;
import com.esir.sr.sweetsnake.constants.GuiConstants;
import com.esir.sr.sweetsnake.enumeration.ElementType;
import com.esir.sr.sweetsnake.enumeration.MoveDirection;

/**
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
public abstract class AbstractComponent extends JPanel implements IElement
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

    /** The image */
    protected ImageIcon       image;

    /** The jlabel icon */
    protected JLabel          icon;

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
    protected AbstractComponent(final String _id, final int _x, final int _y, final ElementType _type) {
        id = _id;
        x = _x;
        y = _y;
        type = _type;

        setLayout(null);
        setOpaque(false);

        final String imagePath = type == ElementType.SNAKE ? GuiConstants.SNAKE_ICON_PATH : GuiConstants.SWEET_ICON_PATH;
        image = new ImageIcon(AbstractComponent.class.getResource(imagePath));

        final Dimension dimension = new Dimension(image.getIconWidth(), image.getIconHeight());
        setSize(dimension);
        setPreferredSize(dimension);

        icon = new JLabel(image);
        icon.setSize(dimension);
        icon.setPreferredSize(dimension);
        icon.setLocation(0, 0);

        add(icon);
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

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
     * @see java.awt.Component#toString()
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
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeElement#getXPos()
     */
    @Override
    public int getXPos() {
        return x;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeElement#getYPos()
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
    public ElementType getType() {
        return type;
    }

    /**********************************************************************************************
     * [BLOCK] SETTERS
     **********************************************************************************************/

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeElement#setXYPos(int, int)
     */
    @Override
    public void setXYPos(final int _x, final int _y) {
        x = _x;
        y = _y;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeElement#setXPos(int)
     */
    @Override
    public void setXPos(final int _x) {
        x = _x;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeElement#setYPos(int)
     */
    @Override
    public void setYPos(final int _y) {
        y = _y;
    }

}
