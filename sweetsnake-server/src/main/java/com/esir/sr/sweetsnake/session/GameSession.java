package com.esir.sr.sweetsnake.session;

import java.rmi.RemoteException;
import java.util.Random;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.LoggerFactory;

import com.esir.sr.sweetsnake.api.IElement;
import com.esir.sr.sweetsnake.api.IGameSession;
import com.esir.sr.sweetsnake.api.IPlayer;
import com.esir.sr.sweetsnake.constants.GameConstants;
import com.esir.sr.sweetsnake.constants.PropertiesConstants;
import com.esir.sr.sweetsnake.enumeration.MoveDirection;
import com.esir.sr.sweetsnake.enumeration.PlayerStatus;
import com.esir.sr.sweetsnake.factory.SessionsFactory;
import com.esir.sr.sweetsnake.game.Snake;
import com.esir.sr.sweetsnake.game.Sweet;
import com.esir.sr.sweetsnake.utils.Pair;

public class GameSession implements IGameSession
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    private static final org.slf4j.Logger                     log = LoggerFactory.getLogger(GameSession.class);

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    private final String                                      id;
    private final IPlayer                           player1, player2;
    // TODO do not use Pair, find another way
    private final Pair<IPlayer, IElement> player1Snake;
    private final Pair<IPlayer, IElement> player2Snake;
    private IElement[][]                            gameMap;
    private boolean                                           gameStarted;
    private int                                               remainingSweets;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * 
     * @param _player1
     * @param _player2
     */
    public GameSession(final IPlayer _player1, final IPlayer _player2) {
        log.info("Initializing a new game session between {} and {}", _player1.getName(), _player2.getName());
        id = RandomStringUtils.randomAlphanumeric(PropertiesConstants.GENERATED_ID_LENGTH);
        player1 = _player1;
        player2 = _player2;
        player1Snake = new Pair<IPlayer, IElement>(player1, new Snake());
        player2Snake = new Pair<IPlayer, IElement>(player2, new Snake());
        gameStarted = false;
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
    private IElement retrievePlayerSnake(final IPlayer player) {
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

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeGameSession#startGame()
     */
    @Override
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
            player1.getClientCallback().startGame(SessionsFactory.convertGameSession(this));
            player2.getClientCallback().startGame(SessionsFactory.convertGameSession(this));

            // TODO maybe reconfirm from client side to synchronize game start
            gameStarted = true;

            player1.setStatus(PlayerStatus.PLAYING);
            player2.setStatus(PlayerStatus.PLAYING);
        } catch (final RemoteException e) {
            log.error(e.getMessage(), e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeGameSession#stopGame()
     */
    @Override
    public void stopGame() {

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeGameSession#movePlayer(com.esir.sr.sweetsnake.api. ISweetSnakePlayer,
     * com.esir.sr.sweetsnake.enumeration.Direction)
     */
    @Override
    public void movePlayer(final IPlayer player, final MoveDirection direction) {
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

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeGameSession#getId()
     */
    @Override
    public String getId() {
        return id;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeGameSession#getPlayer1()
     */
    @Override
    public IPlayer getPlayer1() {
        return player1;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeGameSession#getPlayer2()
     */
    @Override
    public IPlayer getPlayer2() {
        return player2;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeGameSession#isGameStarted()
     */
    @Override
    public boolean isGameStarted() {
        return gameStarted;
    }

}
