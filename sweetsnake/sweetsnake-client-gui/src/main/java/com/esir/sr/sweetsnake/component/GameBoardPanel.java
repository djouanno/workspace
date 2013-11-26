package com.esir.sr.sweetsnake.component;

import java.awt.Dimension;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.esir.sr.sweetsnake.api.IComponent;
import com.esir.sr.sweetsnake.constants.GameConstants;

/**
 * This class graphically reprents the gameboard by extending the JPanel class.
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 * @see javax.swing.JPanel
 */
public class GameBoardPanel extends JPanel
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    /** The serial version UID */
    private static final long             serialVersionUID = 8531962414769780455L;

    /** The logger */
    private static final Logger           log              = LoggerFactory.getLogger(GameBoardPanel.class);

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    /** The gameboard width */
    private final int                     width;

    /** The gameboard height */
    private final int                     height;

    /** The gameboard components */
    private final Map<String, IComponent> components;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * Creates a new gameboard panel
     * 
     * @param _width
     *            The gameboard width
     * @param _height
     *            The gameboard height
     */
    public GameBoardPanel(final int _width, final int _height) {
        super();
        width = _width;
        height = _height;
        components = new LinkedHashMap<String, IComponent>();
        setLayout(null);
        setOpaque(false);
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    /**
     * This method adds a component to the gameboard panel
     * 
     * @param component
     *            The component to add
     */
    public void addComponent(final IComponent component) {
        log.debug("Adding component {} on the map", component);
        if (component != null) {
            components.put(component.getId(), component);
            final JComponent jcomponent = (JComponent) component;
            final int x = component.getXPos(), y = component.getYPos();
            jcomponent.setLocation(x * GameConstants.CELL_SIZE, y * GameConstants.CELL_SIZE);
            add(jcomponent);
        }
    }

    /**
     * This method moves a component on the gameboard panel
     * 
     * @param component
     *            The component to move
     */
    public void moveComponent(final IComponent component) {
        log.debug("Moving component {} on the map", component);
        if (component != null) {
            final JComponent jcomponent = (JComponent) component;
            final int x = component.getXPos(), y = component.getYPos();
            jcomponent.setLocation(x * GameConstants.CELL_SIZE, y * GameConstants.CELL_SIZE);
        }
    }

    /**
     * This method removes a component from the gameboard panel
     * 
     * @param component
     *            The component to remove
     */
    public void removeComponent(final IComponent component) {
        log.debug("Removing component {} from the map", component);
        if (component != null) {
            components.remove(component.getId());
            remove((JComponent) component);
        }
    }

    /**
     * This method returns a gameboard component according to its id
     * 
     * @param id
     *            The component id
     * @return The component if it is found, null otherwise
     */
    public IComponent getComponentById(final String id) {
        return components.get(id);
    }

    /**********************************************************************************************
     * [BLOCK] GETTERS
     **********************************************************************************************/

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.Component#getSize()
     */
    @Override
    public Dimension getSize() {
        return new Dimension(GameConstants.CELL_SIZE * width, GameConstants.CELL_SIZE * height);
    }

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.JComponent#getPreferredSize()
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(GameConstants.CELL_SIZE * width, GameConstants.CELL_SIZE * height);
    }

}
