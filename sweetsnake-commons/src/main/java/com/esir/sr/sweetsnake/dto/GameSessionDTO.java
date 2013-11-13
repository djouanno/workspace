package com.esir.sr.sweetsnake.dto;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

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
    private static final long     serialVersionUID = 459664230963147646L;

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    /** The session id */
    private final String          id;

    /** The players list DTO */
    private final List<PlayerDTO> playersDto;

    /** The game board DTO */
    private final GameBoardDTO    gameBoardDto;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * 
     * @param _id
     * @param _playersDto
     * @param _gameBoardDto
     */
    public GameSessionDTO(final String _id, final List<PlayerDTO> _playersDto, final GameBoardDTO _gameBoardDto) {
        id = _id;
        playersDto = new LinkedList<PlayerDTO>(_playersDto);
        gameBoardDto = _gameBoardDto;
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

}
