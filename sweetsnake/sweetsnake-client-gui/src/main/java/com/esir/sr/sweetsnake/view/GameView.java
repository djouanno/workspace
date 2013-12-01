package com.esir.sr.sweetsnake.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.esir.sr.sweetsnake.api.IComponent;
import com.esir.sr.sweetsnake.component.CustomButton;
import com.esir.sr.sweetsnake.component.GameBoardPanel;
import com.esir.sr.sweetsnake.component.Snake;
import com.esir.sr.sweetsnake.component.Sweet;
import com.esir.sr.sweetsnake.constants.ClientGuiConstants;
import com.esir.sr.sweetsnake.constants.GameConstants;
import com.esir.sr.sweetsnake.dto.ComponentDTO;
import com.esir.sr.sweetsnake.dto.GameBoardRefreshDTO;
import com.esir.sr.sweetsnake.dto.GameEngineDTO;
import com.esir.sr.sweetsnake.dto.PlayerDTO;
import com.esir.sr.sweetsnake.enumeration.MoveDirection;
import com.esir.sr.sweetsnake.enumeration.RefreshAction;
import com.esir.sr.sweetsnake.enumeration.SoundEffect;
import com.esir.sr.sweetsnake.utils.SoundPlayer;

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
    private static final long   serialVersionUID = 6919247419837806743L;

    /** The logger */
    private static final Logger log              = LoggerFactory.getLogger(GameView.class);

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    /** The top panel */
    private JPanel              topPL;

    /** The center panel */
    private JPanel              centerPL;

    /** The game board panel */
    private GameBoardPanel      gameBoardPL;

    /** The bottom panel */
    private JPanel              bottomPL;

    /** The players' scores label */
    private JLabel[]            playersScoresLB;

    /** The quit button */
    private JButton             quitBTN;

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
        dimension = new Dimension(ClientGuiConstants.VIEW_WIDTH - 15, ClientGuiConstants.VIEW_HEIGHT);
        setSize(dimension);
        setPreferredSize(dimension);

        // top panel
        initTopPL();
        add(topPL, BorderLayout.NORTH);

        initPlayersScoresLB();
        final GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.weightx = 1;
        gbc.weighty = 1;
        topPL.add(playersScoresLB[0], gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        topPL.add(playersScoresLB[3], gbc);

        // center panel
        initCenterPL();
        add(centerPL, BorderLayout.CENTER);

        initGameBoardPL();

        centerPL.add(gameBoardPL);

        // bottom panel
        initBottomPL();
        add(bottomPL, BorderLayout.SOUTH);

        final JPanel p = new JPanel(new GridBagLayout());
        p.setOpaque(false);

        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        p.add(playersScoresLB[2], gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;
        p.add(playersScoresLB[1], gbc);
        bottomPL.add(p, BorderLayout.NORTH);

        final JPanel p2 = new JPanel(new GridBagLayout());
        p2.setOpaque(false);

        initQuitBTN();
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(5, 0, 0, 0);
        p2.add(quitBTN, gbc);
        bottomPL.add(p2, BorderLayout.CENTER);

        addKeyListener(new KeyboardListener());
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.view.AbstractView#clear()
     */
    @Override
    public void clear() {
        gameBoardPL.removeAll();
        for (int i = 1; i <= GameConstants.MAX_NUMBER_OF_PLAYERS; i++) {
            hideScore(i);
        }
    }

    /**
     * This method refreshes the displayed players' scores
     * 
     * @param playersScores
     *            The players list
     */
    public void refreshScores(final List<PlayerDTO> players) {
        for (final PlayerDTO player : players) {
            final int i = player.getNumber() - 1;
            final String text = i % 2 == 0 ? intToString(player.getScore(), 3) + " " + player.getName() : player.getName() + " " + intToString(player.getScore(), 3);
            playersScoresLB[i].setText(text);
            playersScoresLB[i].setForeground(findSnakeColor(player.getSnakeId(), player.getNumber()));
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
     * This method draws the gameboard according to the DTO representing the game engine
     * 
     * @param gameEngine
     *            The DTO representing the game engine
     */
    public void drawGameboard(final GameEngineDTO gameEngineDto) {
        final List<GameBoardRefreshDTO> refreshes = gameEngineDto.getGameBoardDto().getComponentsToRefresh();
        final Map<PlayerDTO, ComponentDTO> snakesMapping = gameEngineDto.getSnakesMapping();

        for (final GameBoardRefreshDTO refresh : refreshes) {
            final ComponentDTO componentDto = refresh.getComponentDto();
            final RefreshAction action = refresh.getAction();
            final IComponent component = gameBoardPL.getComponentById(componentDto.getId());

            switch (action) {
                case ADD:
                    final IComponent newComponent = generateComponent(snakesMapping, componentDto);
                    gameBoardPL.addComponent(newComponent);
                    break;
                case MOVE:
                    component.setXYPos(componentDto.getX(), componentDto.getY());
                    gameBoardPL.moveComponent(component);
                    for (final PlayerDTO player : snakesMapping.keySet()) {
                        if (player.getName().equals(client.getUsername()) && snakesMapping.get(player).getId().equals(component.getId())) {
                            SoundPlayer.play(SoundEffect.MOVE);
                            break;
                        }
                    }
                    break;
                case REMOVE:
                    gameBoardPL.removeComponent(component);
                    for (final PlayerDTO player : snakesMapping.keySet()) {
                        if (player.getName().equals(client.getUsername())) {
                            final ComponentDTO snake = snakesMapping.get(player);
                            if (snake.getX() == component.getXPos() && snake.getY() == component.getYPos()) {
                                SoundPlayer.play(SoundEffect.EAT);
                            }
                            break;
                        }
                    }
                    break;
                default:
                    log.warn("Unknown action {}", action);
                    break;
            }
        }
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
        centerPL.setOpaque(false);
        centerPL.setLayout(new GridBagLayout());
        centerPL.setOpaque(true);
        centerPL.setBackground(new Color(0, 180, 50, 150));
    }

    /**
     * This methods initializes the bottom panel
     */
    private void initBottomPL() {
        bottomPL = new JPanel();
        bottomPL.setLayout(new BorderLayout());
        bottomPL.setOpaque(false);
    }

    /**
     * This methods initializes the gameboard panel
     */
    private void initGameBoardPL() {
        gameBoardPL = new GameBoardPanel(GameConstants.GRID_SIZE, GameConstants.GRID_SIZE);
    }

    /**
     * This methods initializes the players' scores labels
     */
    private void initPlayersScoresLB() {
        playersScoresLB = new JLabel[GameConstants.MAX_NUMBER_OF_PLAYERS];
        for (int i = 0; i < playersScoresLB.length; i++) {
            playersScoresLB[i] = new JLabel(intToString(0, 3));
            playersScoresLB[i].setFont(new Font("sans-serif", Font.BOLD, 20));
            playersScoresLB[i].setForeground(new Color(0, 0, 0, 0));
        }
    }

    /**
     * This methods initializes the quit button
     */
    private void initQuitBTN() {
        quitBTN = new CustomButton("quit game");
        quitBTN.addActionListener(new QuitGameListener());
    }

    /**
     * This method generates the component represented by the specified component DTO
     * 
     * @param snakesMapping
     *            The players' snakes mapping
     * @param componentDto
     *            The DTO representing the component to generate
     * @return The component represented by the specified component DTO
     */
    private IComponent generateComponent(final Map<PlayerDTO, ComponentDTO> snakesMapping, final ComponentDTO componentDto) {
        switch (componentDto.getType()) {
            case SNAKE:
                return new Snake(componentDto.getId(), componentDto.getX(), componentDto.getY(), findSnakeIconPath(snakesMapping, componentDto.getId()));
            case SWEET:
                return new Sweet(componentDto.getId(), componentDto.getX(), componentDto.getY());
            default:
                log.warn("Unknown component type {}", componentDto.getType());
                return null;
        }
    }

    /**
     * This method finds the snake icon path according to the specified snake id
     * 
     * @param snakesMapping
     *            The players' snakes mapping
     * @param snakeId
     *            The snake id
     * @return The snake icon path according to the specified snake id
     */
    private String findSnakeIconPath(final Map<PlayerDTO, ComponentDTO> snakesMapping, final String snakeId) {
        for (final PlayerDTO playerDto : snakesMapping.keySet()) {
            if (snakesMapping.get(playerDto).getId().equals(snakeId)) {
                switch (playerDto.getNumber()) {
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
        final int opacity = snakeId == null ? 0 : 255;
        switch (i) {
            case 2:
                return new Color(255, 0, 0, opacity);
            case 3:
                return new Color(12, 12, 235, opacity);
            case 4:
                return new Color(0, 0, 0, opacity);
            default:
                return new Color(39, 109, 31, opacity);
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
            // unsupported
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
         */
        @Override
        public void keyTyped(final KeyEvent e) {
            // unsupported
        }

    }

}
