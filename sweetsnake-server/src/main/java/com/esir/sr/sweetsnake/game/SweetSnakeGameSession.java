package com.esir.sr.sweetsnake.game;

import org.slf4j.LoggerFactory;

import com.esir.sr.sweetsnake.api.ISweetSnakeGameSession;
import com.esir.sr.sweetsnake.api.ISweetSnakePlayer;

public class SweetSnakeGameSession implements ISweetSnakeGameSession
{
    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(SweetSnakeGameSession.class);

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    private final ISweetSnakePlayer       player1, player2;

    // TODO matrice de jeu etc...

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    public SweetSnakeGameSession(final ISweetSnakePlayer _player1, final ISweetSnakePlayer _player2) {
        log.info("Initializing a new game session between {} and {}", _player1.getName(), _player2.getName());
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
        log.info("Starting game session between {} and {}", player1.getName(), player2.getName());
        // TODO
    }

    /**********************************************************************************************
     * [BLOCK] GETTERS
     **********************************************************************************************/

    @Override
    public ISweetSnakePlayer getPlayer1() {
        return player1;
    }

    @Override
    public ISweetSnakePlayer getPlayer2() {
        return player2;
    }

    /**********************************************************************************************
     * [BLOCK] SETTERS
     **********************************************************************************************/



}
