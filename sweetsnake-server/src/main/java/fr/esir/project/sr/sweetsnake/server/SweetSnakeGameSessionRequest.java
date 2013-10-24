package fr.esir.project.sr.sweetsnake.server;

import org.slf4j.LoggerFactory;

import fr.esir.project.sr.sweetsnake.commons.api.ISweetSnakeGameSessionRequest;

public class SweetSnakeGameSessionRequest implements ISweetSnakeGameSessionRequest
{
    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    private static final org.slf4j.Logger log = LoggerFactory.getLogger(SweetSnakeGameSessionRequest.class);

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    private final String                  requestingPlayer, requestedPlayer;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    public SweetSnakeGameSessionRequest(final String _requestingPlayer, final String _requestedPlayer) {
        log.info("Initializing new game session request between {} and {}", _requestingPlayer, _requestedPlayer);
        requestingPlayer = _requestingPlayer;
        requestedPlayer = _requestedPlayer;
    }

    /**********************************************************************************************
     * [BLOCK] PRIVATE METHODS
     **********************************************************************************************/



    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/



    /**********************************************************************************************
     * [BLOCK] GETTERS
     **********************************************************************************************/

    @Override
    public String getRequestingPlayerName() {
        return requestingPlayer;
    }

    @Override
    public String getRequestedPlayerName() {
        return requestedPlayer;
    }

    /**********************************************************************************************
     * [BLOCK] SETTERS
     **********************************************************************************************/
}
