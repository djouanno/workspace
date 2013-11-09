package com.esir.sr.sweetsnake.uicomponent;

import javax.swing.JComponent;
import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.esir.sr.sweetsnake.api.IElement;

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

    /** The game map */
    IElement[][]                gameMap;

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
        gameMap = new IElement[width][height];
        setOpaque(true);
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
            gameMap[element.getX()][element.getX()] = element;
            final JComponent comp = (JComponent) element;
            comp.setAlignmentX(element.getX());
            comp.setAlignmentY(element.getY());
            add(comp);
            comp.setVisible(true);
        }
    }

    /**
     * 
     * @param element
     */
    public void removeElement(final IElement element) {
        log.debug("Removing element {} from the map", element);
        if (element != null) {
            gameMap[element.getX()][element.getX()] = null;
            remove((JComponent) element);
        }
    }

    /**********************************************************************************************
     * [BLOCK] PRIVATE METHODS
     **********************************************************************************************/



    /**********************************************************************************************
     * [BLOCK] GETTERS
     **********************************************************************************************/



    /**********************************************************************************************
     * [BLOCK] SETTERS
     **********************************************************************************************/



}
