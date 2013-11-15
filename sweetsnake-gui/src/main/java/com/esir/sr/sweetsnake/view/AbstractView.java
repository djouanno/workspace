package com.esir.sr.sweetsnake.view;

import java.awt.Dimension;

import javax.swing.JPanel;

import org.springframework.beans.factory.annotation.Autowired;

import com.esir.sr.sweetsnake.api.IClient;
import com.esir.sr.sweetsnake.constants.GuiConstants;
import com.esir.sr.sweetsnake.gui.Gui;

/**
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
public abstract class AbstractView extends JPanel
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    /** The serial version UID */
    private static final long serialVersionUID = 3803484058346507862L;

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    /** The client */
    @Autowired
    protected IClient         client;

    /** The GUI */
    @Autowired
    protected Gui             gui;

    /** The view dimension */
    protected Dimension       dimension;

    /** Is the view builded */
    protected boolean         isBuilded;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR & INIT
     **********************************************************************************************/

    /**
     * 
     */
    protected AbstractView() {
        super();
    }

    /**
     * 
     */
    protected void init() {
        setOpaque(false);
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    /**
     * 
     */
    public void build() {
        if (!isBuilded) {
            final Dimension frameDimension = gui.getContentPane().getSize();
            dimension = new Dimension(frameDimension.width - GuiConstants.GUI_OFFSET, frameDimension.height - GuiConstants.GUI_OFFSET);
            setSize(dimension);
            setPreferredSize(dimension);
            buildImpl();
            isBuilded = true;
        }

    }

    /**
     * 
     */
    protected abstract void buildImpl();

    /**********************************************************************************************
     * [BLOCK] GETTERS
     **********************************************************************************************/

    /**
     * 
     * @return
     */
    public Dimension getDimension() {
        return dimension;
    }

}
