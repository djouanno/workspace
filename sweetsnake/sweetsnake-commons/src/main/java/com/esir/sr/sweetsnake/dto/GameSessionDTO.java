package com.esir.sr.sweetsnake.dto;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.esir.sr.sweetsnake.api.IGameSessionCallback;
import com.esir.sr.sweetsnake.constants.GameConstants;

/**
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

    /** The game board DTO */
    private final GameBoardDTO         gameBoardDto;

    /** The session rmi callback */
    private final IGameSessionCallback callback;

    /** Is the session started */
    private final boolean              isStarted;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * 
     * @param _id
     * @param _playersDto
     * @param _gameBoardDto
     * @param _callback
     * @param _isStarted
     */
    public GameSessionDTO(final String _id, final List<PlayerDTO> _playersDto, final GameBoardDTO _gameBoardDto, final IGameSessionCallback _callback, final boolean _isStarted) {
        id = _id;
        playersDto = new LinkedList<PlayerDTO>(_playersDto);
        gameBoardDto = _gameBoardDto;
        callback = _callback;
        isStarted = _isStarted;
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    @Override
    public String toString() {
        String st = id;
        if (playersDto.size() > 0) {
            st += " [" + playersDto.size() + "/" + GameConstants.MAX_NUMBER_OF_PLAYERS + "]";
        }
        return st;
    }

    /**********************************************************************************************
     * [BLOCK] GETTERS
     **********************************************************************************************/

    /**
     * 
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * @return
     */
    public List<PlayerDTO> getPlayersDto() {
        return Collections.unmodifiableList(playersDto);
    }

    /**
     * 
     * @return
     */
    public GameBoardDTO getGameBoardDto() {
        return gameBoardDto;
    }

    /**
     * 
     * @return
     */
    public boolean isStarted() {
        return isStarted;
    }

    /**
     * 
     * @return
     */
    public IGameSessionCallback getCallback() {
        return callback;
    }

}
