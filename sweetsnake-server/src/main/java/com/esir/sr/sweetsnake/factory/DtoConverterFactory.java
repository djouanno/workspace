package com.esir.sr.sweetsnake.factory;

import com.esir.sr.sweetsnake.api.IElement;
import com.esir.sr.sweetsnake.dto.ElementDTO;
import com.esir.sr.sweetsnake.dto.GameBoardDTO;
import com.esir.sr.sweetsnake.dto.GameRequestDTO;
import com.esir.sr.sweetsnake.dto.GameSessionDTO;
import com.esir.sr.sweetsnake.dto.PlayerDTO;
import com.esir.sr.sweetsnake.game.map.GameBoard;
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
        return new PlayerDTO(player.getName(), player.getStatus());
    }

    /**
     * 
     * @param gameBoard
     * @return
     */
    public static GameBoardDTO convertGameBoard(final GameBoard gameBoard) {
        final ElementDTO[][] elements = new ElementDTO[gameBoard.getWidth()][gameBoard.getHeight()];
        for (int x = 0; x < gameBoard.getWidth(); x++) {
            for (int y = 0; y < gameBoard.getHeight(); y++) {
                final IElement element = gameBoard.getElement(x, y);
                if (element != null) {
                    elements[x][y] = new ElementDTO(element.getId(), x, y, element.getType());
                }
            }
        }

        return new GameBoardDTO(gameBoard.getWidth(), gameBoard.getHeight(), elements, gameBoard.getNbSweets());
    }
}
