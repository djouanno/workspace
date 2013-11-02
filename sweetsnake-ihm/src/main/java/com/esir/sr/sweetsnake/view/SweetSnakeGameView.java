package com.esir.sr.sweetsnake.view;

import javax.annotation.PostConstruct;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.esir.sr.sweetsnake.api.ISweetSnakeElement;
import com.esir.sr.sweetsnake.constants.SweetSnakeGameConstants;

@Component
public class SweetSnakeGameView extends SweetSnakeAbstractView
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    private static final long             serialVersionUID = 6919247419837806743L;
    private static final org.slf4j.Logger log              = LoggerFactory.getLogger(SweetSnakeGameView.class);

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    ISweetSnakeElement[][]                gameMap;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    public SweetSnakeGameView() {
        super();
    }

    /**********************************************************************************************
     * [BLOCK] INIT METHOD
     **********************************************************************************************/

    @PostConstruct
    public void init() {
        log.info("Initializing a new SweetSnakeGameView");
        // TODO
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    @Override
    public void build() {
        gameMap = new ISweetSnakeElement[SweetSnakeGameConstants.GRID_SIZE][SweetSnakeGameConstants.GRID_SIZE];
        // TODO
    }

}
