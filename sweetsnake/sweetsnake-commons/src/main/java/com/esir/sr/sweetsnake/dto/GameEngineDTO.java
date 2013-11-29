package com.esir.sr.sweetsnake.dto;

import java.io.Serializable;
import java.util.Collections;
import java.util.Map;

/**
 * This class is a Data Transfert Object representing a GameEngine.
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
public class GameEngineDTO implements Serializable
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    /** The serial version UID */
    private static final long                  serialVersionUID = 1323803113081012512L;

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    /** The gameboard DTO */
    private final GameBoardDTO                 gameBoardDto;

    /** The players' snakes mapping */
    private final Map<PlayerDTO, ComponentDTO> snakesMapping;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * Creates a new game engine DTO
     * 
     * @param _gameBoardDto
     *            The game engine DTO
     * @param _snakesMapping
     *            The snakes mapping
     */
    public GameEngineDTO(final GameBoardDTO _gameBoardDto, final Map<PlayerDTO, ComponentDTO> _snakesMapping) {
        gameBoardDto = _gameBoardDto;
        snakesMapping = _snakesMapping;
    }

    /**********************************************************************************************
     * [BLOCK] GETTERS
     **********************************************************************************************/

    /**
     * This method returns the DTO representing the game board
     * 
     * @return The DTO representing the game board
     */
    public GameBoardDTO getGameBoardDto() {
        return gameBoardDto;
    }

    /**
     * This method returns the players' snakes mapping
     * 
     * @return A map representing the players' snakes mapping
     */
    public Map<PlayerDTO, ComponentDTO> getSnakesMapping() {
        return Collections.unmodifiableMap(snakesMapping);
    }

}
