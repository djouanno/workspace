package com.esir.sr.sweetsnake.view;

import java.awt.Dimension;

import javax.swing.JPanel;

import org.springframework.beans.factory.annotation.Autowired;

import com.esir.sr.sweetsnake.api.IServerForAdmin;
import com.esir.sr.sweetsnake.gui.ServerGui;

/**
 * This class provides the common behaviors for any GUI view.<br />
 * It extends the JPanel class to be graphically displayed.
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 * @see javax.swing.JPanel
 */
public abstract class AbstractView extends JPanel
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    /** The serial version UID */
    private static final long serialVersionUID = 2898344616680884755L;

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    /** The server */
    @Autowired(required = false)
    protected IServerForAdmin server;

    /** The GUI */
    @Autowired
    protected ServerGui       gui;

    /** The view dimension */
    protected Dimension       dimension;

    /** Is the view builded */
    protected boolean         isBuilded;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR & INIT
     **********************************************************************************************/

    /**
     * Creates a new view
     */
    protected AbstractView() {
        super();
    }

    /**
     * Initializes a new view
     */
    protected void init() {
        setOpaque(false);
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    /**
     * This method builds the view if it has not already been built
     */
    public void build() {
        if (!isBuilded) {
            buildImpl();
            isBuilded = true;
        }
    }

    /**
     * This method unbuilds the view if it has already been built
     */
    public void unbuild() {
        if (isBuilded) {
            removeAll();
            isBuilded = false;
        }
    }

    /**
     * This method provides the implemented code to build the view
     */
    protected abstract void buildImpl();

    /**********************************************************************************************
     * [BLOCK] GETTERS
     **********************************************************************************************/

    /**
     * This method returns the dimension of the view
     * 
     * @return The dimension of the view
     */
    public Dimension getDimension() {
        return dimension;
    }

}
