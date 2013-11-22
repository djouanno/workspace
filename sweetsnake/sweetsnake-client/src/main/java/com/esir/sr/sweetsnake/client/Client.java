package com.esir.sr.sweetsnake.client;

import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.esir.sr.sweetsnake.api.IClientCallback;
import com.esir.sr.sweetsnake.api.IClientForGui;
import com.esir.sr.sweetsnake.api.IClientForServer;
import com.esir.sr.sweetsnake.api.IGuiForClient;
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
public class Client implements IClientForServer, IClientForGui
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
    private IGuiForClient        gui;

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
        log.info("Initialiazing the Client");
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
        log.info("Destroying the Client");
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
     * @see com.esir.sr.sweetsnake.api.IClientForGui#reachServer()
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
     * @see com.esir.sr.sweetsnake.api.IClientForGui#connect(java.lang.String)
     */
    @Override
    public void connect(final String _username) throws UnableToConnectException {
        if (_username == null || _username.isEmpty()) {
            throw new UnableToConnectException("invalid username");
        }
        log.debug("Connecting with username {}", _username);
        username = new String(_username).trim();
        server.connect(callback);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IClientForGui#disconnect()
     */
    @Override
    public void disconnect() {
        log.debug("Disconnecting from username {}", username);
        server.disconnect(callback);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IClientForGui#sendRequest(com.esir.sr.sweetsnake.dto.PlayerDTO)
     */
    @Override
    public void sendRequest(final PlayerDTO playerDto) {
        try {
            server.sendRequest(callback, playerDto);
        } catch (PlayerNotFoundException | PlayerNotAvailableException | GameSessionNotFoundException e) {
            gui.displayErrorMessage(e.getMessage());
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IClientForGui#createSession()
     */
    @Override
    public void createSession() {
        try {
            server.createSession(callback);
        } catch (final UnauthorizedActionException e) {
            gui.displayErrorMessage(e.getMessage());
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IClientForGui#joinSession(com.esir.sr.sweetsnake.dto.GameSessionDTO)
     */
    @Override
    public void joinSession(final GameSessionDTO sessionDto) {
        try {
            server.joinSession(callback, sessionDto);
        } catch (GameSessionNotFoundException | MaximumNumberOfPlayersException e) {
            gui.displayErrorMessage(e.getMessage());
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IClientForGui#readyToPlay()
     */
    @Override
    public void readyToPlay() {
        log.debug("session : {}", session);
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
     * @see com.esir.sr.sweetsnake.api.IClientForGui#startSession()
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
     * @see com.esir.sr.sweetsnake.api.IClientForGui#leaveSession()
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
     * @see com.esir.sr.sweetsnake.api.IClientForGui#moveSnake(com.esir.sr.sweetsnake.enumeration.MoveDirection)
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
     * @see com.esir.sr.sweetsnake.api.IClientForServer#connected()
     */
    @Override
    public void connected() {
        isConnected = true;
        gui.connectedToServer();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IClientForServer#disconnected()
     */
    @Override
    public void disconnected() {
        isConnected = false;
        gui.disconnectedFromServer();
        server = null;
        reachServer();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IClientForServer#refreshPlayersList(java.util.List)
     */
    @Override
    public void refreshPlayersList(final List<PlayerDTO> playersList) {
        gui.refreshPlayersList(playersList);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IClientForServer#refreshSessionsList(java.util.List)
     */
    @Override
    public void refreshSessionsList(final List<GameSessionDTO> sessionsList) {
        gui.refreshSessionsList(sessionsList);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IClientForServer#requestSent(com.esir.sr.sweetsnake.dto.GameRequestDTO)
     */
    @Override
    public void requestSent(final GameRequestDTO requestDto) {
        requests.add(requestDto);
        gui.requestSent(requestDto);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IClientForServer#requestReceived(com.esir.sr.sweetsnake.dto.GameRequestDTO)
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
     * @see com.esir.sr.sweetsnake.api.IClientForServer#requestDenied(com.esir.sr.sweetsnake.dto.GameRequestDTO)
     */
    @Override
    public void requestDenied(final GameRequestDTO requestDTO) {
        gui.requestDenied(requestDTO);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IClientForServer#sessionJoined(int, com.esir.sr.sweetsnake.dto.GameSessionDTO)
     */
    @Override
    public void sessionJoined(final int playerNb, final GameSessionDTO sessionDto) {
        session = sessionDto;
        gui.sessionJoined(session, playerNb);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IClientForServer#sessionStarted(int, com.esir.sr.sweetsnake.dto.GameSessionDTO)
     */
    @Override
    public void sessionStarted(final int playerNb, final GameSessionDTO sessionDto) {
        session = sessionDto;
        gui.sessionStarted(session, playerNb);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IClientForServer#sessionLeft(com.esir.sr.sweetsnake.dto.GameSessionDTO,
     * com.esir.sr.sweetsnake.dto.PlayerDTO, boolean, boolean)
     */
    @Override
    public void sessionLeft(final GameSessionDTO sessionDto, final PlayerDTO leaver, final boolean stopped, final boolean finished) {
        session = sessionDto;
        gui.sessionLeft(session, leaver, stopped, finished);
        if (finished) {
            session = null;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IClientForServer#sessionFinished(com.esir.sr.sweetsnake.dto.GameSessionDTO)
     */
    @Override
    public void sessionFinished(final GameSessionDTO sessionDto) {
        session = sessionDto;
        gui.sessionFinished(session);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IClientForServer#refreshSession(com.esir.sr.sweetsnake.dto.GameSessionDTO)
     */
    @Override
    public void refreshSession(final GameSessionDTO sessionDto) {
        session = sessionDto;
        gui.refreshGameboard(session.getGameBoardDto());
        gui.refreshScores(session.getPlayersDto());
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC CLIENT EXPOSED METHODS
     **********************************************************************************************/

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IClient#getUsername()
     */
    @Override
    public String getUsername() {
        return username;
    }

}
