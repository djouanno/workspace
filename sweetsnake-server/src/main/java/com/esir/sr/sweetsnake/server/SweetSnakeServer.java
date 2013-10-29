package com.esir.sr.sweetsnake.server;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.esir.sr.sweetsnake.api.ISweetSnakeClientCallback;
import com.esir.sr.sweetsnake.api.ISweetSnakeGameRequest;
import com.esir.sr.sweetsnake.api.ISweetSnakeGameSession;
import com.esir.sr.sweetsnake.api.ISweetSnakePlayer;
import com.esir.sr.sweetsnake.api.ISweetSnakeServer;
import com.esir.sr.sweetsnake.dto.SweetSnakeGameRequestDTO;
import com.esir.sr.sweetsnake.dto.SweetSnakeGameSessionDTO;
import com.esir.sr.sweetsnake.dto.SweetSnakePlayerDTO;
import com.esir.sr.sweetsnake.enumeration.Direction;
import com.esir.sr.sweetsnake.enumeration.Status;
import com.esir.sr.sweetsnake.exception.BadGameSessionException;
import com.esir.sr.sweetsnake.exception.PlayerNotFoundException;
import com.esir.sr.sweetsnake.exception.UnableToConnectException;
import com.esir.sr.sweetsnake.factory.SweetSnakeFactory;
import com.esir.sr.sweetsnake.session.SweetSnakeGameRequest;
import com.esir.sr.sweetsnake.session.SweetSnakeGameSession;
import com.esir.sr.sweetsnake.session.SweetSnakePlayer;

@Component
public class SweetSnakeServer implements ISweetSnakeServer
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    private static final Logger                                   log = LoggerFactory.getLogger(SweetSnakeServer.class);

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    private Map<String, ISweetSnakePlayer>                        players;
    private Map<ISweetSnakePlayer, ISweetSnakeGameRequest>        gameRequests;
    private Map<SweetSnakeGameSessionDTO, ISweetSnakeGameSession> gameSessions;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * 
     */
    protected SweetSnakeServer() {
        super();
    }

    /**********************************************************************************************
     * [BLOCK] PRIVATE METHODS
     **********************************************************************************************/

    /**
     * 
     * @param request
     * @return
     */
    private SweetSnakeGameSessionDTO startGameSession(final ISweetSnakeGameRequest request) {
        final ISweetSnakePlayer player1 = request.getRequestingPlayer(), player2 = request.getRequestedPlayer();

        final ISweetSnakeGameSession gameSession = new SweetSnakeGameSession(player1, player2);
        final SweetSnakeGameSessionDTO gameSessionDTO = SweetSnakeFactory.convertGameSession(gameSession);

        gameSessions.put(gameSessionDTO, gameSession);
        gameRequests.remove(player1);
        gameSession.startGame();

        return gameSessionDTO;
    }

    /**
     * 
     * @param client
     * @return
     */
    private String retrieveClientName(final ISweetSnakeClientCallback client) {
        try {
            return client.getName();
        } catch (final RemoteException e) {
            log.error("unable to retrieve client name : {}", e.getMessage(), e);
        }
        return null;
    }

    /**
     * 
     * @param name
     * @return
     */
    private ISweetSnakePlayer retrievePlayer(final String name) {
        if (players.containsKey(name)) {
            return players.get(name);
        }
        return null;
    }

    /**
     * 
     * @param player
     * @return
     */
    private ISweetSnakeGameRequest retrieveRequest(final ISweetSnakePlayer player) {
        if (gameRequests.containsKey(player)) {
            return gameRequests.get(player);
        }
        return null;
    }

    /**
     * 
     * @param sessionDTO
     * @return
     */
    private ISweetSnakeGameSession retrieveSession(final SweetSnakeGameSessionDTO sessionDTO) {
        if (gameSessions.containsKey(sessionDTO)) {
            return gameSessions.get(sessionDTO);
        }
        return null;
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    /**
     * 
     */
    @PostConstruct
    public void init() {
        log.info("Initialization of the SweetSnakeServer");
        players = new HashMap<String, ISweetSnakePlayer>();
        gameSessions = new HashMap<SweetSnakeGameSessionDTO, ISweetSnakeGameSession>();
        gameRequests = new HashMap<ISweetSnakePlayer, ISweetSnakeGameRequest>();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeServer#connect(com.esir.sr.sweetsnake.api.
     * ISweetSnakeClientCallback)
     */
    @Override
    public void connect(final ISweetSnakeClientCallback client) throws UnableToConnectException {
        final String clientName = retrieveClientName(client);
        if (clientName == null) {
            throw new UnableToConnectException("username cannot be null");
        }
        if (players.containsKey(clientName)) {
            throw new UnableToConnectException("username " + clientName + " already taken");
        }
        final ISweetSnakePlayer player = new SweetSnakePlayer(client);
        player.setStatus(Status.AVAILABLE);
        players.put(clientName, player);
        log.info("New client with username {} has connected", clientName);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeServer#disconnect(com.esir.sr.sweetsnake.api.
     * ISweetSnakeClientCallback)
     */
    @Override
    public void disconnect(final ISweetSnakeClientCallback client) {
        final String clientName = retrieveClientName(client);
        if (players.containsKey(clientName)) {
            players.remove(clientName);
            log.info("Client with username {} has disconnected", clientName);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.esir.sr.sweetsnake.api.ISweetSnakeServer#requestGameSession(com.esir.sr.sweetsnake.api
     * .ISweetSnakeClientCallback, com.esir.sr.sweetsnake.dto.SweetSnakePlayerDTO)
     */
    @Override
    public SweetSnakeGameRequestDTO requestGameSession(final ISweetSnakeClientCallback client, final SweetSnakePlayerDTO otherPlayer) throws PlayerNotFoundException, BadGameSessionException {
        if (!players.containsKey(otherPlayer.getName())) {
            throw new PlayerNotFoundException("unable to find the specified player");
        }

        final ISweetSnakePlayer player2 = retrievePlayer(otherPlayer.getName());
        if (player2.getStatus() != Status.AVAILABLE) {
            throw new BadGameSessionException("player is not available");
        }

        final ISweetSnakePlayer player1 = retrievePlayer(retrieveClientName(client));
        if (gameRequests.containsKey(player1)) {
            throw new BadGameSessionException("a request is already pending");
        }

        final ISweetSnakeGameRequest request = new SweetSnakeGameRequest(player1, player2);
        gameRequests.put(player1, request);

        final SweetSnakeGameRequestDTO requestDTO = SweetSnakeFactory.convertGameSessionRequest(request);
        player2.getClientCallback().requestGame(requestDTO);
        log.info("Game session request between {} and {} is pending", player1, player2);

        return requestDTO;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.esir.sr.sweetsnake.api.ISweetSnakeServer#acceptGameSession(com.esir.sr.sweetsnake.api
     * .ISweetSnakeClientCallback, com.esir.sr.sweetsnake.dto.SweetSnakeGameRequestDTO)
     */
    @Override
    public SweetSnakeGameSessionDTO acceptGameSession(final ISweetSnakeClientCallback client, final SweetSnakeGameRequestDTO requestDTO) throws PlayerNotFoundException, BadGameSessionException {
        final ISweetSnakePlayer requestingPlayer = retrievePlayer(requestDTO.getRequestingPlayerName());
        if (requestingPlayer == null) {
            throw new PlayerNotFoundException("player not found");
        }

        final ISweetSnakeGameRequest request = retrieveRequest(requestingPlayer);
        if (request == null) {
            throw new BadGameSessionException("request not available");
        }
        if (!request.getRequestedPlayer().getName().equals(retrieveClientName(client))) {
            throw new BadGameSessionException("no matching request");
        }

        return startGameSession(request);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.esir.sr.sweetsnake.api.ISweetSnakeServer#leaveGameSession(com.esir.sr.sweetsnake.api.
     * ISweetSnakeClientCallback)
     */
    @Override
    public void leaveGameSession(final ISweetSnakeClientCallback client) {
        // TODO
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeServer#cancelGameRequest(com.esir.sr.sweetsnake
     * .api.ISweetSnakeClientCallback)
     */
    @Override
    public void cancelGameRequest(final ISweetSnakeClientCallback client) {
        final String clientName = retrieveClientName(client);
        final ISweetSnakePlayer player = retrievePlayer(clientName);
        final ISweetSnakeGameRequest request = retrieveRequest(player);
        if (request != null) {
            request.cancel();
            gameRequests.remove(request);
        }
        log.info("Game session request canceled by player {}", player);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeServer#requestMove(com.esir.sr.sweetsnake.api.
     * ISweetSnakeClientCallback, com.esir.sr.sweetsnake.dto.SweetSnakeGameSessionDTO,
     * com.esir.sr.sweetsnake.enumeration.Direction)
     */
    @Override
    public void requestMove(final ISweetSnakeClientCallback client, final SweetSnakeGameSessionDTO sessionDTO, final Direction direction) throws BadGameSessionException {
        final ISweetSnakeGameSession session = retrieveSession(sessionDTO);
        if (session == null) {
            throw new BadGameSessionException("unavailable session");
        }
        final ISweetSnakePlayer player = retrievePlayer(retrieveClientName(client));
        if (player != session.getPlayer1() && player != session.getPlayer2()) {
            throw new BadGameSessionException("unauthorized session");
        }
        if (session.isGameStarted()) {
            session.movePlayer(player, direction);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeServer#getPlayersList(com.esir.sr.sweetsnake.api.
     * ISweetSnakeClientCallback)
     */
    @Override
    public List<SweetSnakePlayerDTO> getPlayersList(final ISweetSnakeClientCallback client) {
        final String clientName = retrieveClientName(client);
        final List<SweetSnakePlayerDTO> playersList = new ArrayList<SweetSnakePlayerDTO>();
        for (final String player : players.keySet()) {
            if (!clientName.equals(player)) {
                final SweetSnakePlayerDTO playerDTO = SweetSnakeFactory.convertPlayer(players.get(player));
                playersList.add(playerDTO);
            }
        }
        return playersList;
    }

}
