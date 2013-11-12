package com.esir.sr.sweetsnake.factory;

import java.util.LinkedList;
import java.util.List;

import com.esir.sr.sweetsnake.api.IComponent;
import com.esir.sr.sweetsnake.dto.ComponentDTO;
import com.esir.sr.sweetsnake.dto.GameBoardDTO;
import com.esir.sr.sweetsnake.dto.GameRequestDTO;
import com.esir.sr.sweetsnake.dto.GameSessionDTO;
import com.esir.sr.sweetsnake.dto.PlayerDTO;
import com.esir.sr.sweetsnake.enumeration.RefreshAction;
import com.esir.sr.sweetsnake.game.map.GameBoard;
import com.esir.sr.sweetsnake.session.GameRequest;
import com.esir.sr.sweetsnake.session.GameSession;
import com.esir.sr.sweetsnake.session.Player;
import com.esir.sr.sweetsnake.utils.Pair;

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
    public static GameRequestDTO convertGameSessionRequest(final GameRequest request) {
        return new GameRequestDTO(request.getId(), convertPlayer(request.getRequestingPlayer()), convertPlayer(request.getRequestedPlayer()));
    }

    /**
     * 
     * @param session
     * @return
     */
    public static GameSessionDTO convertGameSession(final GameSession session) {
        return new GameSessionDTO(session.getId(), convertPlayer(session.getPlayer1()), convertPlayer(session.getPlayer2()), convertGameBoard(session.getGameEngine().getGameBoard()));
    }

    /**
     * 
     * @param player
     * @return
     */
    public static PlayerDTO convertPlayer(final Player player) {
        return new PlayerDTO(player.getName(), player.getStatus(), player.getSnakeId(), player.getScore());
    }

    /**
     * 
     * @param gameBoard
     * @return
     */
    public static GameBoardDTO convertGameBoard(final GameBoard gameBoard) {
        final List<Pair<IComponent, RefreshAction>> components = gameBoard.getComponentsToRefresh();
        final List<Pair<ComponentDTO, RefreshAction>> componentsDto = new LinkedList<Pair<ComponentDTO, RefreshAction>>();
        for (final Pair<IComponent, RefreshAction> pair : components) {
            final ComponentDTO componentDto = convertComponent(pair.getFirst());
            componentsDto.add(new Pair<ComponentDTO, RefreshAction>(componentDto, pair.getSecond()));
        }

        return new GameBoardDTO(gameBoard.getWidth(), gameBoard.getHeight(), gameBoard.getNbSweets(), componentsDto);
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
