package com.esir.sr.sweetsnake.client;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.esir.sr.sweetsnake.api.IClient;
import com.esir.sr.sweetsnake.api.IClientCallback;
import com.esir.sr.sweetsnake.api.IGui;
import com.esir.sr.sweetsnake.api.IServer;
import com.esir.sr.sweetsnake.dto.GameRequestDTO;
import com.esir.sr.sweetsnake.dto.GameSessionDTO;
import com.esir.sr.sweetsnake.dto.PlayerDTO;
import com.esir.sr.sweetsnake.enumeration.MoveDirection;
import com.esir.sr.sweetsnake.enumeration.PlayerStatus;
import com.esir.sr.sweetsnake.exception.GameRequestNotFoundException;
import com.esir.sr.sweetsnake.exception.PlayerNotAvailableException;
import com.esir.sr.sweetsnake.exception.PlayerNotFoundException;
import com.esir.sr.sweetsnake.exception.UnableToConnectException;
import com.esir.sr.sweetsnake.provider.RmiProvider;

/**
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
@Component
public class Client implements IClient
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    /** The logger */
    private static final Logger log = LoggerFactory.getLogger(Client.class);

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    /** The client callback */
    @Autowired
    private IClientCallback     callback;

    /** The rmi provider */
    @Autowired
    private RmiProvider         rmiProvider;

    /** The GUI */
    @Autowired
    private IGui                gui;

    /** The server */
    private IServer             server;

    /** The username */
    private String              username;

    /** The player status */
    private PlayerStatus        status;

    /** The sent request DTO (only once at a time) */
    private GameRequestDTO      sentRequestDTO;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR & INIT
     **********************************************************************************************/

    /**
     * 
     */
    protected Client() {
        super();
    }

    /**
     * 
     */
    @PostConstruct
    protected void init() {
        log.info("Initialiazing a new SweetSnakeClient");
        server = rmiProvider.getRmiService();
        if (server == null) {
            gui.serverNotReachable();
        } else {
            status = PlayerStatus.DISCONNECTED;
            gui.serverReachable();
        }
    }

    /**
     * 
     */
    @PreDestroy
    protected void destroy() {
        log.info("Destroying SweetSnakeClient");
        if (status != PlayerStatus.DISCONNECTED) {
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
            rmiProvider.retryReach();
            server = rmiProvider.getRmiService();
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
        status = PlayerStatus.AVAILABLE;
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
            status = PlayerStatus.DISCONNECTED;
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
    public void requestGame(final PlayerDTO player) {
        if (status == PlayerStatus.INVITING) {
            final int answer = gui.displayCustomMessage("Your already asked for a game with " + sentRequestDTO.getRequestedPlayerName() + "\nPlease wait for your opponent to respond or cancel the request", new String[] { "wait", "cancel request" });
            if (answer == 1) {
                try {
                    server.cancelGameRequest(callback, sentRequestDTO);
                    status = PlayerStatus.AVAILABLE;
                } catch (PlayerNotFoundException | GameRequestNotFoundException e) {
                    gui.displayErrorMessage(e.getMessage());
                }
            }
        } else {
            try {
                sentRequestDTO = server.requestGame(callback, player);
                status = PlayerStatus.INVITING;
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
    public void requestGame(final GameRequestDTO requestDTO) {
        status = PlayerStatus.INVITED;
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
            status = PlayerStatus.AVAILABLE;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeClient#requestRefused(com.esir.sr.sweetsnake.dto.SweetSnakeGameRequestDTO)
     */
    @Override
    public void requestRefused(final GameRequestDTO requestDTO) {
        status = PlayerStatus.AVAILABLE;
        gui.displayInfoMessage(requestDTO.getRequestedPlayerName() + " has denied your request");
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeClient#startGame(com.esir.sr.sweetsnake.dto.SweetSnakeGameSessionDTO)
     */
    @Override
    public void startGame(final GameSessionDTO session) {
        gui.startGame(/* TODO send paramters (game map...)) */);
        status = PlayerStatus.PLAYING;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeClient#confirmMove(com.esir.sr.sweetsnake.enumeration.SweetSnakeDirection)
     */
    @Override
    public void confirmMove(final MoveDirection direction) {
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
    public PlayerStatus getStatus() {
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
    public List<PlayerDTO> getPlayersList() {
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
