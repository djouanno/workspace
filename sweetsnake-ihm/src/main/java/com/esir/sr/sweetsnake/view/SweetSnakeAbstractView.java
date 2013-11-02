package com.esir.sr.sweetsnake.view;

import java.awt.Dimension;

import javax.annotation.PostConstruct;
import javax.swing.JPanel;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.esir.sr.sweetsnake.api.ISweetSnakeClient;
import com.esir.sr.sweetsnake.api.ISweetSnakeIhm;
import com.esir.sr.sweetsnake.api.ISweetSnakeView;
import com.esir.sr.sweetsnake.constants.SweetSnakeGameConstants;

public abstract class SweetSnakeAbstractView extends JPanel implements ISweetSnakeView
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    private static final long             serialVersionUID = 3803484058346507862L;
    private static final org.slf4j.Logger log              = LoggerFactory.getLogger(SweetSnakeAbstractView.class);

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    @Autowired
    protected ISweetSnakeIhm              ihm;

    @Autowired
    protected ISweetSnakeClient           client;

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
        setSize(new Dimension(SweetSnakeGameConstants.CELL_SIZE * (SweetSnakeGameConstants.GRID_SIZE + 1), SweetSnakeGameConstants.CELL_SIZE * (SweetSnakeGameConstants.GRID_SIZE + 1)));
        setPreferredSize(new Dimension(SweetSnakeGameConstants.CELL_SIZE * (SweetSnakeGameConstants.GRID_SIZE + 1), SweetSnakeGameConstants.CELL_SIZE * (SweetSnakeGameConstants.GRID_SIZE + 1)));
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
