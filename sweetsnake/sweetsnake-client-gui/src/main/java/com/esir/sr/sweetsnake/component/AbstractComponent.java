package com.esir.sr.sweetsnake.component;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.esir.sr.sweetsnake.api.IComponent;
import com.esir.sr.sweetsnake.constants.GameConstants;
import com.esir.sr.sweetsnake.enumeration.ComponentType;
import com.esir.sr.sweetsnake.enumeration.MoveDirection;

/**
 * This class implements the IComponent interface and extends the JPanel class in order to graphically represent a game component.
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 * @see javax.swing.JPanel
 * @see com.esir.sr.sweetsnake.api.IComponent
 */
public abstract class AbstractComponent extends JPanel implements IComponent
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    /** The serial version UID */
    private static final long serialVersionUID = 3748120944354885599L;

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    /** The component id */
    protected String          id;

    /** The component x position on the gameboard */
    protected int             x;

    /** The component y position on the gameboard */
    protected int             y;

    /** The component type */
    protected ComponentType   type;

    /** The component image */
    protected ImageIcon       image;

    /** The jlabel icon */
    protected JLabel          icon;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * Creates a new component
     * 
     * @param _id
     *            The component id
     * @param _x
     *            The component X position
     * @param _y
     *            The component Y position
     * @param _type
     *            The component type
     */
    protected AbstractComponent(final String _id, final int _x, final int _y, final ComponentType _type, final String imagePath) {
        id = _id;
        x = _x;
        y = _y;
        type = _type;

        setLayout(null);
        setOpaque(false);

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
    public ComponentType getType() {
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
