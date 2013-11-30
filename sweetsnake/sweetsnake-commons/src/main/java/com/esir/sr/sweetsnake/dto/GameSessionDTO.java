package com.esir.sr.sweetsnake.dto;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.esir.sr.sweetsnake.api.IGameSessionCallback;
import com.esir.sr.sweetsnake.constants.GameConstants;

/**
 * This class is a Data Transfert Object representing a GameSession.
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
public class GameSessionDTO implements Serializable
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    /** The serial version UID */
    private static final long          serialVersionUID = 459664230963147646L;

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    /** The session id */
    private final String               id;

    /** The players list DTO */
    private final List<PlayerDTO>      playersDto;

    /** The game engine DTO */
    private final GameEngineDTO        gameEngineDto;

    /** The session rmi callback */
    private final IGameSessionCallback callback;

    /** Is the session started */
    private final boolean              isStarted;

    /** The leading player DTO */
    private final PlayerDTO            leader;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * Creates a new game session DTO
     * 
     * @param _id
     *            The game session id
     * @param _playersDto
     *            The list of the DTO representing all the players taking part in the game session
     * @param _gameEngineDto
     *            The DTO representing the game engine
     * @param _callback
     *            The game session callback
     * @param _isStarted
     *            Is the session started or not
     * @param _leader
     *            The DTO representing the leading player
     */
    public GameSessionDTO(final String _id, final List<PlayerDTO> _playersDto, final GameEngineDTO _gameEngineDto, final IGameSessionCallback _callback, final boolean _isStarted, final PlayerDTO _leader) {
        id = _id;
        playersDto = new LinkedList<PlayerDTO>(_playersDto);
        gameEngineDto = _gameEngineDto;
        callback = _callback;
        isStarted = _isStarted;
        leader = _leader;
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        String st = id;
        if (!playersDto.isEmpty()) {
            st = playersDto.get(0).getName();
            st += " [" + playersDto.size() + "/" + GameConstants.MAX_NUMBER_OF_PLAYERS + "]";
        }
        return st;
    }

    /**********************************************************************************************
     * [BLOCK] GETTERS
     **********************************************************************************************/

    /**
     * This method return the game session id
     * 
     * @return A string representing the game session id
     */
    public String getId() {
        return id;
    }

    /**
     * This method returns the DTO representing all the players taking part in the game session
     * 
     * @return A list containing the DTO representing all the players taking part in the game session
     */
    public List<PlayerDTO> getPlayersDto() {
        return Collections.unmodifiableList(playersDto);
    }

    /**
     * This method returns the DTO representing the game engine
     * 
     * @return The DTO representing the game engine
     */
    public GameEngineDTO getGameEngineDto() {
        return gameEngineDto;
    }

    /**
     * This method tells if the session is started or not
     * 
     * @return True if the session is started, false otherwise
     */
    public boolean isStarted() {
        return isStarted;
    }

    /**
     * This method returns the player leading the session, ie who can start it
     * 
     * @return The leading player of the session
     * */
    public PlayerDTO getLeader() {
        return leader;
    }

    /**
     * This method returns the game session callback
     * 
     * @return The game session callback
     */
    public IGameSessionCallback getCallback() {
        return callback;
    }

}
