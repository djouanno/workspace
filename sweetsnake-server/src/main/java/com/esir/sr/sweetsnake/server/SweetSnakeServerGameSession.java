package com.esir.sr.sweetsnake.server;

import org.slf4j.LoggerFactory;

import com.esir.sr.sweetsnake.server.api.IPlayer;
import com.esir.sr.sweetsnake.server.api.ISweetSnakeServerGameSession;

public class SweetSnakeServerGameSession implements ISweetSnakeServerGameSession
{
    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(SweetSnakeServerGameSession.class);

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    private final IPlayer                 player1, player2;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    public SweetSnakeServerGameSession(final IPlayer _player1, final IPlayer _player2) {
        log.info("Initializing a new game session between players {} and {}", _player1.getName(), _player2.getName());
        player1 = _player1;
        player2 = _player2;
    }

    /**********************************************************************************************
     * [BLOCK] PRIVATE METHODS
     **********************************************************************************************/



    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    @Override
    public void startGame() {
        log.info("Starting game between players {} and {}", player1.getName(), player2.getName());
        // TODO
    }

    /**********************************************************************************************
     * [BLOCK] GETTERS
     **********************************************************************************************/



    /**********************************************************************************************
     * [BLOCK] SETTERS
     **********************************************************************************************/



}
