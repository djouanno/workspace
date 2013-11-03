package com.esir.sr.sweetsnake.view;

import java.awt.Dimension;

import javax.annotation.PostConstruct;
import javax.swing.JPanel;

import org.springframework.beans.factory.annotation.Autowired;

import com.esir.sr.sweetsnake.api.ISweetSnakeClient;
import com.esir.sr.sweetsnake.api.ISweetSnakeIhm;
import com.esir.sr.sweetsnake.api.ISweetSnakeView;

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
    protected ISweetSnakeIhm    ihm;

    @Autowired
    protected ISweetSnakeClient client;

    protected Dimension         dimension;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    public SweetSnakeAbstractView() {
        super();
    }

    /**********************************************************************************************
     * [BLOCK] INIT METHOD
     **********************************************************************************************/

    @PostConstruct
    public void initAbs() {
        dimension = new Dimension(414, 392); // TODO calculate or retrieve this (424 x 402 - ihm offset)
        setSize(dimension);
        setPreferredSize(dimension);
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
    public abstract void build();

}
