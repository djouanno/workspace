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
import com.esir.sr.sweetsnake.component.ImagePanel;
import com.esir.sr.sweetsnake.component.Snake;
import com.esir.sr.sweetsnake.component.Sweet;
import com.esir.sr.sweetsnake.constants.GuiConstants;
import com.esir.sr.sweetsnake.dto.ComponentDTO;
import com.esir.sr.sweetsnake.dto.GameBoardDTO;
import com.esir.sr.sweetsnake.enumeration.MoveDirection;
import com.esir.sr.sweetsnake.enumeration.RefreshAction;
import com.esir.sr.sweetsnake.utils.Pair;

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
    private static final long   serialVersionUID = 6919247419837806743L;

    /** The logger */
    private static final Logger log              = LoggerFactory.getLogger(GameView.class);

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    /** The game board DTO */
    private GameBoardDTO        gameBoardDto;

    /** The view title panel */
    private ImagePanel          gameOnIPL;

    /** The top panel */
    private JPanel              topPL;

    /** The center panel */
    private JPanel              centerPL;

    /** The game board panel */
    private GameBoardPanel      gameBoardPL;

    /** The bottom panel */
    private JPanel              bottomPL;

    /** The first player score label */
    private JLabel              player1ScoreLB;

    /** The second player score label */
    private JLabel              player2ScoreLB;

    /** The leave button */
    private JButton             leaveBTN;

    /** Is the the current player player1 */
    private boolean             isFirstPlayer;

    /** The player1's snake id */
    private String              player1SnakeId;

    /** The player2's snake id */
    private String              player2SnakeId;

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
        player1SnakeId = "";
        player2SnakeId = "";
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

        initGameOnIPL();
        topPL.add(gameOnIPL);

        // center panel
        initCenterPL();
        add(centerPL, BorderLayout.CENTER);

        initGameBoardPL();

        centerPL.add(gameBoardPL);

        // bottom panel
        initBottomPL();
        add(bottomPL, BorderLayout.SOUTH);

        final GridBagConstraints gbc = new GridBagConstraints();

        initPlayer1ScoreLB();
        initPlayer2ScoreLB();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 0, 0, 80);
        bottomPL.add(isFirstPlayer ? player1ScoreLB : player2ScoreLB, gbc);

        initLeaveBTN();
        gbc.gridx = 1;
        gbc.insets = new Insets(5, 0, 0, 0);
        bottomPL.add(leaveBTN, gbc);

        gbc.gridx = 2;
        gbc.insets = new Insets(5, 80, 0, 0);
        bottomPL.add(isFirstPlayer ? player2ScoreLB : player1ScoreLB, gbc);

        addKeyListener(new KeyboardListener());
        setFocusable(true);
        requestFocusInWindow();
    }

    /**
     * 
     * @param player1Score
     * @param player2Score
     */
    public void refreshScores(final int player1Score, final int player2Score) {
        player1ScoreLB.setText(intToString(player1Score, 3));
        player2ScoreLB.setText(intToString(player2Score, 3));
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
     * @param _isFirstPlayer
     */
    public void setFirstPlayer(final boolean _isFirstPlayer) {
        isFirstPlayer = _isFirstPlayer;
    }

    /**
     * 
     * @param _player1SnakeId
     */
    public void setPlayer1SnakeId(final String _player1SnakeId) {
        player1SnakeId = _player1SnakeId;
    }

    /**
     * 
     * @param _player2SnakeId
     */
    public void setPlayer2SnakeId(final String _player2SnakeId) {
        player2SnakeId = _player2SnakeId;
    }

    /**********************************************************************************************
     * [BLOCK] PRIVATE METHODS
     **********************************************************************************************/

    /**
     * 
     */
    private void initTopPL() {
        topPL = new JPanel();
        topPL.setOpaque(false);
    }

    /**
     * 
     */
    private void initGameOnIPL() {
        gameOnIPL = new ImagePanel(GuiConstants.GAME_ON_PATH);
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
    private void initGameBoardPL() {
        if (gameBoardDto != null) {
            gameBoardPL = new GameBoardPanel(gameBoardDto.getWidth(), gameBoardDto.getHeight(), isFirstPlayer);
            gameBoardPL.setBorder(new EmptyBorder(0, 0, 10, 0));
            drawGameboard();
        }
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
    private void initPlayer1ScoreLB() {
        player1ScoreLB = new JLabel(intToString(0, 3));
        player1ScoreLB.setForeground(isFirstPlayer ? new Color(39, 109, 31) : Color.red);
        player1ScoreLB.setFont(new Font("sans-serif", Font.BOLD, 20));
    }

    /**
     * 
     */
    private void initPlayer2ScoreLB() {
        player2ScoreLB = new JLabel(intToString(0, 3));
        player2ScoreLB.setForeground(isFirstPlayer ? Color.red : new Color(39, 109, 31));
        player2ScoreLB.setFont(new Font("sans-serif", Font.BOLD, 20));
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
        final List<Pair<ComponentDTO, RefreshAction>> componentsToRefresh = gameBoardDto.getComponentsToRefresh();
        for (final Pair<ComponentDTO, RefreshAction> pair : componentsToRefresh) {
            final ComponentDTO componentDto = pair.getFirst();
            final RefreshAction action = pair.getSecond();
            final IComponent component = gameBoardPL.getComponentById(componentDto.getId());

            if (component == null) {
                IComponent newComponent = null;
                final int x = componentDto.getX(), y = componentDto.getY();
                switch (componentDto.getType()) {
                    case SNAKE:
                        String snakeColor = GuiConstants.GREEN_SNAKE_VALUE;
                        if (isFirstPlayer && componentDto.getId().equals(player2SnakeId) || !isFirstPlayer && componentDto.getId().equals(player1SnakeId)) {
                            snakeColor = GuiConstants.RED_SNAKE_VALUE;
                        }
                        newComponent = new Snake(componentDto.getId(), x, y, snakeColor);
                        break;
                    case SWEET:
                        newComponent = new Sweet(componentDto.getId(), x, y);
                        break;
                    default:
                        break;
                }
                gameBoardPL.setComponent(newComponent);
            } else {
                switch (action) {
                    case REMOVE:
                        gameBoardPL.removeComponent(component);
                        break;
                    case SET: // TODO never used, component always removed so uses the first if condition
                        gameBoardPL.setComponent(component);
                        break;
                    default:
                        break;
                }
            }
        }
        gui.refreshUI();
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
            client.leaveGame();
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
            if (isFirstPlayer) {
                moveTable.put(KeyEvent.VK_DOWN, MoveDirection.DOWN);
                moveTable.put(KeyEvent.VK_UP, MoveDirection.UP);
                moveTable.put(KeyEvent.VK_LEFT, MoveDirection.LEFT);
                moveTable.put(KeyEvent.VK_RIGHT, MoveDirection.RIGHT);
            } else {
                moveTable.put(KeyEvent.VK_DOWN, MoveDirection.UP);
                moveTable.put(KeyEvent.VK_UP, MoveDirection.DOWN);
                moveTable.put(KeyEvent.VK_LEFT, MoveDirection.RIGHT);
                moveTable.put(KeyEvent.VK_RIGHT, MoveDirection.LEFT);
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
