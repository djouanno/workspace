package com.esir.sr.sweetsnake.game.engine;

import java.util.Collections;
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
 * This class represents a game engine used by a game session in order to proceed the events triggered by the clients.
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
    private final Map<Player, IComponent> snakesMapping;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * Creates a new game engine
     * 
     * @param _board
     *            The gameboard
     * @param _playersMap
     *            The players' snakes map
     */
    public GameEngine(final GameSession _session) {
        log.info("Initializing a new Game Engine for session {}", _session.getId());
        session = _session;
        gameBoard = GameBoardGenerator.generateBoard(GameConstants.GRID_SIZE, GameConstants.GRID_SIZE, GameConstants.NUMBER_OF_SWEETS);
        snakesMapping = new LinkedHashMap<Player, IComponent>();

        int i = 1;
        for (final Player player : _session.getPlayers()) {
            final Snake snake = new Snake();
            player.setSnakeId(snake.getId());
            final PlayerPosition position = new PlayerPosition(gameBoard.getWidth(), gameBoard.getHeight(), i);
            snake.setXYPos(position.getXPos(), position.getYPos());
            snakesMapping.put(player, snake);
            gameBoard.addComponent(snake);
            i++;
        }
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    /**
     * This method moves, if it is possible, a player's snake to another cell of the gameboard
     * 
     * @param direction
     *            The direction where to move the snake
     * @param player
     *            The player to whom belongs the snake to move
     */
    public void moveSnake(final MoveDirection direction, final Player player) {
        final IComponent snake = snakesMapping.get(player);
        final int x = (snake.getXPos() + direction.getValue()[0] + GameConstants.GRID_SIZE) % GameConstants.GRID_SIZE;
        final int y = (snake.getYPos() + direction.getValue()[1] + GameConstants.GRID_SIZE) % GameConstants.GRID_SIZE;
        final IComponent currentComponent = gameBoard.getComponent(x, y);

        if (currentComponent == null) {
            gameBoard.moveComponent(snake, direction);
        } else {
            switch (currentComponent.getType()) {
                case SNAKE:
                    // do nothing
                    break;
                case SWEET:
                    gameBoard.removeComponent(currentComponent);
                    gameBoard.moveComponent(snake, direction);
                    player.setScore(player.getScore() + GameConstants.SWEET_SCORE_VALUE);
                    break;
                default:
                    log.warn("Unknown component type {}", currentComponent.getType());
                    break;
            }
        }

        if (gameBoard.getNbSweets() == 0) {
            session.stopGame(false);
        }
    }

    /**
     * This method removes a player's snake from the gameboard
     * 
     * @param player
     *            The player to whom belongs the snake to remove
     */
    public void removeSnake(final Player player) {
        final IComponent snake = snakesMapping.get(player);
        gameBoard.removeComponent(snake);
        player.setSnakeId(null);
    }

    /**********************************************************************************************
     * [BLOCK] GETTERS
     **********************************************************************************************/

    /**
     * This method returns the gameboard associated to the game engine
     * 
     * @return The gameboard associated to the game engine
     */
    public GameBoard getGameBoard() {
        return gameBoard;
    }

    /**
     * This method returns the players' snakes mapping
     * 
     * @return A map representing the players' snakes mapping
     */
    public Map<Player, IComponent> getSnakesMapping() {
        return Collections.unmodifiableMap(snakesMapping);
    }

}
