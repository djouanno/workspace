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
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
public class DtoConverterFactory
{

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    /**
     * 
     * @param request
     * @return
     */
    public static GameRequestDTO convertGameRequest(final GameRequest request) {
        return new GameRequestDTO(request.getId(), request.getSessionid(), convertPlayer(request.getRequestingPlayer()), convertPlayer(request.getRequestedPlayer()));
    }

    /**
     * 
     * @param session
     * @return
     */
    public static GameSessionDTO convertGameSession(final GameSession session) {
        if (session.getGameEngine() == null) {
            return new GameSessionDTO(session.getId(), convertPlayers(session.getPlayers()), null, session.getCallback(), session.allReady(), session.isStarted());
        }
        return new GameSessionDTO(session.getId(), convertPlayers(session.getPlayers()), convertGameBoard(session.getGameEngine().getGameBoard()), session.getCallback(), session.allReady(), session.isStarted());
    }

    /**
     * 
     * @param player
     * @return
     */
    public static PlayerDTO convertPlayer(final Player player) {
        return new PlayerDTO(player.getName(), player.getStatus(), player.getSnakeId(), player.getNumber(), player.getScore(), player.isFictive());
    }

    /**
     * 
     * @param players
     * @return
     */
    public static List<PlayerDTO> convertPlayers(final List<Player> players) {
        final List<PlayerDTO> playersDto = new LinkedList<PlayerDTO>();
        for (final Player player : players) {
            playersDto.add(convertPlayer(player));
        }
        return playersDto;
    }

    /**
     * 
     * @param gameBoard
     * @return
     */
    public static GameBoardDTO convertGameBoard(final GameBoard gameBoard) {
        return new GameBoardDTO(gameBoard.getWidth(), gameBoard.getHeight(), gameBoard.getNbSweets(), convertGameBoardRefreshes(gameBoard.getRefreshes()));
    }

    /**
     * 
     * @param refreshes
     * @return
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
     * 
     * @param component
     * @return
     */
    public static ComponentDTO convertComponent(final IComponent component) {
        return new ComponentDTO(component.getId(), component.getXPos(), component.getYPos(), component.getType());
    }

}
