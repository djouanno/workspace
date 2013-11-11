package com.esir.sr.sweetsnake.session;

import java.rmi.RemoteException;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.LoggerFactory;

import com.esir.sr.sweetsnake.constants.PropertiesConstants;
import com.esir.sr.sweetsnake.enumeration.MoveDirection;
import com.esir.sr.sweetsnake.factory.DtoConverterFactory;
import com.esir.sr.sweetsnake.game.engine.GameEngine;

/**
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
public class GameSession
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    /** The logger */
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(GameSession.class);

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    /** The game engine */
    private final GameEngine              engine;

    /** The session id */
    private final String                  id;

    /** The session first player */
    private final Player                  player1;

    /** The session second player */
    private final Player                  player2;

    /** Is the game started */
    private boolean                       isGameStarted;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * 
     * @param _player1
     * @param _player2
     */
    public GameSession(final Player _player1, final Player _player2) {
        log.info("Initializing a new game session between {} and {}", _player1, _player2);
        id = RandomStringUtils.randomAlphanumeric(PropertiesConstants.GENERATED_ID_LENGTH);
        player1 = _player1;
        player2 = _player2;
        engine = new GameEngine(this);
    }

    /**********************************************************************************************
     * [BLOCK] PRIVATE METHODS
     **********************************************************************************************/

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    /**
     * 
     */
    public void startGame() {
        try {
            player1.getClientCallback().gameStarted(DtoConverterFactory.convertGameSession(this));
            player2.getClientCallback().gameStarted(DtoConverterFactory.convertGameSession(this));
            // TODO maybe reconfirm from client side to synchronize game start
            isGameStarted = true;
            log.info("Game session between {} and {} is started", player1, player2);
        } catch (final RemoteException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 
     */
    public void stopGame() {
        try {
            player1.getClientCallback().gameLeaved(DtoConverterFactory.convertGameSession(this));
            player2.getClientCallback().gameLeaved(DtoConverterFactory.convertGameSession(this));
        } catch (final RemoteException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 
     * @param player
     * @param direction
     */
    public void movePlayer(final Player player, final MoveDirection direction) {
        engine.moveSnake(direction, player);
    }

    /**
     * 
     * @return
     */
    public boolean isGameStarted() {
        return isGameStarted;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "session[id=" + id + ", player1=" + player1 + ", player2=" + player2 + ", started=" + isGameStarted + "]";
    }

    /**********************************************************************************************
     * [BLOCK] GETTERS
     **********************************************************************************************/

    /**
     * 
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * @return
     */
    public Player getPlayer1() {
        return player1;
    }

    /**
     * 
     * @return
     */
    public Player getPlayer2() {
        return player2;
    }

    /**
     * 
     * @return
     */
    public GameEngine getGameEngine() {
        return engine;
    }

}
