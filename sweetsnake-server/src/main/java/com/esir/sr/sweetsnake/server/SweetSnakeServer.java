package com.esir.sr.sweetsnake.server;

import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.esir.sr.sweetsnake.api.ISweetSnakeClientCallback;
import com.esir.sr.sweetsnake.api.ISweetSnakeGameRequest;
import com.esir.sr.sweetsnake.api.ISweetSnakeGameRequestsRegistry;
import com.esir.sr.sweetsnake.api.ISweetSnakeGameSession;
import com.esir.sr.sweetsnake.api.ISweetSnakeGameSessionsRegistry;
import com.esir.sr.sweetsnake.api.ISweetSnakePlayer;
import com.esir.sr.sweetsnake.api.ISweetSnakePlayersRegistry;
import com.esir.sr.sweetsnake.api.ISweetSnakeServer;
import com.esir.sr.sweetsnake.dto.SweetSnakeGameRequestDTO;
import com.esir.sr.sweetsnake.dto.SweetSnakeGameSessionDTO;
import com.esir.sr.sweetsnake.dto.SweetSnakePlayerDTO;
import com.esir.sr.sweetsnake.enumeration.SweetSnakeDirection;
import com.esir.sr.sweetsnake.enumeration.SweetSnakePlayerStatus;
import com.esir.sr.sweetsnake.exception.GameRequestNotFoundException;
import com.esir.sr.sweetsnake.exception.GameSessionNotFoundException;
import com.esir.sr.sweetsnake.exception.PlayerNotAvailableException;
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

    private static final Logger             log = LoggerFactory.getLogger(SweetSnakeServer.class);

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    @Autowired
    private ISweetSnakePlayersRegistry      players;

    @Autowired
    private ISweetSnakeGameRequestsRegistry gameRequests;

    @Autowired
    private ISweetSnakeGameSessionsRegistry gameSessions;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR & INIT
     **********************************************************************************************/

    /**
     * 
     */
    protected SweetSnakeServer() {
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
    private String retrieveClientName(final ISweetSnakeClientCallback client) {
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
    public void connect(final ISweetSnakeClientCallback client) throws UnableToConnectException {
        final String clientName = retrieveClientName(client);
        if (clientName == null) {
            throw new UnableToConnectException("username cannot be null");
        }
        if (players.contains(clientName)) {
            throw new UnableToConnectException("username " + clientName + " already taken");
        }
        final ISweetSnakePlayer player = new SweetSnakePlayer(client);
        player.setStatus(SweetSnakePlayerStatus.AVAILABLE);
        players.add(player);
        log.info("New client with username {} has connected", clientName);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeServer#disconnect(com.esir.sr.sweetsnake.api. ISweetSnakeClientCallback)
     */
    @Override
    public void disconnect(final ISweetSnakeClientCallback client) throws PlayerNotFoundException {
        final String clientName = retrieveClientName(client);
        final ISweetSnakePlayer player = players.get(clientName);
        for (final String sentRequestId : player.getSentRequestsIds()) {
            try {
                gameRequests.remove(sentRequestId);
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
    public SweetSnakeGameRequestDTO requestGame(final ISweetSnakeClientCallback client, final SweetSnakePlayerDTO otherPlayer) throws PlayerNotFoundException, PlayerNotAvailableException {
        final ISweetSnakePlayer player2 = players.get(otherPlayer.getName());

        if (player2.getStatus() != SweetSnakePlayerStatus.AVAILABLE) {
            throw new PlayerNotAvailableException("player is not available");
        }

        final ISweetSnakePlayer player1 = players.get(retrieveClientName(client));
        final ISweetSnakeGameRequest request = new SweetSnakeGameRequest(player1, player2);
        gameRequests.add(request);
        player1.addSentRequestId(request.getId());
        player2.addReceivedRequestId(request.getId());

        final SweetSnakeGameRequestDTO requestDTO = SweetSnakeFactory.convertGameSessionRequest(request);

        try {
            player2.getClientCallback().requestGame(requestDTO);
        } catch (final RemoteException e) {
            log.error(e.getMessage(), e);
        }

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
    public SweetSnakeGameSessionDTO acceptGame(final ISweetSnakeClientCallback client, final SweetSnakeGameRequestDTO requestDTO) throws PlayerNotFoundException, GameRequestNotFoundException {
        final ISweetSnakeGameRequest request = gameRequests.get(requestDTO.getId());

        if (!request.getRequestedPlayer().getName().equals(retrieveClientName(client))) {
            throw new GameRequestNotFoundException("no matching request");
        }

        final ISweetSnakePlayer player1 = request.getRequestingPlayer(), player2 = request.getRequestedPlayer();
        final ISweetSnakeGameSession gameSession = new SweetSnakeGameSession(player1, player2);

        gameSessions.add(gameSession);
        gameRequests.get(requestDTO.getId()).getRequestedPlayer().removeReceivedRequestId(requestDTO.getId());
        gameRequests.get(requestDTO.getId()).getRequestingPlayer().removeSentRequestId(requestDTO.getId());
        gameRequests.remove(requestDTO.getId());

        gameSession.startGame();

        return SweetSnakeFactory.convertGameSession(gameSession);
    }

    @Override
    public void refuseGame(final ISweetSnakeClientCallback client, final SweetSnakeGameRequestDTO requestDTO) throws GameRequestNotFoundException {
        final ISweetSnakeGameRequest request = gameRequests.get(requestDTO.getId());

        if (!request.getRequestedPlayer().getName().equals(retrieveClientName(client))) {
            throw new GameRequestNotFoundException("no matching request");
        }

        final ISweetSnakePlayer player1 = gameRequests.get(requestDTO.getId()).getRequestingPlayer(), player2 = gameRequests.get(requestDTO.getId()).getRequestedPlayer();
        player2.removeReceivedRequestId(requestDTO.getId());
        player1.removeSentRequestId(requestDTO.getId());
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
    public void leaveGame(final ISweetSnakeClientCallback client) {
        // TODO
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeServer#cancelGameRequest(com.esir.sr.sweetsnake .api.ISweetSnakeClientCallback)
     */
    @Override
    public void cancelGameRequest(final ISweetSnakeClientCallback client, final SweetSnakeGameRequestDTO requestDTO) throws PlayerNotFoundException, GameRequestNotFoundException {
        final String clientName = retrieveClientName(client);
        final ISweetSnakePlayer player = players.get(clientName);
        final ISweetSnakeGameRequest request = gameRequests.get(requestDTO.getId());
        request.cancel();
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
    public void requestMove(final ISweetSnakeClientCallback client, final SweetSnakeGameSessionDTO sessionDTO, final SweetSnakeDirection direction) throws PlayerNotFoundException, GameSessionNotFoundException {
        final ISweetSnakeGameSession session = gameSessions.get(sessionDTO.getId());
        final ISweetSnakePlayer player = players.get(retrieveClientName(client));

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
    public List<SweetSnakePlayerDTO> getPlayersList(final ISweetSnakeClientCallback client) {
        final String clientName = retrieveClientName(client);
        final List<SweetSnakePlayerDTO> playersList = new LinkedList<SweetSnakePlayerDTO>();
        for (final String player : players.getPlayersName()) {
            if (!clientName.equals(player)) {
                try {
                    final SweetSnakePlayerDTO playerDTO = SweetSnakeFactory.convertPlayer(players.get(player));
                    playersList.add(playerDTO);
                } catch (final PlayerNotFoundException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
        return playersList;
    }

}
