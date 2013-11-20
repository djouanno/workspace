package com.esir.sr.sweetsnake.view;

import java.awt.Dimension;

import javax.swing.JPanel;

import org.springframework.beans.factory.annotation.Autowired;

import com.esir.sr.sweetsnake.api.IServerForAdmin;
import com.esir.sr.sweetsnake.gui.ServerGui;

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
    private static final long serialVersionUID = 2898344616680884755L;

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    /** The server */
    @Autowired
    protected IServerForAdmin    server;

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
            buildImpl();
            isBuilded = true;
        }
    }

    /**
     * 
     */
    public void unbuild() {
        if (isBuilded) {
            removeAll();
            isBuilded = false;
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
