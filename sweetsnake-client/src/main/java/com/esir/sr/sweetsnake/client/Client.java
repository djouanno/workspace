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
import com.esir.sr.sweetsnake.enumeration.PlayerStatus;
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

    /** The player status */
    private PlayerStatus         status;

    /** The sent requests DTO */
    private List<GameRequestDTO> requests;

    /** The session DTO (only one at a time) */
    private GameSessionDTO       session;

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

    @Override
    public void connect(final String _username) throws UnableToConnectException {
        if (_username == null || _username.isEmpty()) {
            throw new UnableToConnectException("invalid username");
        }
        log.debug("Connecting with username {}", _username);
        username = new String(_username);
        server.connect(callback);
        status = PlayerStatus.AVAILABLE;
        gui.connectedToServer();
    }

    @Override
    public void disconnect() {
        log.debug("Disconnecting from username {}", username);
        server.disconnect(callback);
        status = PlayerStatus.DISCONNECTED;
    }

    @Override
    public void refreshPlayersList(final List<PlayerDTO> playersList) {
        gui.refreshPlayersList(playersList);
    }

    @Override
    public void sendRequest(final PlayerDTO playerDto) {
        try {
            server.sendRequest(callback, playerDto);
        } catch (PlayerNotFoundException | PlayerNotAvailableException e) {
            gui.displayErrorMessage(e.getMessage());
        }
    }

    @Override
    public void requestSent(final GameRequestDTO requestDto) {
        requests.add(requestDto);
        status = PlayerStatus.INVITING;
        gui.requestSent(requestDto);
    }

    @Override
    public void requestReceived(final GameRequestDTO requestDTO) {
        status = PlayerStatus.INVITED;
        final int answer = gui.gameRequested(requestDTO);
        if (answer == 0) {
            try {
                server.acceptRequest(callback, requestDTO);
                gui.gameJoined(session.getPlayersDto());
            } catch (GameRequestNotFoundException | GameSessionNotFoundException | MaximumNumberOfPlayersException e) {
                gui.displayErrorMessage(e.getMessage());
            }
        } else {
            try {
                server.denyRequest(callback, requestDTO);
            } catch (final GameRequestNotFoundException e) {
                gui.displayErrorMessage(e.getMessage());
            }
            status = PlayerStatus.AVAILABLE;
        }
    }

    @Override
    public void requestDenied(final boolean allDenied, final GameRequestDTO requestDTO) {
        status = PlayerStatus.AVAILABLE;
        gui.requestRefused(allDenied, requestDTO);
    }

    @Override
    public void sessionJoined(final GameSessionDTO sessionDto) {
        session = sessionDto;
        gui.gameJoined(session.getPlayersDto());
    }

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

    @Override
    public void sessionStarted(final GameSessionDTO _sessionDto) {
        session = _sessionDto;
        final Map<Integer, String> playersSnakes = new LinkedHashMap<Integer, String>();
        int myPlayerNb = 1;
        for (final PlayerDTO player : session.getPlayersDto()) {
            playersSnakes.put(player.getNumber(), player.getSnakeId());
            if (player.getName().equals(username)) {
                myPlayerNb = player.getNumber();
            }
        }
        gui.gameStarted(myPlayerNb, playersSnakes, session.getGameBoardDto());
        status = PlayerStatus.PLAYING;
    }

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

    @Override
    public void sessionLeft(final GameSessionDTO sessionDto, final PlayerDTO leaver) {
        log.debug("Game left by {}", leaver);
        final boolean finished = sessionDto.getPlayersDto().size() <= 1 || leaver.getName().equals(username);
        if (finished) {
            session = null;
            status = PlayerStatus.AVAILABLE;
        }
        gui.gameLeft(leaver, finished);
    }

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

    @Override
    public void refreshSession(final GameSessionDTO sessionDto) {
        gui.refreshGameboard(sessionDto.getGameBoardDto());
        final Map<Integer, Integer> playersScores = new LinkedHashMap<Integer, Integer>();
        for (final PlayerDTO player : sessionDto.getPlayersDto()) {
            playersScores.put(player.getNumber(), player.getScore());
        }
        gui.refreshScores(playersScores);
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

}
