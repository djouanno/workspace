package com.esir.sr.sweetsnake.dto;

import java.io.Serializable;

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
    private static final long  serialVersionUID = 459664230963147646L;

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    /** The session id */
    private final String       id;

    /** The first player DTO */
    private final PlayerDTO    player1Dto;

    /** The second player DTO */
    private final PlayerDTO    player2Dto;

    /** The game board DTO */
    private final GameBoardDTO gameBoardDto;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * 
     * @param _id
     * @param _player1Dto
     * @param _player2Dto
     * @param _gameBoardDto
     */
    public GameSessionDTO(final String _id, final PlayerDTO _player1Dto, final PlayerDTO _player2Dto, final GameBoardDTO _gameBoardDto) {
        id = _id;
        player1Dto = _player1Dto;
        player2Dto = _player2Dto;
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
    public PlayerDTO getPlayer1Dto() {
        return player1Dto;
    }

    /**
     * 
     * @return
     */
    public PlayerDTO getPlayer2Dto() {
        return player2Dto;
    }

    /**
     * 
     * @return
     */
    public GameBoardDTO getGameBoardDto() {
        return gameBoardDto;
    }

}
