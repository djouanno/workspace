package com.esir.sr.sweetsnake.view;

import java.awt.Dimension;

import javax.swing.JPanel;

import org.springframework.beans.factory.annotation.Autowired;

import com.esir.sr.sweetsnake.api.IClientForGui;
import com.esir.sr.sweetsnake.constants.ClientGuiConstants;
import com.esir.sr.sweetsnake.gui.ClientGui;
import com.esir.sr.sweetsnake.provider.BeanProvider;

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
    private static final long serialVersionUID = 3803484058346507862L;

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    /** The client */
    @Autowired(required = false)
    protected IClientForGui   client;

    /** The GUI */
    @Autowired
    protected ClientGui       gui;

    /** The bean provider */
    @Autowired
    protected BeanProvider    beanProvider;

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
            dimension = new Dimension(ClientGuiConstants.VIEW_WIDTH, ClientGuiConstants.VIEW_HEIGHT);
            setSize(dimension);
            setPreferredSize(dimension);
            buildImpl();
            isBuilded = true;
        }
    }

    /**
     * This method provides the implemented code to build the view
     */
    protected abstract void buildImpl();

    /**
     * This method clears the view
     */
    public abstract void clear();

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
