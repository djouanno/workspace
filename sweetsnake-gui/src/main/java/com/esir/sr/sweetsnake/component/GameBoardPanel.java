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

    /** The map width */
    private final int                     width;

    /** The map height */
    private final int                     height;

    /** The components */
    private final Map<String, IComponent> components;

    /** Is the the current player player1 */
    private final boolean                 isFirstPlayer;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * 
     * @param _width
     * @param _height
     * @param _nbSweets
     * @param _isFirstPlayer
     */
    public GameBoardPanel(final int _width, final int _height, final boolean _isFirstPlayer) {
        super();
        width = _width;
        height = _height;
        isFirstPlayer = _isFirstPlayer;
        components = new LinkedHashMap<String, IComponent>();
        setLayout(null);
        setOpaque(false);
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    /**
     * 
     * @param component
     */
    public void setComponent(final IComponent component) {
        log.debug("Setting element {} on the map", component);
        if (component != null) {
            components.put(component.getId(), component);
            final JComponent jcomponent = (JComponent) component;
            int x = component.getXPos(), y = component.getYPos();
            if (!isFirstPlayer) {
                x = width - 1 - component.getXPos();
                y = height - 1 - component.getYPos();
            }
            jcomponent.setLocation(x * GameConstants.CELL_SIZE, y * GameConstants.CELL_SIZE);
            add(jcomponent);
        }
    }

    /**
     * 
     * @param component
     */
    public void removeComponent(final IComponent component) {
        log.debug("Removing element {} from the map", component);
        if (component != null) {
            components.remove(component.getId());
            remove((JComponent) component);
        }
    }

    /**
     * 
     * @param id
     * @return
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
