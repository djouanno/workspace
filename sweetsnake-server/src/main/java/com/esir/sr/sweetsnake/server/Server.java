package com.esir.sr.sweetsnake.server;

import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.esir.sr.sweetsnake.api.IClientCallback;
import com.esir.sr.sweetsnake.api.IServer;
import com.esir.sr.sweetsnake.dto.GameRequestDTO;
import com.esir.sr.sweetsnake.dto.GameSessionDTO;
import com.esir.sr.sweetsnake.dto.PlayerDTO;
import com.esir.sr.sweetsnake.enumeration.MoveDirection;
import com.esir.sr.sweetsnake.enumeration.PlayerStatus;
import com.esir.sr.sweetsnake.exception.GameRequestNotFoundException;
import com.esir.sr.sweetsnake.exception.GameSessionNotFoundException;
import com.esir.sr.sweetsnake.exception.PlayerNotAvailableException;
import com.esir.sr.sweetsnake.exception.PlayerNotFoundException;
import com.esir.sr.sweetsnake.exception.UnableToConnectException;
import com.esir.sr.sweetsnake.factory.DtoConverterFactory;
import com.esir.sr.sweetsnake.registry.GameRequestsRegistry;
import com.esir.sr.sweetsnake.registry.GameSessionsRegistry;
import com.esir.sr.sweetsnake.registry.PlayersRegistry;
import com.esir.sr.sweetsnake.session.GameRequest;
import com.esir.sr.sweetsnake.session.GameSession;
import com.esir.sr.sweetsnake.session.Player;

/**
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
@Component
public class Server implements IServer
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    /** The logger */
    private static final Logger  log = LoggerFactory.getLogger(Server.class);

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    /** The players registry */
    @Autowired
    private PlayersRegistry      players;

    /** The requests registry */
    @Autowired
    private GameRequestsRegistry gameRequests;

    /** The sessions registry */
    @Autowired
    private GameSessionsRegistry gameSessions;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR & INIT
     **********************************************************************************************/

    /**
     * 
     */
    protected Server() {
        super();
    }

    /**
     * 
     */
    @PostConstruct
    protected void init() {
        log.info("Initialization of the SweetSnakeServer");
    }

    /**********************************************************************************************
     * [BLOCK] PRIVATE METHODS
     **********************************************************************************************/

    /**
     * 
     * @param client
     * @return
     */
    private String retrieveClientName(final IClientCallback client) {
        try {
            return client.getUsername();
        } catch (final RemoteException e) {
            log.error(e.getMessage(), e);
        }
        return new String();
    }


    /**
     * 
     * @param request
     */
    private void addRequest(final GameRequest request) {
        final Player requestingPlayer = request.getRequestingPlayer(), requestedPlayer = request.getRequestedPlayer();
        requestingPlayer.setSentRequestId(request.getId());
        requestedPlayer.setReceivedRequestId(request.getId());
        gameRequests.add(request);
        requestingPlayer.setStatus(PlayerStatus.INVITING);
        requestedPlayer.setStatus(PlayerStatus.INVITED);
    }

    /**
     * 
     * @param request
     * @throws GameRequestNotFoundException
     */
    private void removeRequest(final String requestId) {
        try {
            final GameRequest request = gameRequests.get(requestId);
            final Player requestingPlayer = request.getRequestingPlayer(), requestedPlayer = request.getRequestedPlayer();
            gameRequests.remove(request.getId());
            requestingPlayer.setSentRequestId(null);
            requestedPlayer.setReceivedRequestId(null);
            requestingPlayer.setStatus(PlayerStatus.AVAILABLE);
            requestedPlayer.setStatus(PlayerStatus.AVAILABLE);
        } catch (final GameRequestNotFoundException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 
     * @param session
     */
    private void addSession(final GameSession session) {
        final Player player1 = session.getPlayer1(), player2 = session.getPlayer2();
        player1.setGameSessionId(session.getId());
        player2.setGameSessionId(session.getId());
        gameSessions.add(session);
        player1.setStatus(PlayerStatus.PLAYING);
        player2.setStatus(PlayerStatus.PLAYING);
    }

    /**
     * 
     * @param session
     */
    private void removeSession(final String sessionId) {
        try {
            final GameSession session = gameSessions.get(sessionId);
            final Player player1 = session.getPlayer1(), player2 = session.getPlayer2();
            gameSessions.remove(session.getId());
            player1.setGameSessionId(null);
            player2.setGameSessionId(null);
            player1.setStatus(PlayerStatus.AVAILABLE);
            player2.setStatus(PlayerStatus.AVAILABLE);
        } catch (final GameSessionNotFoundException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IServer#connect(com.esir.sr.sweetsnake.api.IClientCallback)
     */
    @Override
    public void connect(final IClientCallback client) throws UnableToConnectException {
        final String clientName = retrieveClientName(client);
        if (clientName == null) {
            throw new UnableToConnectException("username cannot be null");
        }
        if (players.contains(clientName)) {
            throw new UnableToConnectException("username " + clientName + " already taken");
        }
        final Player player = new Player(client);
        player.setStatus(PlayerStatus.AVAILABLE);
        players.add(player);
        log.info("New client with username {} has connected", clientName);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IServer#disconnect(com.esir.sr.sweetsnake.api.IClientCallback)
     */
    @Override
    public void disconnect(final IClientCallback client) throws PlayerNotFoundException {
        final String clientName = retrieveClientName(client);
        final Player player = players.get(clientName);
        if (player.getSentRequestId() != null) {
            removeRequest(player.getSentRequestId());
        }
        if (player.getReceivedRequestId() != null) {
            removeRequest(player.getReceivedRequestId());
        }
        if (player.getGameSessionId() != null) {
            try {
                log.debug("Removing current session {} for player {}", gameSessions.get(player.getGameSessionId()), player);
                // TODO does not work when client brutally closes the window !!!!!
                leaveGame(client, DtoConverterFactory.convertGameSession(gameSessions.get(player.getGameSessionId())));
            } catch (final GameSessionNotFoundException e) {
                log.error(e.getMessage(), e);
            }
        }
        players.remove(clientName);
        log.info("Client with username {} has disconnected", clientName);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IServer#requestGame(com.esir.sr.sweetsnake.api.IClientCallback,
     * com.esir.sr.sweetsnake.dto.PlayerDTO)
     */
    @Override
    public GameRequestDTO requestGame(final IClientCallback client, final PlayerDTO otherPlayer) throws PlayerNotFoundException, PlayerNotAvailableException {
        final Player player2 = players.get(otherPlayer.getName());

        if (player2.getStatus() != PlayerStatus.AVAILABLE) {
            throw new PlayerNotAvailableException("player is not available");
        }

        final Player player1 = players.get(retrieveClientName(client));
        final GameRequest request = new GameRequest(player1, player2);
        addRequest(request);

        final GameRequestDTO requestDTO = DtoConverterFactory.convertGameSessionRequest(request);

        // requestGame() on client side is a blocking method while the other player has not answered
        // so we have to launch it from a new thread
        final Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    player2.getClientCallback().gameRequested(requestDTO);
                } catch (final RemoteException e) {
                    log.error(e.getMessage(), e);
                }
            }
        });
        t.start();

        log.info("Game request between {} and {} is pending", player1, player2);

        return requestDTO;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IServer#acceptGame(com.esir.sr.sweetsnake.api.IClientCallback,
     * com.esir.sr.sweetsnake.dto.GameRequestDTO)
     */
    @Override
    public GameSessionDTO acceptGame(final IClientCallback client, final GameRequestDTO requestDTO) throws PlayerNotFoundException, GameRequestNotFoundException {
        final GameRequest request = gameRequests.get(requestDTO.getId());

        if (!request.getRequestedPlayer().getName().equals(retrieveClientName(client))) {
            throw new GameRequestNotFoundException("no matching request");
        }

        removeRequest(requestDTO.getId());

        final Player player1 = request.getRequestingPlayer(), player2 = request.getRequestedPlayer();
        final GameSession gameSession = new GameSession(player1, player2);
        addSession(gameSession);
        gameSession.startGame();

        return DtoConverterFactory.convertGameSession(gameSession);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IServer#refuseGame(com.esir.sr.sweetsnake.api.IClientCallback,
     * com.esir.sr.sweetsnake.dto.GameRequestDTO)
     */
    @Override
    public void refuseGame(final IClientCallback client, final GameRequestDTO requestDTO) throws GameRequestNotFoundException {
        final GameRequest request = gameRequests.get(requestDTO.getId());

        if (!request.getRequestedPlayer().getName().equals(retrieveClientName(client))) {
            throw new GameRequestNotFoundException("no matching request");
        }

        final Player player1 = gameRequests.get(requestDTO.getId()).getRequestingPlayer();
        removeRequest(requestDTO.getId());

        try {
            player1.getClientCallback().gameRefused(requestDTO);
        } catch (final RemoteException e) {
            log.error(e.getMessage(), e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IServer#leaveGame(com.esir.sr.sweetsnake.api.IClientCallback)
     */
    @Override
    public void leaveGame(final IClientCallback client, final GameSessionDTO sessionDTO) throws GameSessionNotFoundException {
        final GameSession session = gameSessions.get(sessionDTO.getId());
        try {
            log.debug("Leaving session {} from player {}", session, client.getUsername());
            session.getPlayer1().getClientCallback().gameLeaved(sessionDTO);
            session.getPlayer2().getClientCallback().gameLeaved(sessionDTO);
            session.stopGame();
            removeSession(session.getId());
        } catch (final RemoteException e) {
            log.error(e.getMessage(), e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IServer#cancelGameRequest(com.esir.sr.sweetsnake.api.IClientCallback,
     * com.esir.sr.sweetsnake.dto.GameRequestDTO)
     */
    @Override
    public void cancelGameRequest(final IClientCallback client, final GameRequestDTO requestDTO) throws PlayerNotFoundException, GameRequestNotFoundException {
        final String clientName = retrieveClientName(client);
        final Player player = players.get(clientName);
        final GameRequest request = gameRequests.get(requestDTO.getId());
        removeRequest(requestDTO.getId());
        log.info("Game request {} canceled by player {}", request, player);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IServer#requestMove(com.esir.sr.sweetsnake.api.IClientCallback,
     * com.esir.sr.sweetsnake.dto.GameSessionDTO, com.esir.sr.sweetsnake.enumeration.MoveDirection)
     */
    @Override
    public void requestMove(final IClientCallback client, final GameSessionDTO sessionDTO, final MoveDirection direction) throws PlayerNotFoundException, GameSessionNotFoundException {
        final GameSession session = gameSessions.get(sessionDTO.getId());
        final Player player = players.get(retrieveClientName(client));

        if (player != session.getPlayer1() && player != session.getPlayer2()) {
            throw new PlayerNotFoundException("unauthorized session for this player");
        }

        if (session.isGameStarted()) {
            session.movePlayer(player, direction);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IServer#getPlayersList(com.esir.sr.sweetsnake.api.IClientCallback)
     */
    @Override
    public List<PlayerDTO> getPlayersList(final IClientCallback client) {
        final String clientName = retrieveClientName(client);
        final List<PlayerDTO> playersList = new LinkedList<PlayerDTO>();
        for (final String player : players.getPlayersName()) {
            if (!clientName.equals(player)) {
                try {
                    final PlayerDTO playerDTO = DtoConverterFactory.convertPlayer(players.get(player));
                    playersList.add(playerDTO);
                } catch (final PlayerNotFoundException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
        return playersList;
    }

}
