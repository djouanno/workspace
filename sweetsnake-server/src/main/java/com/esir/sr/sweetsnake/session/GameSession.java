package com.esir.sr.sweetsnake.session;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.LoggerFactory;

import com.esir.sr.sweetsnake.api.IElement;
import com.esir.sr.sweetsnake.constants.GameConstants;
import com.esir.sr.sweetsnake.constants.PropertiesConstants;
import com.esir.sr.sweetsnake.element.Snake;
import com.esir.sr.sweetsnake.enumeration.MoveDirection;
import com.esir.sr.sweetsnake.enumeration.PlayerStatus;
import com.esir.sr.sweetsnake.factory.DtoConverterFactory;
import com.esir.sr.sweetsnake.game.engine.Engine;
import com.esir.sr.sweetsnake.game.map.BoardGenerator;

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

    private final String                                    id;
    private final Player                           			player1, player2;
   
    private final Map<Player, IElement> mapPlayers;
    private boolean                                         gameStarted;
    private int                                             remainingSweets;
    
    // TODO Mettre en AUTOWIRED
    private BoardGenerator boardGenerator = new BoardGenerator();
    // TODO Mettre en AUTOWIRED
    private Engine engine = new Engine();

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
         
         
         mapPlayers = new HashMap<Player, IElement>();
         Snake snake = new Snake();
         mapPlayers.put(player1, snake);
         
         snake = new Snake();
         mapPlayers.put(player2, snake);
         
         engine.setMapPlayers(mapPlayers);
         
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
    private IElement retrievePlayerSnake(final Player player) {
    	return mapPlayers.get(player);
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    /**
     * 
     */
    public void startGame() {
		log.info("Initializing a new gameboard between {} and {}", player1.getName(), player2.getName());
		
		engine.setBoard(boardGenerator.generateBoard(GameConstants.GRID_SIZE, GameConstants.GRID_SIZE, GameConstants.NUMBER_OF_SWEETS));
		
		mapPlayers.get(player1).setXY(0, 0);
		engine.getBoard().set(mapPlayers.get(player1));
		
		mapPlayers.get(player2).setXY(5, 5);
		engine.getBoard().set(mapPlayers.get(player2));
		
		try {
		    player1.getClientCallback().startGame(DtoConverterFactory.convertGameSession(this));
		    player2.getClientCallback().startGame(DtoConverterFactory.convertGameSession(this));
		
		    // TODO maybe reconfirm from client side to synchronize game start
		    gameStarted = true;
		
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
    	engine.update(direction, player);
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
