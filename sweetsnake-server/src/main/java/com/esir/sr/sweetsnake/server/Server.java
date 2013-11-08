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
import com.esir.sr.sweetsnake.api.IGameRequest;
import com.esir.sr.sweetsnake.api.IGameRequestsRegistry;
import com.esir.sr.sweetsnake.api.IGameSession;
import com.esir.sr.sweetsnake.api.IGameSessionsRegistry;
import com.esir.sr.sweetsnake.api.IPlayer;
import com.esir.sr.sweetsnake.api.IPlayersRegistry;
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
import com.esir.sr.sweetsnake.factory.SessionsFactory;
import com.esir.sr.sweetsnake.session.GameRequest;
import com.esir.sr.sweetsnake.session.GameSession;
import com.esir.sr.sweetsnake.session.Player;

@Component
public class Server implements IServer
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    private static final Logger             log = LoggerFactory.getLogger(Server.class);

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    @Autowired
    private IPlayersRegistry      players;

    @Autowired
    private IGameRequestsRegistry gameRequests;

    @Autowired
    private IGameSessionsRegistry gameSessions;

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

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeServer#connect(com.esir.sr.sweetsnake.api. ISweetSnakeClientCallback)
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
        final IPlayer player = new Player(client);
        player.setStatus(PlayerStatus.AVAILABLE);
        players.add(player);
        log.info("New client with username {} has connected", clientName);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeServer#disconnect(com.esir.sr.sweetsnake.api. ISweetSnakeClientCallback)
     */
    @Override
    public void disconnect(final IClientCallback client) throws PlayerNotFoundException {
        final String clientName = retrieveClientName(client);
        final IPlayer player = players.get(clientName);
        for (final String sentRequestId : player.getSentRequestsIds()) {
            try {
                gameRequests.remove(sentRequestId);
            } catch (final GameRequestNotFoundException e) {
                log.error(e.getMessage(), e);
            }
        }
        for (final String receivedRequestId : player.getReceivedRequestsIds()) {
            try {
                gameRequests.remove(receivedRequestId);
            } catch (final GameRequestNotFoundException e) {
                log.error(e.getMessage(), e);
            }
        }
        // TODO remove game session
        players.remove(clientName);
        log.info("Client with username {} has disconnected", clientName);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeServer#requestGame(com.esir.sr.sweetsnake.api.ISweetSnakeClientCallback,
     * com.esir.sr.sweetsnake.dto.SweetSnakePlayerDTO)
     */
    @Override
    public GameRequestDTO requestGame(final IClientCallback client, final PlayerDTO otherPlayer) throws PlayerNotFoundException, PlayerNotAvailableException {
        final IPlayer player2 = players.get(otherPlayer.getName());

        if (player2.getStatus() != PlayerStatus.AVAILABLE) {
            throw new PlayerNotAvailableException("player is not available");
        }

        final IPlayer player1 = players.get(retrieveClientName(client));
        final IGameRequest request = new GameRequest(player1, player2);
        gameRequests.add(request);
        player1.addSentRequestId(request.getId());
        player2.addReceivedRequestId(request.getId());

        final GameRequestDTO requestDTO = SessionsFactory.convertGameSessionRequest(request);

        // requestGame() on client side is a blocking method while the other player has not answered
        // so we have to launch it from a new thread
        final Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    player2.getClientCallback().requestGame(requestDTO);
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
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeServer#acceptGame(com.esir.sr.sweetsnake.api.ISweetSnakeClientCallback,
     * com.esir.sr.sweetsnake.dto.SweetSnakeGameRequestDTO)
     */
    @Override
    public GameSessionDTO acceptGame(final IClientCallback client, final GameRequestDTO requestDTO) throws PlayerNotFoundException, GameRequestNotFoundException {
        final IGameRequest request = gameRequests.get(requestDTO.getId());

        if (!request.getRequestedPlayer().getName().equals(retrieveClientName(client))) {
            throw new GameRequestNotFoundException("no matching request");
        }

        final IPlayer player1 = request.getRequestingPlayer(), player2 = request.getRequestedPlayer();
        final IGameSession gameSession = new GameSession(player1, player2);

        gameSessions.add(gameSession);
        gameRequests.remove(requestDTO.getId());

        gameSession.startGame();

        return SessionsFactory.convertGameSession(gameSession);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeServer#refuseGame(com.esir.sr.sweetsnake.api.ISweetSnakeClientCallback,
     * com.esir.sr.sweetsnake.dto.SweetSnakeGameRequestDTO)
     */
    @Override
    public void refuseGame(final IClientCallback client, final GameRequestDTO requestDTO) throws GameRequestNotFoundException {
        final IGameRequest request = gameRequests.get(requestDTO.getId());

        if (!request.getRequestedPlayer().getName().equals(retrieveClientName(client))) {
            throw new GameRequestNotFoundException("no matching request");
        }

        final IPlayer player1 = gameRequests.get(requestDTO.getId()).getRequestingPlayer();
        gameRequests.remove(requestDTO.getId());

        try {
            player1.getClientCallback().requestRefused(requestDTO);
        } catch (final RemoteException e) {
            log.error(e.getMessage(), e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeServer#leaveGame(com.esir.sr.sweetsnake.api.ISweetSnakeClientCallback)
     */
    @Override
    public void leaveGame(final IClientCallback client) {
        // TODO
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeServer#cancelGameRequest(com.esir.sr.sweetsnake .api.ISweetSnakeClientCallback)
     */
    @Override
    public void cancelGameRequest(final IClientCallback client, final GameRequestDTO requestDTO) throws PlayerNotFoundException, GameRequestNotFoundException {
        final String clientName = retrieveClientName(client);
        final IPlayer player = players.get(clientName);
        gameRequests.remove(requestDTO.getId());
        log.info("Game session request canceled by player {}", player);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeServer#requestMove(com.esir.sr.sweetsnake.api. ISweetSnakeClientCallback,
     * com.esir.sr.sweetsnake.dto.SweetSnakeGameSessionDTO, com.esir.sr.sweetsnake.enumeration.Direction)
     */
    @Override
    public void requestMove(final IClientCallback client, final GameSessionDTO sessionDTO, final MoveDirection direction) throws PlayerNotFoundException, GameSessionNotFoundException {
        final IGameSession session = gameSessions.get(sessionDTO.getId());
        final IPlayer player = players.get(retrieveClientName(client));

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
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeServer#getPlayersList(com.esir.sr.sweetsnake.api. ISweetSnakeClientCallback)
     */
    @Override
    public List<PlayerDTO> getPlayersList(final IClientCallback client) {
        final String clientName = retrieveClientName(client);
        final List<PlayerDTO> playersList = new LinkedList<PlayerDTO>();
        for (final String player : players.getPlayersName()) {
            if (!clientName.equals(player)) {
                try {
                    final PlayerDTO playerDTO = SessionsFactory.convertPlayer(players.get(player));
                    playersList.add(playerDTO);
                } catch (final PlayerNotFoundException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
        return playersList;
    }

}
