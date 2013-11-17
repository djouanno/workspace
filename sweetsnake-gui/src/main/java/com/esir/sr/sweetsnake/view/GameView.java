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
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.esir.sr.sweetsnake.api.IComponent;
import com.esir.sr.sweetsnake.component.GameBoardPanel;
import com.esir.sr.sweetsnake.component.Snake;
import com.esir.sr.sweetsnake.component.Sweet;
import com.esir.sr.sweetsnake.constants.GameConstants;
import com.esir.sr.sweetsnake.constants.GuiConstants;
import com.esir.sr.sweetsnake.dto.ComponentDTO;
import com.esir.sr.sweetsnake.dto.GameBoardDTO;
import com.esir.sr.sweetsnake.dto.GameBoardRefreshDTO;
import com.esir.sr.sweetsnake.dto.PlayerDTO;
import com.esir.sr.sweetsnake.enumeration.MoveDirection;
import com.esir.sr.sweetsnake.enumeration.RefreshAction;

/**
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
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

    /** The leave button */
    private JButton              leaveBTN;

    /** The current player's number */
    private int                  playerNb;

    /** The players' numbers & snakes map */
    private Map<Integer, String> playersSnakes;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR & INIT
     **********************************************************************************************/

    /**
     * 
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
        log.info("Initializing a new SweetSnakeGameView");
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
    public void buildImpl() {
        setLayout(new BorderLayout());

        // top panel
        initTopPL();
        add(topPL, BorderLayout.NORTH);

        initPlayersScoresLB();
        final GridBagConstraints gbc = new GridBagConstraints();

        final int[][] positions = { { 0, 3, 2, 1 }, { 1, 2, 3, 0 }, { 2, 0, 1, 3 }, { 3, 1, 0, 2 } };

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 5, 170);
        topPL.add(playersScoresLB[positions[playerNb - 1][0]], gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(0, 170, 5, 0);
        topPL.add(playersScoresLB[positions[playerNb - 1][1]], gbc);

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
        bottomPL.add(playersScoresLB[positions[playerNb - 1][2]], gbc);

        initLeaveBTN();
        gbc.gridx = 1;
        gbc.insets = new Insets(5, 0, 0, 0);
        bottomPL.add(leaveBTN, gbc);

        gbc.gridx = 2;
        gbc.insets = new Insets(5, 120, 0, 0);
        bottomPL.add(playersScoresLB[positions[playerNb - 1][3]], gbc);

        addKeyListener(new KeyboardListener());
        setFocusable(true);
        requestFocusInWindow();
    }

    /**
     * 
     * @param playersScores
     */
    public void refreshScores(final List<PlayerDTO> players) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                for (final PlayerDTO player : players) {
                    playersScoresLB[player.getNumber() - 1].setText(intToString(player.getScore(), 3));
                }
            }
        });
    }

    public void hideScore(final int _playerNb) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                playersScoresLB[_playerNb - 1].setForeground(findSnakeColor(null, _playerNb));
            }
        });
    }

    /**
     * 
     * @param _gameBoardDto
     */
    public void setGameboardDto(final GameBoardDTO _gameBoardDto) {
        gameBoardDto = _gameBoardDto;
    }

    /**
     * 
     * @param _playerNb
     */
    public void setPlayerNb(final int _playerNb) {
        playerNb = _playerNb;
    }

    /**
     * 
     * @param _playersSnakes
     */
    public void setPlayersSnakesMap(final Map<Integer, String> _playersSnakes) {
        playersSnakes = _playersSnakes;
    }

    /**********************************************************************************************
     * [BLOCK] PRIVATE METHODS
     **********************************************************************************************/

    /**
     * 
     */
    private void initTopPL() {
        topPL = new JPanel();
        topPL.setLayout(new GridBagLayout());
        topPL.setOpaque(false);
    }

    /**
     * 
     */
    private void initCenterPL() {
        centerPL = new JPanel();
        centerPL.setOpaque(true);
        centerPL.setBackground(Color.cyan);
        centerPL.setLayout(new GridBagLayout());
    }

    /**
     * 
     */
    private void initBottomPL() {
        bottomPL = new JPanel();
        bottomPL.setLayout(new GridBagLayout());
        bottomPL.setOpaque(false);
    }

    /**
     * 
     */
    private void initGameBoardPL() {
        if (gameBoardDto != null) {
            gameBoardPL = new GameBoardPanel(gameBoardDto.getWidth(), gameBoardDto.getHeight(), playerNb);
            gameBoardPL.setBorder(new EmptyBorder(0, 0, 10, 0));
            drawGameboard();
        }
    }

    /**
     * 
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
     * 
     */
    private void initLeaveBTN() {
        leaveBTN = new JButton("leave game");
        leaveBTN.addActionListener(new LeaveGameListener());
    }

    /**
     * 
     */
    public void drawGameboard() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
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
                gui.refreshUI();
            }
        });
    }

    /**
     * 
     * @param snakeId
     * @return
     */
    private String findSnakeIconPath(final String snakeId) {
        log.debug("Looking for snake {}", snakeId);
        for (final int player : playersSnakes.keySet()) {
            log.debug("Found snake with id {}", playersSnakes.get(player));
            if (playersSnakes.get(player).equals(snakeId)) {
                log.debug("{} equals {}", playersSnakes.get(player), snakeId);
                switch (player) {
                    case 2:
                        return GuiConstants.RED_SNAKE_ICON_PATH;
                    case 3:
                        return GuiConstants.BLUE_SNAKE_ICON_PATH;
                    case 4:
                        return GuiConstants.BLACK_SNAKE_ICON_PATH;
                    default:
                        return GuiConstants.GREEN_SNAKE_ICON_PATH;
                }
            }
        }
        return GuiConstants.GREEN_SNAKE_ICON_PATH;
    }

    /**
     * 
     * @param snakeId
     * @param i
     * @return
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
     * 
     * @param num
     * @param digits
     * @return
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
     * 
     * @author Herminaël Rougier
     * @author Damien Jouanno
     * 
     */
    private class LeaveGameListener implements ActionListener
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
     * 
     * @author Herminaël Rougier
     * @author Damien Jouanno
     * 
     */
    private class KeyboardListener implements KeyListener
    {

        private final Map<Integer, MoveDirection> moveTable;

        public KeyboardListener() {
            moveTable = new LinkedHashMap<Integer, MoveDirection>();
            switch (playerNb) {
                case 2:
                    moveTable.put(KeyEvent.VK_DOWN, MoveDirection.UP);
                    moveTable.put(KeyEvent.VK_UP, MoveDirection.DOWN);
                    moveTable.put(KeyEvent.VK_LEFT, MoveDirection.RIGHT);
                    moveTable.put(KeyEvent.VK_RIGHT, MoveDirection.LEFT);
                    break;
                case 3:
                    moveTable.put(KeyEvent.VK_DOWN, MoveDirection.RIGHT);
                    moveTable.put(KeyEvent.VK_UP, MoveDirection.LEFT);
                    moveTable.put(KeyEvent.VK_LEFT, MoveDirection.DOWN);
                    moveTable.put(KeyEvent.VK_RIGHT, MoveDirection.UP);
                    break;
                case 4:
                    moveTable.put(KeyEvent.VK_DOWN, MoveDirection.LEFT);
                    moveTable.put(KeyEvent.VK_UP, MoveDirection.RIGHT);
                    moveTable.put(KeyEvent.VK_LEFT, MoveDirection.UP);
                    moveTable.put(KeyEvent.VK_RIGHT, MoveDirection.DOWN);
                    break;
                default:
                    moveTable.put(KeyEvent.VK_DOWN, MoveDirection.DOWN);
                    moveTable.put(KeyEvent.VK_UP, MoveDirection.UP);
                    moveTable.put(KeyEvent.VK_LEFT, MoveDirection.LEFT);
                    moveTable.put(KeyEvent.VK_RIGHT, MoveDirection.RIGHT);
                    break;
            }
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
         */
        @Override
        public void keyPressed(final KeyEvent e) {
            final int keyCode = e.getKeyCode();
            log.debug("Key pressed with code {}", keyCode);
            if (moveTable.containsKey(keyCode)) {
                gui.moveSnake(moveTable.get(keyCode));
            }
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
         */
        @Override
        public void keyReleased(final KeyEvent e) {
            // TODO Auto-generated method stub
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
         */
        @Override
        public void keyTyped(final KeyEvent e) {
            // TODO Auto-generated method stub
        }

    }

}
