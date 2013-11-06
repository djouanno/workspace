package com.esir.sr.sweetsnake.client;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.esir.sr.sweetsnake.api.ISweetSnakeClient;
import com.esir.sr.sweetsnake.api.ISweetSnakeClientCallback;
import com.esir.sr.sweetsnake.api.ISweetSnakeGui;
import com.esir.sr.sweetsnake.api.ISweetSnakeServer;
import com.esir.sr.sweetsnake.dto.SweetSnakeGameRequestDTO;
import com.esir.sr.sweetsnake.dto.SweetSnakeGameSessionDTO;
import com.esir.sr.sweetsnake.dto.SweetSnakePlayerDTO;
import com.esir.sr.sweetsnake.enumeration.SweetSnakeDirection;
import com.esir.sr.sweetsnake.enumeration.SweetSnakePlayerStatus;
import com.esir.sr.sweetsnake.exception.GameRequestNotFoundException;
import com.esir.sr.sweetsnake.exception.PlayerNotAvailableException;
import com.esir.sr.sweetsnake.exception.PlayerNotFoundException;
import com.esir.sr.sweetsnake.exception.UnableToConnectException;
import com.esir.sr.sweetsnake.service.SweetSnakeRmiService;

@Component
public class SweetSnakeClient implements ISweetSnakeClient
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    private static final Logger       log = LoggerFactory.getLogger(SweetSnakeClient.class);

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    @Autowired
    private ISweetSnakeClientCallback callback;

    @Autowired
    private SweetSnakeRmiService      rmiService;

    private ISweetSnakeServer         server;

    /** The GUI */
    @Autowired
    private ISweetSnakeGui            gui;

    /** The player name */
    private String                    username;

    /** The player status */
    private SweetSnakePlayerStatus    status;

    /** The game request DTO, can be sent only once at a time */
    private SweetSnakeGameRequestDTO  sentRequestDTO;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR & INIT
     **********************************************************************************************/

    /**
     * 
     */
    protected SweetSnakeClient() {
        super();
    }

    /**
     * 
     */
    @PostConstruct
    protected void init() {
        log.info("Initialiazing a new SweetSnakeClient");
        server = rmiService.getRmiService();
        if (server == null) {
            gui.serverNotReachable();
        } else {
            status = SweetSnakePlayerStatus.DISCONNECTED;
            gui.serverReachable();
        }
    }

    /**
     * 
     */
    @PreDestroy
    protected void destroy() {
        log.info("Destroying SweetSnakeClient");
        if (status != SweetSnakePlayerStatus.DISCONNECTED) {
            disconnect();
        }
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
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeClient#reachServer()
     */
    @Override
    public void reachServer() {
        if (server == null) {
            rmiService.retryReach();
            server = rmiService.getRmiService();
            if (server == null) {
                gui.serverNotReachable();
            } else {
                gui.serverReachable();
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeClient#connect(java.lang.String)
     */
    @Override
    public void connect(final String _username) throws UnableToConnectException {
        if (_username == null || _username.isEmpty()) {
            throw new UnableToConnectException("invalid username");
        }
        log.debug("Connecting with username {}", _username);
        username = new String(_username);
        server.connect(callback);
        status = SweetSnakePlayerStatus.AVAILABLE;
        gui.successfullyConnected();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeClient#disconnect()
     */
    @Override
    public void disconnect() {
        try {
            server.disconnect(callback);
            status = SweetSnakePlayerStatus.DISCONNECTED;
        } catch (final PlayerNotFoundException e) {
            log.error(e.getMessage(), e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeClient#requestGame(com.esir.sr.sweetsnake.dto.SweetSnakePlayerDTO)
     */
    @Override
    public void requestGame(final SweetSnakePlayerDTO player) {
        if (status == SweetSnakePlayerStatus.INVITING) {
            final int answer = gui.displayCustomMessage("Your already asked for a game with " + sentRequestDTO.getRequestedPlayerName() + "\nPlease wait for your opponent to respond or cancel the request", new String[] { "wait", "cancel request" });
            if (answer == 1) {
                try {
                    server.cancelGameRequest(callback, sentRequestDTO);
                    status = SweetSnakePlayerStatus.AVAILABLE;
                } catch (PlayerNotFoundException | GameRequestNotFoundException e) {
                    gui.displayErrorMessage(e.getMessage());
                }
            }
        } else {
            try {
                sentRequestDTO = server.requestGame(callback, player);
                status = SweetSnakePlayerStatus.INVITING;
                gui.displayInfoMessage("Your request has been sent to " + player.getName());
            } catch (PlayerNotFoundException | PlayerNotAvailableException e) {
                gui.displayErrorMessage(e.getMessage());
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeClient#requestGame(com.esir.sr.sweetsnake.dto.SweetSnakeGameRequestDTO)
     */
    @Override
    public void requestGame(final SweetSnakeGameRequestDTO requestDTO) {
        status = SweetSnakePlayerStatus.INVITED;
        final int answer = gui.displayCustomMessage(requestDTO.getRequestingPlayerName() + " wants to play with you", new String[] { "accept", "deny" });
        if (answer == 0) {
            try {
                server.acceptGame(callback, requestDTO);
            } catch (PlayerNotFoundException | GameRequestNotFoundException e) {
                gui.displayErrorMessage(e.getMessage());
            }
        } else {
            try {
                server.refuseGame(callback, requestDTO);
            } catch (final GameRequestNotFoundException e) {
                gui.displayErrorMessage(e.getMessage());
            }
            status = SweetSnakePlayerStatus.AVAILABLE;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeClient#requestRefused(com.esir.sr.sweetsnake.dto.SweetSnakeGameRequestDTO)
     */
    @Override
    public void requestRefused(final SweetSnakeGameRequestDTO requestDTO) {
        status = SweetSnakePlayerStatus.AVAILABLE;
        gui.displayInfoMessage(requestDTO.getRequestedPlayerName() + " has denied your request");
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeClient#startGame(com.esir.sr.sweetsnake.dto.SweetSnakeGameSessionDTO)
     */
    @Override
    public void startGame(final SweetSnakeGameSessionDTO session) {
        gui.startGame(/* TODO send paramters (game map...)) */);
        status = SweetSnakePlayerStatus.PLAYING;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeClient#confirmMove(com.esir.sr.sweetsnake.enumeration.SweetSnakeDirection)
     */
    @Override
    public void confirmMove(final SweetSnakeDirection direction) {
        gui.moveSnake(direction);
    }

    /**********************************************************************************************
     * [BLOCK] GETTERS
     **********************************************************************************************/

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeClient#getName()
     */
    @Override
    public String getUsername() {
        return username;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeClient#getStatus()
     */
    @Override
    public SweetSnakePlayerStatus getStatus() {
        return status;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeClient#getScore()
     */
    @Override
    public long getScore() {
        return 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeClient#getPlayersList()
     */
    @Override
    public List<SweetSnakePlayerDTO> getPlayersList() {
        return server.getPlayersList(callback);
    }

    /**********************************************************************************************
     * [BLOCK] SETTERS
     **********************************************************************************************/

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeClient#setScore(long)
     */
    @Override
    public void setScore(final long score) {
        log.debug("New client score set to {}", score);
    }

}
