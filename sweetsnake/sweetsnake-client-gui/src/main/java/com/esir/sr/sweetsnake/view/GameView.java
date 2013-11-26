package com.esir.sr.sweetsnake.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.esir.sr.sweetsnake.api.IComponent;
import com.esir.sr.sweetsnake.component.GameBoardPanel;
import com.esir.sr.sweetsnake.component.Snake;
import com.esir.sr.sweetsnake.component.Sweet;
import com.esir.sr.sweetsnake.constants.ClientGuiConstants;
import com.esir.sr.sweetsnake.constants.GameConstants;
import com.esir.sr.sweetsnake.dto.ComponentDTO;
import com.esir.sr.sweetsnake.dto.GameBoardDTO;
import com.esir.sr.sweetsnake.dto.GameBoardRefreshDTO;
import com.esir.sr.sweetsnake.dto.PlayerDTO;
import com.esir.sr.sweetsnake.enumeration.MoveDirection;
import com.esir.sr.sweetsnake.enumeration.RefreshAction;

/**
 * This class graphically reprents the game view by extending the AbstractView class.
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 * @see com.esir.sr.sweetsnake.view.AbstractView
 */
@Component
public class GameView extends AbstractView
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    /** The serial version UID */
    private static final long    serialVersionUID = 6919247419837806743L;

    /** The logger */
    private static final Logger  log              = LoggerFactory.getLogger(GameView.class);

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    /** The game board DTO */
    private GameBoardDTO         gameBoardDto;

    /** The top panel */
    private JPanel               topPL;

    /** The center panel */
    private JPanel               centerPL;

    /** The game board panel */
    private GameBoardPanel       gameBoardPL;

    /** The bottom panel */
    private JPanel               bottomPL;

    /** The players' scores label */
    private JLabel[]             playersScoresLB;

    /** The quit button */
    private JButton              quitBTN;

    /** The players' numbers & snakes map */
    private Map<Integer, String> playersSnakes;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR & INIT
     **********************************************************************************************/

    /**
     * Creates a new game view
     */
    protected GameView() {
        super();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.view.SweetSnakeAbstractView#init()
     */
    @PostConstruct
    @Override
    protected void init() {
        super.init();
        log.info("Initializing the Game View");
        playersSnakes = new LinkedHashMap<Integer, String>();
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.view.SweetSnakeAbstractView#buildImpl()
     */
    @Override
    protected void buildImpl() {
        setLayout(new BorderLayout());

        // top panel
        initTopPL();
        add(topPL, BorderLayout.NORTH);

        initPlayersScoresLB();
        final GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 5, 170);
        topPL.add(playersScoresLB[0], gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(0, 170, 5, 0);
        topPL.add(playersScoresLB[3], gbc);

        // center panel
        initCenterPL();
        add(centerPL, BorderLayout.CENTER);

        initGameBoardPL();

        centerPL.add(gameBoardPL);

        // bottom panel
        initBottomPL();
        add(bottomPL, BorderLayout.SOUTH);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 0, 0, 120);
        bottomPL.add(playersScoresLB[2], gbc);

        initQuitBTN();
        gbc.gridx = 1;
        gbc.insets = new Insets(5, 0, 0, 0);
        bottomPL.add(quitBTN, gbc);

        gbc.gridx = 2;
        gbc.insets = new Insets(5, 120, 0, 0);
        bottomPL.add(playersScoresLB[1], gbc);

        addKeyListener(new KeyboardListener());
    }

    /**
     * This method refreshes the displayed players' scores
     * 
     * @param playersScores
     *            The players list
     */
    public void refreshScores(final List<PlayerDTO> players) {
        for (final PlayerDTO player : players) {
            playersScoresLB[player.getNumber() - 1].setText(intToString(player.getScore(), 3));
        }
    }

    /**
     * This method hides the score label for the specified player
     * 
     * @param _playerNb
     *            The player's number to hide
     */
    public void hideScore(final int _playerNb) {
        playersScoresLB[_playerNb - 1].setForeground(findSnakeColor(null, _playerNb));
    }

    /**
     * This method draws the gameboard according to the DTO representing the gameboard currently set
     */
    public void drawGameboard() {
        final List<GameBoardRefreshDTO> refreshes = gameBoardDto.getComponentsToRefresh();
        for (final GameBoardRefreshDTO refresh : refreshes) {
            final ComponentDTO componentDto = refresh.getComponentDto();
            final RefreshAction action = refresh.getAction();
            final IComponent component = gameBoardPL.getComponentById(componentDto.getId());

            switch (action) {
                case ADD:
                    IComponent newComponent = null;
                    final int x = componentDto.getX(),
                    y = componentDto.getY();
                    switch (componentDto.getType()) {
                        case SNAKE:
                            newComponent = new Snake(componentDto.getId(), x, y, findSnakeIconPath(componentDto.getId()));
                            break;
                        case SWEET:
                            newComponent = new Sweet(componentDto.getId(), x, y);
                            break;
                    }
                    gameBoardPL.addComponent(newComponent);
                    break;
                case MOVE:
                    component.setXYPos(componentDto.getX(), componentDto.getY());
                    gameBoardPL.moveComponent(component);
                    break;
                case REMOVE:
                    gameBoardPL.removeComponent(component);
                    break;
            }
        }
    }

    /**
     * This methods sets the DTO representing the gameboard to be drawn
     * 
     * @param _gameBoardDto
     *            The DTO representing the gameboard to be drawn
     */
    public void setGameboardDto(final GameBoardDTO _gameBoardDto) {
        gameBoardDto = _gameBoardDto;
    }

    /**
     * This method sets the players' snakes map to map each player with its component
     * 
     * @param _playersSnakes
     *            The players' snakes map
     */
    public void setPlayersSnakesMap(final Map<Integer, String> _playersSnakes) {
        playersSnakes = _playersSnakes;
    }

    /**********************************************************************************************
     * [BLOCK] PRIVATE METHODS
     **********************************************************************************************/

    /**
     * This methods initializes the top panel
     */
    private void initTopPL() {
        topPL = new JPanel();
        topPL.setLayout(new GridBagLayout());
        topPL.setOpaque(false);
    }

    /**
     * This methods initializes the center panel
     */
    private void initCenterPL() {
        centerPL = new JPanel();
        centerPL.setOpaque(true);
        centerPL.setBackground(Color.cyan);
        centerPL.setLayout(new GridBagLayout());
    }

    /**
     * This methods initializes the bottom panel
     */
    private void initBottomPL() {
        bottomPL = new JPanel();
        bottomPL.setLayout(new GridBagLayout());
        bottomPL.setOpaque(false);
    }

    /**
     * This methods initializes the gameboard panel
     */
    private void initGameBoardPL() {
        if (gameBoardDto != null) {
            gameBoardPL = new GameBoardPanel(gameBoardDto.getWidth(), gameBoardDto.getHeight());
            gameBoardPL.setBorder(new EmptyBorder(0, 0, 10, 0));
            drawGameboard();
        }
    }

    /**
     * This methods initializes the players' scores labels
     */
    private void initPlayersScoresLB() {
        playersScoresLB = new JLabel[GameConstants.MAX_NUMBER_OF_PLAYERS];
        for (int i = 0; i < playersScoresLB.length; i++) {
            playersScoresLB[i] = new JLabel(intToString(0, 3));
            playersScoresLB[i].setForeground(findSnakeColor(playersSnakes.get(i + 1), i + 1));
            playersScoresLB[i].setFont(new Font("sans-serif", Font.BOLD, 20));
        }
    }

    /**
     * This methods initializes the quit button
     */
    private void initQuitBTN() {
        quitBTN = new JButton("quit game");
        quitBTN.addActionListener(new QuitGameListener());
    }

    /**
     * This method finds the snake icon path according to the specified snake id
     * 
     * @param snakeId
     *            The snake id
     * @return The snake icon path according to the specified snake id
     */
    private String findSnakeIconPath(final String snakeId) {
        for (final int player : playersSnakes.keySet()) {
            if (playersSnakes.get(player).equals(snakeId)) {
                switch (player) {
                    case 2:
                        return ClientGuiConstants.RED_SNAKE_ICON_PATH;
                    case 3:
                        return ClientGuiConstants.BLUE_SNAKE_ICON_PATH;
                    case 4:
                        return ClientGuiConstants.BLACK_SNAKE_ICON_PATH;
                    default:
                        return ClientGuiConstants.GREEN_SNAKE_ICON_PATH;
                }
            }
        }
        return ClientGuiConstants.GREEN_SNAKE_ICON_PATH;
    }

    /**
     * This method finds the snake color according to the specified snake id and player's number
     * 
     * @param snakeId
     *            The snake id
     * @param i
     *            The player's number
     * @return The snake color according to the specified snake id and player's number
     */
    private Color findSnakeColor(final String snakeId, final int i) {
        switch (i) {
            case 2:
                return new Color(255, 0, 0, snakeId == null ? 0 : 255);
            case 3:
                return new Color(12, 12, 235, snakeId == null ? 0 : 255);
            case 4:
                return new Color(0, 0, 0, snakeId == null ? 0 : 255);
            default:
                return new Color(39, 109, 31, snakeId == null ? 0 : 255);
        }
    }

    /**
     * This method converts an integer to a string containing the specified number of zeros before the integer
     * 
     * @param num
     *            The number to convert
     * @param digits
     *            The number of 0 to display before the number
     * @return A string representing the number filled with zero
     */
    private static String intToString(final int num, final int digits) {
        final char[] zeros = new char[digits];
        Arrays.fill(zeros, '0');
        final DecimalFormat df = new DecimalFormat(String.valueOf(zeros));

        return df.format(num);
    }

    /**********************************************************************************************
     * [BLOCK] INTERNAL LISTENERS
     **********************************************************************************************/

    /**
     * This class provides an action listener for the quit button by implementing the ActionListener interface.
     * 
     * @author Herminaël Rougier
     * @author Damien Jouanno
     * 
     * @see java.awt.event.ActionListener
     */
    private class QuitGameListener implements ActionListener
    {

        /*
         * (non-Javadoc)
         * 
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        @Override
        public void actionPerformed(final ActionEvent e) {
            client.leaveSession();
        }

    }

    /**
     * This class provides a key listener for the game view by implementing the KeyListener interface.
     * 
     * @author Herminaël Rougier
     * @author Damien Jouanno
     * 
     * @see java.awt.event.KeyListener
     */
    private class KeyboardListener implements KeyListener
    {

        /** The move table mapping */
        private final Map<Integer, MoveDirection> moveTable;

        /**
         * Creates a new keyboard listener
         */
        public KeyboardListener() {
            moveTable = new LinkedHashMap<Integer, MoveDirection>();
            moveTable.put(KeyEvent.VK_DOWN, MoveDirection.DOWN);
            moveTable.put(KeyEvent.VK_UP, MoveDirection.UP);
            moveTable.put(KeyEvent.VK_LEFT, MoveDirection.LEFT);
            moveTable.put(KeyEvent.VK_RIGHT, MoveDirection.RIGHT);
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
         */
        @Override
        public void keyPressed(final KeyEvent e) {
            final int keyCode = e.getKeyCode();
            if (moveTable.containsKey(keyCode)) {
                client.moveSnake(moveTable.get(keyCode));
            }
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
         */
        @Override
        public void keyReleased(final KeyEvent e) {
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
         */
        @Override
        public void keyTyped(final KeyEvent e) {
        }

    }

}
