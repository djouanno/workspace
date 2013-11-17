package com.esir.sr.sweetsnake.game.engine;

import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.esir.sr.sweetsnake.api.IComponent;
import com.esir.sr.sweetsnake.constants.GameConstants;
import com.esir.sr.sweetsnake.enumeration.MoveDirection;
import com.esir.sr.sweetsnake.game.board.GameBoard;
import com.esir.sr.sweetsnake.game.board.GameBoardGenerator;
import com.esir.sr.sweetsnake.game.component.Snake;
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

    /** The game session */
    private final GameSession             session;

    /** The game board */
    private final GameBoard               gameBoard;

    /** The players' snakes mapping */
    private final Map<Player, IComponent> playersMap;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * 
     * @param _board
     * @param _playersMap
     */
    public GameEngine(final GameSession _session) {
        log.info("Initializing a new Game Engine between for session {}", _session);
        session = _session;
        gameBoard = GameBoardGenerator.generateBoard(GameConstants.GRID_SIZE, GameConstants.GRID_SIZE, GameConstants.NUMBER_OF_SWEETS);
        playersMap = new LinkedHashMap<Player, IComponent>();

        int i = 1;
        for (final Player player : _session.getPlayers()) {
            final Snake snake = new Snake();
            player.setSnakeId(snake.getId());
            final PlayerPosition position = new PlayerPosition(gameBoard.getWidth(), gameBoard.getHeight(), i);
            snake.setXYPos(position.getXPos(), position.getYPos());
            playersMap.put(player, snake);
            gameBoard.addComponent(snake);
            i++;
        }
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
        final IComponent currentComponent = gameBoard.getComponent(x, y);

        if (currentComponent == null) {
            log.debug("Current component is null");
            gameBoard.moveComponent(snake, direction);
        } else {
            log.debug("Current component is not null");
            switch (currentComponent.getType()) {
            // other player snake cell
                case SNAKE:
                    // do nothing
                    break;
                // sweet cell
                case SWEET:
                    gameBoard.removeComponent(currentComponent);
                    gameBoard.moveComponent(snake, direction);
                    player.setScore(player.getScore() + GameConstants.SWEET_SCORE_VALUE);
                    break;
            }
        }

        if (gameBoard.getNbSweets() == 0) {
            session.stopGame();
        }
    }

    /**
     * 
     * @param player
     */
    public void removeSnake(final Player player) {
        final IComponent snake = playersMap.get(player);
        gameBoard.removeComponent(snake);
        player.setSnakeId(null);
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
