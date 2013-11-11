package com.esir.sr.sweetsnake.game.engine;

import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.esir.sr.sweetsnake.api.IComponent;
import com.esir.sr.sweetsnake.constants.GameConstants;
import com.esir.sr.sweetsnake.enumeration.MoveDirection;
import com.esir.sr.sweetsnake.game.component.Snake;
import com.esir.sr.sweetsnake.game.map.GameBoard;
import com.esir.sr.sweetsnake.game.map.GameBoardGenerator;
import com.esir.sr.sweetsnake.session.GameSession;
import com.esir.sr.sweetsnake.session.Player;

/**
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
public class GameEngine
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    /** The logger */
    private static final Logger           log = LoggerFactory.getLogger(GameEngine.class);

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    /** The game board */
    private final GameBoard               gameBoard;

    /** The players map */
    private final Map<Player, IComponent> playersMap;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * 
     * @param _board
     * @param _playersMap
     */
    public GameEngine(final GameSession session) {
        log.info("Initializing a new Game Engine between for session {}", session);
        gameBoard = GameBoardGenerator.generateBoard(GameConstants.GRID_SIZE, GameConstants.GRID_SIZE, GameConstants.NUMBER_OF_SWEETS);
        playersMap = new LinkedHashMap<Player, IComponent>();

        final Snake player1Snake = new Snake();
        player1Snake.setXYPos(0, 0);
        playersMap.put(session.getPlayer1(), player1Snake);
        gameBoard.setElement(player1Snake);

        final Snake player2Snake = new Snake();
        player2Snake.setXYPos(GameConstants.GRID_SIZE - 1, GameConstants.GRID_SIZE - 1);
        playersMap.put(session.getPlayer2(), player2Snake);
        gameBoard.setElement(player2Snake);
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    /**
     * 
     * @param direction
     * @param player
     */
    public void moveSnake(final MoveDirection direction, final Player player) {
        final IComponent snake = playersMap.get(player);
        final int x = (snake.getXPos() + direction.getValue()[0] + GameConstants.GRID_SIZE) % GameConstants.GRID_SIZE;
        final int y = (snake.getYPos() + direction.getValue()[1] + GameConstants.GRID_SIZE) % GameConstants.GRID_SIZE;
        final IComponent currentElement = gameBoard.getElement(x, y);

        gameBoard.removeElement(snake);

        if (currentElement == null) {
            snake.move(direction);
            gameBoard.setElement(snake);
        } else {
            switch (currentElement.getType()) {
                case SNAKE: // other player snake cell
                    gameBoard.setElement(snake);
                    break;
                case SWEET: // sweet cell
                    gameBoard.removeElement(currentElement);
                    snake.move(direction);
                    gameBoard.setElement(snake);
                    player.setScore(player.getScore() + GameConstants.SWEET_SCORE_VALUE);
                    break;
                default:
                    break;
            }
        }

        if (gameBoard.getNbSweets() == 0) {

        }
    }

    /**********************************************************************************************
     * [BLOCK] GETTERS
     **********************************************************************************************/

    /**
     * 
     * @return
     */
    public GameBoard getGameBoard() {
        return gameBoard;
    }

}
