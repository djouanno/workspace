package com.esir.sr.sweetsnake.client;

import java.rmi.RemoteException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
import com.esir.sr.sweetsnake.exception.GameRequestNotFoundException;
import com.esir.sr.sweetsnake.exception.GameSessionNotFoundException;
import com.esir.sr.sweetsnake.exception.MaximumNumberOfPlayersException;
import com.esir.sr.sweetsnake.exception.PlayerNotAvailableException;
import com.esir.sr.sweetsnake.exception.PlayerNotFoundException;
import com.esir.sr.sweetsnake.exception.UnableToConnectException;
import com.esir.sr.sweetsnake.exception.UnauthorizedActionException;
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
    private static final Logger  log = LoggerFactory.getLogger(Client.class);

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    /** The client callback */
    @Autowired
    private IClientCallback      callback;

    /** The rmi provider */
    @Autowired
    private RmiProvider          rmiProvider;

    /** The GUI */
    @Autowired
    private IGui                 gui;

    /** The server */
    private IServer              server;

    /** The username */
    private String               username;

    /** The sent requests DTO */
    private List<GameRequestDTO> requests;

    /** The session DTO (only one at a time) */
    private GameSessionDTO       session;

    /** Is the client connected to the server */
    private boolean              isConnected;

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
        requests = new LinkedList<GameRequestDTO>();
        server = rmiProvider.getRmiService();
        if (server == null) {
            gui.serverNotReachable();
        } else {
            gui.serverReachable();
        }
    }

    /**
     * 
     */
    @PreDestroy
    protected void destroy() {
        log.info("Destroying SweetSnakeClient");
        if (isConnected) {
            disconnect();
        }
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC GUI EXPOSED METHODS
     **********************************************************************************************/

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IClient#reachServer()
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
     * @see com.esir.sr.sweetsnake.api.IClient#connect(java.lang.String)
     */
    @Override
    public void connect(final String _username) throws UnableToConnectException {
        if (_username == null || _username.isEmpty()) {
            throw new UnableToConnectException("invalid username");
        }
        log.debug("Connecting with username {}", _username);
        username = new String(_username);
        server.connect(callback);
        isConnected = true;
        gui.connectedToServer();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IClient#disconnect()
     */
    @Override
    public void disconnect() {
        log.debug("Disconnecting from username {}", username);
        server.disconnect(callback);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IClient#sendRequest(com.esir.sr.sweetsnake.dto.PlayerDTO)
     */
    @Override
    public void sendRequest(final PlayerDTO playerDto) {
        try {
            server.sendRequest(callback, playerDto);
        } catch (PlayerNotFoundException | PlayerNotAvailableException e) {
            gui.displayErrorMessage(e.getMessage());
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IClient#readyToPlay()
     */
    @Override
    public void readyToPlay() {
        try {
            session.getCallback().ready(callback);
        } catch (final NullPointerException e) {
            gui.displayErrorMessage("you are not currently playing");
        } catch (final RemoteException e) {
            gui.displayErrorMessage(e.getMessage());
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IClient#startSession()
     */
    @Override
    public void startSession() {
        try {
            session.getCallback().startGame(callback);
        } catch (final NullPointerException e) {
            gui.displayErrorMessage("you are not currently playing");
        } catch (final UnauthorizedActionException | RemoteException e) {
            gui.displayErrorMessage(e.getMessage());
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IClient#leaveSession()
     */
    @Override
    public void leaveSession() {
        try {
            session.getCallback().leaveGame(callback);
        } catch (final NullPointerException e) {
            gui.displayErrorMessage("you are not currently playing");
        } catch (final RemoteException e) {
            gui.displayErrorMessage(e.getMessage());
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IClient#moveSnake(com.esir.sr.sweetsnake.enumeration.MoveDirection)
     */
    @Override
    public void moveSnake(final MoveDirection direction) {
        try {
            session.getCallback().move(callback, direction);
        } catch (final NullPointerException e) {
            gui.displayErrorMessage("you are not currently playing");
        } catch (final UnauthorizedActionException | RemoteException e) {
            gui.displayErrorMessage(e.getMessage());
        }
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC SERVER EXPOSED METHODS
     **********************************************************************************************/

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IClient#refreshPlayersList(java.util.List)
     */
    @Override
    public void refreshPlayersList(final List<PlayerDTO> playersList) {
        gui.refreshPlayersList(playersList);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IClient#requestSent(com.esir.sr.sweetsnake.dto.GameRequestDTO)
     */
    @Override
    public void requestSent(final GameRequestDTO requestDto) {
        requests.add(requestDto);
        gui.requestSent(requestDto);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IClient#requestReceived(com.esir.sr.sweetsnake.dto.GameRequestDTO)
     */
    @Override
    public void requestReceived(final GameRequestDTO requestDTO) {
        final int answer = gui.requestReceived(requestDTO);
        if (answer == 0) {
            try {
                server.acceptRequest(callback, requestDTO);
            } catch (GameRequestNotFoundException | GameSessionNotFoundException | MaximumNumberOfPlayersException e) {
                gui.displayErrorMessage(e.getMessage());
            }
        } else {
            try {
                server.denyRequest(callback, requestDTO);
            } catch (final GameRequestNotFoundException e) {
                gui.displayErrorMessage(e.getMessage());
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IClient#requestDenied(boolean, com.esir.sr.sweetsnake.dto.GameRequestDTO)
     */
    @Override
    public void requestDenied(final boolean allDenied, final GameRequestDTO requestDTO) {
        gui.requestRefused(allDenied, requestDTO);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IClient#sessionJoined(int, com.esir.sr.sweetsnake.dto.GameSessionDTO)
     */
    @Override
    public void sessionJoined(final int playerNb, final GameSessionDTO sessionDto) {
        session = sessionDto;
        gui.sessionJoined(playerNb, session.getPlayersDto());
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IClient#sessionStarted(int, com.esir.sr.sweetsnake.dto.GameSessionDTO)
     */
    @Override
    public void sessionStarted(final int playerNb, final GameSessionDTO _sessionDto) {
        session = _sessionDto;
        final Map<Integer, String> playersSnakes = new LinkedHashMap<Integer, String>();
        for (final PlayerDTO player : session.getPlayersDto()) {
            playersSnakes.put(player.getNumber(), player.getSnakeId());
        }
        gui.sessionStarted(playerNb, playersSnakes, session.getGameBoardDto());
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IClient#sessionLeft(com.esir.sr.sweetsnake.dto.GameSessionDTO,
     * com.esir.sr.sweetsnake.dto.PlayerDTO, finished)
     */
    @Override
    public void sessionLeft(final GameSessionDTO sessionDto, final PlayerDTO leaver, final boolean finished) {
        log.debug("Game left by {}", leaver);
        if (finished) {
            session = null;
        }
        gui.sessionLeft(sessionDto.getPlayersDto(), leaver, finished);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IClient#sessionFinished(com.esir.sr.sweetsnake.dto.GameSessionDTO)
     */
    @Override
    public void sessionFinished(final GameSessionDTO sessionDto) {
        gui.sessionFinished(sessionDto.getPlayersDto());
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IClient#refreshSession(com.esir.sr.sweetsnake.dto.GameSessionDTO)
     */
    @Override
    public void refreshSession(final GameSessionDTO sessionDto) {
        gui.refreshGameboard(sessionDto.getGameBoardDto());
        gui.refreshScores(sessionDto.getPlayersDto());
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC CLIENT EXPOSED METHODS
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

}
