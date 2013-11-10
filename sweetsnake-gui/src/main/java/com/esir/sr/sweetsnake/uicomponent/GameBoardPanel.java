package com.esir.sr.sweetsnake.uicomponent;

import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.esir.sr.sweetsnake.api.IElement;
import com.esir.sr.sweetsnake.constants.GameConstants;

public class GameBoardPanel extends JPanel
{
    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    /** The serial version UID */
    private static final long   serialVersionUID = 8531962414769780455L;

    /** The logger */
    private static final Logger log              = LoggerFactory.getLogger(GameBoardPanel.class);

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    /** The map width */
    private final int           width;

    /** The map height */
    private final int           height;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * 
     * @param _width
     * @param _height
     * @param _nbSweets
     */
    public GameBoardPanel(final int _width, final int _height) {
        super();
        width = _width;
        height = _height;
        setLayout(null);
        setOpaque(false);
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    /**
     * 
     * @param element
     */
    public void setElement(final IElement element) {
        log.debug("Setting element {} on the map", element);
        if (element != null) {
            final JComponent comp = (JComponent) element;
            comp.setLocation(element.getXPos() * GameConstants.CELL_SIZE, element.getYPos() * GameConstants.CELL_SIZE);
            add(comp);
            revalidate();
            repaint();
        }
    }

    /**
     * 
     * @param element
     */
    public void removeElement(final IElement element) {
        log.debug("Removing element {} from the map", element);
        if (element != null) {
            remove((JComponent) element);
            revalidate();
            repaint();
        }
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
