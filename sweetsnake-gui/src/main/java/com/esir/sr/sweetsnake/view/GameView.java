package com.esir.sr.sweetsnake.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.swing.JButton;
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

    /** The leave button */
    private JButton             leaveBTN;

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

        initLeaveBTN();
        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 0, 0, 0);
        bottomPL.add(leaveBTN, gbc);

        addKeyListener(new KeyboardListener());
        setFocusable(true);
        requestFocusInWindow();
    }

    /**
     * 
     * @param _gameBoardDto
     */
    public void setGameBoardDto(final GameBoardDTO _gameBoardDto) {
        gameBoardDto = _gameBoardDto;
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
            gameBoardPL = new GameBoardPanel(gameBoardDto.getWidth(), gameBoardDto.getHeight());
            gameBoardPL.setBorder(new EmptyBorder(0, 0, 10, 0));
            for (int x = 0; x < gameBoardDto.getWidth(); x++) {
                for (int y = 0; y < gameBoardDto.getHeight(); y++) {
                    final ComponentDTO elementDto = gameBoardDto.getElement(x, y);
                    if (elementDto != null) {
                        IComponent element = null;
                        switch (elementDto.getType()) {
                            case SNAKE:
                                element = new Snake(elementDto.getId(), x, y);
                                break;
                            case SWEET:
                                element = new Sweet(elementDto.getId(), x, y);
                                break;
                            default:
                                break;
                        }
                        gameBoardPL.setElement(element);
                    }
                }
            }
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
    private void initLeaveBTN() {
        leaveBTN = new JButton("leave game");
        leaveBTN.addActionListener(new LeaveGameListener());
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
