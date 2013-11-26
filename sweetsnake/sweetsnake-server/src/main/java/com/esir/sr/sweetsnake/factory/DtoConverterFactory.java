package com.esir.sr.sweetsnake.factory;

import java.util.LinkedList;
import java.util.List;

import com.esir.sr.sweetsnake.api.IComponent;
import com.esir.sr.sweetsnake.dto.ComponentDTO;
import com.esir.sr.sweetsnake.dto.GameBoardDTO;
import com.esir.sr.sweetsnake.dto.GameBoardRefreshDTO;
import com.esir.sr.sweetsnake.dto.GameRequestDTO;
import com.esir.sr.sweetsnake.dto.GameSessionDTO;
import com.esir.sr.sweetsnake.dto.PlayerDTO;
import com.esir.sr.sweetsnake.game.board.GameBoard;
import com.esir.sr.sweetsnake.game.board.GameBoardRefresh;
import com.esir.sr.sweetsnake.session.GameRequest;
import com.esir.sr.sweetsnake.session.GameSession;
import com.esir.sr.sweetsnake.session.Player;

/**
 * This class provides factory methods to convert objects into Data Transfer Object ones.
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
public class DtoConverterFactory
{

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * Private constructor to prevent instantiation of the factory
     */
    private DtoConverterFactory() {
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC STATIC METHODS
     **********************************************************************************************/

    /**
     * This method converts a game request into its corresponding DTO
     * 
     * @param request
     *            The game request to convert
     * @return The DTO representing the game request
     */
    public static GameRequestDTO convertGameRequest(final GameRequest request) {
        return new GameRequestDTO(request.getId(), request.getSessionid(), convertPlayer(request.getRequestingPlayer()), convertPlayer(request.getRequestedPlayer()));
    }

    /**
     * This method converts a game session into its corresponding DTO
     * 
     * @param session
     *            The game session to convert
     * @return The DTO representing the game session
     */
    public static GameSessionDTO convertGameSession(final GameSession session) {
        if (session.getGameEngine() == null) {
            return new GameSessionDTO(session.getId(), convertPlayers(session.getPlayers()), null, session.getCallback(), session.isStarted());
        }
        return new GameSessionDTO(session.getId(), convertPlayers(session.getPlayers()), convertGameBoard(session.getGameEngine().getGameBoard()), session.getCallback(), session.isStarted());
    }

    /**
     * This method converts a player into its corresponding DTO
     * 
     * @param player
     *            The player to convert
     * @return The DTO representing the player
     */
    public static PlayerDTO convertPlayer(final Player player) {
        return new PlayerDTO(player.getName(), player.getStatus(), player.getSnakeId(), player.getNumber(), player.getScore());
    }

    /**
     * This method converts a list of players into a list of their corresponding DTO
     * 
     * @param players
     *            The list of players to convert
     * @return A list containing the DTO representing the players
     */
    public static List<PlayerDTO> convertPlayers(final List<Player> players) {
        final List<PlayerDTO> playersDto = new LinkedList<PlayerDTO>();
        for (final Player player : players) {
            playersDto.add(convertPlayer(player));
        }
        return playersDto;
    }

    /**
     * This method converts a gameboard into its corresponding DTO
     * 
     * @param gameBoard
     *            The gameboard to convert
     * @return The DTO representing the gameboard
     */
    public static GameBoardDTO convertGameBoard(final GameBoard gameBoard) {
        return new GameBoardDTO(gameBoard.getWidth(), gameBoard.getHeight(), gameBoard.getNbSweets(), convertGameBoardRefreshes(gameBoard.getRefreshes()));
    }

    /**
     * This method converts a list of gameboard refreshes into a list of their corresponding DTO
     * 
     * @param refreshes
     *            The list of gameboard refreshes to convert
     * @return A list containing the DTO representing the gameboard refreshes
     */
    public static List<GameBoardRefreshDTO> convertGameBoardRefreshes(final List<GameBoardRefresh> refreshes) {
        final List<GameBoardRefreshDTO> refreshesDto = new LinkedList<GameBoardRefreshDTO>();
        for (final GameBoardRefresh refresh : refreshes) {
            final ComponentDTO componentDto = convertComponent(refresh.getComponent());
            refreshesDto.add(new GameBoardRefreshDTO(componentDto, refresh.getAction()));
        }
        return refreshesDto;
    }

    /**
     * This method converts a component into its corresponding DTO
     * 
     * @param component
     *            The component to convert
     * @return The DTO representing the component
     */
    public static ComponentDTO convertComponent(final IComponent component) {
        return new ComponentDTO(component.getId(), component.getXPos(), component.getYPos(), component.getType());
    }

}
