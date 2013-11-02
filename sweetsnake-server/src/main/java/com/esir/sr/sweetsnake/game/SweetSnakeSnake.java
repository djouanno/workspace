package com.esir.sr.sweetsnake.game;

import org.slf4j.LoggerFactory;

import com.esir.sr.sweetsnake.constants.SweetSnakeGameConstants;
import com.esir.sr.sweetsnake.enumeration.SweetSnakeDirection;
import com.esir.sr.sweetsnake.enumeration.SweetSnakeElementType;

public class SweetSnakeSnake extends SweetSnakeAbstractElement
{
    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(SweetSnakeSnake.class);

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/


    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * 
     */
    public SweetSnakeSnake() {
        super(SweetSnakeElementType.SNAKE);
    }

    /**********************************************************************************************
     * [BLOCK] PRIVATE METHODS
     **********************************************************************************************/



    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.game.SweetSnakeAbstractElement#move(com.esir.sr.sweetsnake.enumeration .Direction)
     */
    @Override
    public void move(final SweetSnakeDirection direction) {
        log.info("Moving snake to the {}", direction);
        x = (x + direction.getValue()[0] + SweetSnakeGameConstants.GRID_SIZE) % SweetSnakeGameConstants.GRID_SIZE;
        y = (y + direction.getValue()[1] + SweetSnakeGameConstants.GRID_SIZE) % SweetSnakeGameConstants.GRID_SIZE;
    }

    /**********************************************************************************************
     * [BLOCK] GETTERS
     **********************************************************************************************/



    /**********************************************************************************************
     * [BLOCK] SETTERS
     **********************************************************************************************/



}
