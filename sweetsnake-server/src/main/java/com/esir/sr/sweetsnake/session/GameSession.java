package com.esir.sr.sweetsnake.session;

import java.rmi.RemoteException;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.LoggerFactory;

import com.esir.sr.sweetsnake.api.IElement;
import com.esir.sr.sweetsnake.constants.GameConstants;
import com.esir.sr.sweetsnake.constants.PropertiesConstants;
import com.esir.sr.sweetsnake.element.Snake;
import com.esir.sr.sweetsnake.element.Sweet;
import com.esir.sr.sweetsnake.enumeration.MoveDirection;
import com.esir.sr.sweetsnake.enumeration.PlayerStatus;
import com.esir.sr.sweetsnake.factory.DtoConverterFactory;
import com.esir.sr.sweetsnake.utils.Pair;

/**
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
public class GameSession
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    /** The logger */
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(GameSession.class);

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    /** The session id */
    private final String                  id;

    /** The first player */
    private final Player                  player1;

    /** The second player */
    private final Player                  player2;

    // TODO do not use Pair, find another way
    private final Pair<Player, IElement>  player1Snake;
    private final Pair<Player, IElement>  player2Snake;
    // TODO move to a gameMap object
    private IElement[][]                  gameMap;
    private int                           remainingSweets;

    /** Is the game started */
    private boolean                       isGameStarted;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * 
     * @param _player1
     * @param _player2
     */
    public GameSession(final Player _player1, final Player _player2) {
        log.info("Initializing a new game session between {} and {}", _player1.getName(), _player2.getName());
        id = RandomStringUtils.randomAlphanumeric(PropertiesConstants.GENERATED_ID_LENGTH);
        player1 = _player1;
        player2 = _player2;
        player1Snake = new Pair<Player, IElement>(player1, new Snake());
        player2Snake = new Pair<Player, IElement>(player2, new Snake());
        isGameStarted = false;
        remainingSweets = GameConstants.NUMBER_OF_SWEETS;
    }

    /**********************************************************************************************
     * [BLOCK] PRIVATE METHODS
     **********************************************************************************************/

    /**
     * 
     * @param player
     * @return
     */
    private IElement retrievePlayerSnake(final Player player) {
        if (player == player1Snake.getFirst()) {
            return player1Snake.getSecond();
        }
        if (player == player2Snake.getFirst()) {
            return player2Snake.getSecond();
        }
        return null;
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    /**
     * 
     */
    public void startGame() {
        log.info("Initializing a new gameboard between {} and {}", player1.getName(), player2.getName());

        gameMap = new IElement[GameConstants.GRID_SIZE][GameConstants.GRID_SIZE];
        gameMap[0][0] = player1Snake.getSecond();
        gameMap[GameConstants.GRID_SIZE - 1][GameConstants.GRID_SIZE - 1] = player2Snake.getSecond();

        final Random random = new Random();
        for (int i = 0; i < GameConstants.NUMBER_OF_SWEETS; i++) {
            int j, k;
            do {
                j = random.nextInt(GameConstants.GRID_SIZE);
                k = random.nextInt(GameConstants.GRID_SIZE);
            } while (gameMap[j][k] != null);

            gameMap[j][k] = new Sweet();
            gameMap[j][k].setXY(j, k);
        }

        try {
            player1.getClientCallback().startGame(DtoConverterFactory.convertGameSession(this));
            player2.getClientCallback().startGame(DtoConverterFactory.convertGameSession(this));

            // TODO maybe reconfirm from client side to synchronize game start
            isGameStarted = true;

            player1.setStatus(PlayerStatus.PLAYING);
            player2.setStatus(PlayerStatus.PLAYING);
        } catch (final RemoteException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**
     * 
     */
    public void stopGame() {
        // TODO
    }

    /**
     * 
     * @param player
     * @param direction
     */
    public void movePlayer(final Player player, final MoveDirection direction) {
        final IElement playerSnake = retrievePlayerSnake(player);
        if (playerSnake == null) {
            log.error("Can't find player's snake"); // TODO throw exception
        }

        playerSnake.move(direction);

        final IElement element = gameMap[playerSnake.getX()][playerSnake.getY()];
        if (element != null) {
            gameMap[playerSnake.getX()][playerSnake.getY()] = null;
            remainingSweets--;
            log.info("{} has eaten a sweet ; {} remaining on the map", player.getName(), remainingSweets);
            if (remainingSweets == 0) {
                stopGame();
            }
        }
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
    public Player getPlayer1() {
        return player1;
    }

    /**
     * 
     * @return
     */
    public Player getPlayer2() {
        return player2;
    }

    /**
     * 
     * @return
     */
    public boolean isGameStarted() {
        return isGameStarted;
    }

}
