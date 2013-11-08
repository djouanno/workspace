package com.esir.sr.sweetsnake.view;

import java.awt.Dimension;

import javax.swing.JPanel;

import org.springframework.beans.factory.annotation.Autowired;

import com.esir.sr.sweetsnake.api.IClient;
import com.esir.sr.sweetsnake.api.IView;
import com.esir.sr.sweetsnake.constants.GuiConstants;
import com.esir.sr.sweetsnake.gui.Gui;

public abstract class AbstractView extends JPanel implements IView
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    private static final long   serialVersionUID = 3803484058346507862L;

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    @Autowired
    protected IClient client;

    @Autowired
    protected Gui     gui;

    protected Dimension         dimension;
    protected boolean           isBuilded;

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
        // setFocusable(true);
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeView#build()
     */
    @Override
    public void build() {
        if (!isBuilded) {
            final Dimension frameDimension = gui.getContentPane().getSize();
            dimension = new Dimension(frameDimension.width - GuiConstants.IHM_OFFSET, frameDimension.height - GuiConstants.IHM_OFFSET);
            setSize(dimension);
            setPreferredSize(dimension);
            buildImpl();
        }

    }

    /**
     * 
     */
    protected abstract void buildImpl();

}
