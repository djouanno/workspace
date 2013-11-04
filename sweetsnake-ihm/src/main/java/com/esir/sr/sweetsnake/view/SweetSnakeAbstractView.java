package com.esir.sr.sweetsnake.view;

import java.awt.Dimension;

import javax.swing.JPanel;

import org.springframework.beans.factory.annotation.Autowired;

import com.esir.sr.sweetsnake.api.ISweetSnakeClient;
import com.esir.sr.sweetsnake.api.ISweetSnakeView;
import com.esir.sr.sweetsnake.constants.SweetSnakeIhmConstants;
import com.esir.sr.sweetsnake.ihm.SweetSnakeIhm;

public abstract class SweetSnakeAbstractView extends JPanel implements ISweetSnakeView
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    private static final long   serialVersionUID = 3803484058346507862L;

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    @Autowired
    protected ISweetSnakeClient client;

    @Autowired
    protected SweetSnakeIhm     ihm;

    protected Dimension         dimension;
    protected boolean           isBuilded;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR & INIT
     **********************************************************************************************/

    /**
     * 
     */
    protected SweetSnakeAbstractView() {
        super();
    }

    /**
     * 
     */
    protected void init() {
        setOpaque(false);
        setFocusable(true);
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
            final Dimension frameDimension = ihm.getContentPane().getSize();
            dimension = new Dimension(frameDimension.width - SweetSnakeIhmConstants.IHM_OFFSET, frameDimension.height - SweetSnakeIhmConstants.IHM_OFFSET);
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
