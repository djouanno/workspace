package com.esir.sr.sweetsnake.game;

import org.slf4j.LoggerFactory;

import com.esir.sr.sweetsnake.enumeration.Direction;
import com.esir.sr.sweetsnake.session.SweetSnakeGameSession;

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

    public SweetSnakeSnake() {
        super(Type.SNAKE);
    }

    /**********************************************************************************************
     * [BLOCK] PRIVATE METHODS
     **********************************************************************************************/



    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    @Override
    public void move(final Direction direction) {
        x = (x + direction.getValue()[0] + SweetSnakeGameSession.GRID_SIZE) % SweetSnakeGameSession.GRID_SIZE;
        y = (y + direction.getValue()[1] + SweetSnakeGameSession.GRID_SIZE) % SweetSnakeGameSession.GRID_SIZE;
    }

    /**********************************************************************************************
     * [BLOCK] GETTERS
     **********************************************************************************************/



    /**********************************************************************************************
     * [BLOCK] SETTERS
     **********************************************************************************************/



}
