package com.esir.sr.sweetsnake.gui;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.esir.sr.sweetsnake.api.IClient;
import com.esir.sr.sweetsnake.api.IGui;
import com.esir.sr.sweetsnake.component.ImagePanel;
import com.esir.sr.sweetsnake.component.Toast;
import com.esir.sr.sweetsnake.constants.GuiConstants;
import com.esir.sr.sweetsnake.dto.GameBoardDTO;
import com.esir.sr.sweetsnake.dto.GameRequestDTO;
import com.esir.sr.sweetsnake.dto.PlayerDTO;
import com.esir.sr.sweetsnake.enumeration.MoveDirection;
import com.esir.sr.sweetsnake.view.AbstractView;
import com.esir.sr.sweetsnake.view.ConnectionView;
import com.esir.sr.sweetsnake.view.GameView;
import com.esir.sr.sweetsnake.view.PlayersView;
import com.esir.sr.sweetsnake.view.UnreachableServerView;

/**
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
@Component
public class Gui extends JFrame implements IGui
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    /** The serial version UID */
    private static final long     serialVersionUID = -4189434181017519666L;

    /** The logger */
    private static final Logger   log              = LoggerFactory.getLogger(Gui.class);

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    /** The client */
    @Autowired
    private IClient               client;

    /** The unreachable server view */
    @Autowired
    private UnreachableServerView UnreachableServerView;

    /** The connection view */
    @Autowired
    private ConnectionView        connectionView;

    /** The players view */
    @Autowired
    private PlayersView           playersView;

    /** The game view */
    @Autowired
    private GameView              gameView;

    /** The gui current view */
    private AbstractView          currentView;

    /** The menu bar */
    private JMenuBar              menuBar;

    /** The server menu */
    private JMenu                 serverMenu;

    /** The disonnect menu item */
    private JMenuItem             disconnectMenuItem;

    /** The GUI dimension */
    private Dimension             dimension;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR & INIT
     **********************************************************************************************/

    /**
     * 
     */
    protected Gui() {
        super();
    }

    /**
     * 
     */
    @PostConstruct
    protected void init() {
        log.info("Initializing a new SweetSnakeIhm");
        initFrameParameters();
        initMenuBar();
    }

    /**
     * 
     */
    @PreDestroy
    protected void destroy() {
        log.info("Destroying SweetSnakeIhm");
    }

    /**********************************************************************************************
     * [BLOCK] PRIVATE METHODS
     **********************************************************************************************/

    /**
     * 
     */
    private void initFrameParameters() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("SweetSnake");
        dimension = new Dimension(GuiConstants.GUI_WIDTH, GuiConstants.GUI_HEIGHT);
        setSize(dimension);
        setPreferredSize(dimension);

        final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        final int X = screen.width / 2 - dimension.width / 2;
        final int Y = screen.height / 2 - dimension.height / 2;
        setBounds(X, Y, dimension.width, dimension.height);
        setContentPane(new ImagePanel(GuiConstants.BG_PATH));

        pack();

        setVisible(true);
    }

    /**
     * 
     */
    private void initMenuBar() {
        menuBar = new JMenuBar();
        menuBar.setOpaque(false);

        serverMenu = new JMenu("server");
        disconnectMenuItem = new JMenuItem("disconnect");
        serverMenu.add(disconnectMenuItem);
        disconnectMenuItem.addActionListener(null /* TODO */);
        menuBar.add(serverMenu);

        setJMenuBar(menuBar);
    }

    /**
     * 
     * @param view
     */
    private void switchView(final AbstractView view) {
        initMenuBar();
        view.build();
        if (view.getMenu() != null) {
            menuBar.add(view.getMenu());
        }
        getContentPane().removeAll();
        getContentPane().add(view);
        refreshUI();
        currentView = view;
        log.debug("View switched to {}", view.getClass().getName());
    }

    /**
     * 
     * @param parent
     * @return
     */
    private JOptionPane getOptionPane(final JComponent parent) {
        JOptionPane pane = null;
        if (!(parent instanceof JOptionPane)) {
            pane = getOptionPane((JComponent) parent.getParent());
        } else {
            pane = (JOptionPane) parent;
        }
        return pane;
    }

    /**
     * 
     * @param message
     */
    private void displayInfoMessage(final String title, final String message) {
        // showMessageDialog is a blocking method while the user has not closed the dialog so we have to launch it in
        // a new thread
        final Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                JOptionPane.showMessageDialog(Gui.this, message, title, JOptionPane.INFORMATION_MESSAGE);
            }
        });
        t.start();
    }

    /**
     * 
     * @param message
     */
    private void displayToast(final String message) {
        Toast.displayToast(this, message);
    }

    /**
     * 
     * @param message
     * @param buttonsText
     * @return
     */
    private int displayCustomQuestion(final String title, final String message, final String[] buttonsText) {
        final Object[] buttons = new Object[buttonsText.length];
        int i = 0;
        for (final String buttonText : buttonsText) {
            final JButton button = new JButton(buttonText);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(final ActionEvent e) {
                    final JOptionPane pane = getOptionPane((JComponent) e.getSource());
                    pane.setValue(button);
                }
            });
            buttons[i] = button;
            i++;
        }
        return JOptionPane.showOptionDialog(this, message, title, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, buttons, buttons[0]);
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC IMPLEMENTED METHODS
     **********************************************************************************************/

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeIhm#serverReachable()
     */
    @Override
    public void serverReachable() {
        switchView(connectionView);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeIhm#serverNotReachable()
     */
    @Override
    public void serverNotReachable() {
        switchView(UnreachableServerView);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeIhm#successfullyConnected()
     */
    @Override
    public void connectedToServer() {
        switchView(playersView);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IGui#refreshPlayersList(java.util.List)
     */
    @Override
    public void refreshPlayersList(final List<PlayerDTO> playersList) {
        if (currentView == playersView) {
            log.debug("Refreshing players list with {} players", playersList.size());
            playersView.refreshPlayersList(playersList);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IGui#requestAlreadyPending(com.esir.sr.sweetsnake.dto.GameRequestDTO)
     */
    @Override
    public int requestAlreadyPending(final GameRequestDTO request) {
        return displayCustomQuestion("A request is already pending", "Your already asked for a game with " + request.getRequestedPlayerDto().getName() + "\nPlease wait for your opponent to respond or cancel the request", new String[] { "wait", "cancel request" });
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IGui#gameRequested(com.esir.sr.sweetsnake.dto.GameRequestDTO)
     */
    @Override
    public int gameRequested(final GameRequestDTO request) {
        return displayCustomQuestion("Someone wants to play with you", request.getRequestingPlayerDto().getName() + " wants to play with you", new String[] { "accept", "deny" });
    }

    /*
     * 
     */
    @Override
    public void requestSent(final GameRequestDTO request) {
        displayToast("Your request has been sent to " + request.getRequestedPlayerDto().getName());
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IGui#requestRefused(com.esir.sr.sweetsnake.dto.GameRequestDTO)
     */
    @Override
    public void requestRefused(final GameRequestDTO request) {
        displayInfoMessage("Request denied", request.getRequestedPlayerDto().getName() + " has denied your request");
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IGui#gameStarted(boolean, java.util.Map, com.esir.sr.sweetsnake.dto.GameBoardDTO)
     */
    @Override
    public void gameStarted(final int playerNb, final Map<Integer, String> playersSnakes, final GameBoardDTO gameBoard) {
        gameView.setPlayerNb(playerNb);
        gameView.setPlayersSnakesMap(playersSnakes);
        gameView.setGameboardDto(gameBoard);
        switchView(gameView);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IGui#gameLeaved(com.esir.sr.sweetsnake.dto.PlayerDTO)
     */
    @Override
    public void gameLeaved(final String leaver) {
        switchView(playersView);
        if (leaver != null) {
            displayInfoMessage("Game stopped", leaver + " has left the game :(");
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IGui#refreshGameboard(com.esir.sr.sweetsnake.dto.GameBoardDTO)
     */
    @Override
    public void refreshGameboard(final GameBoardDTO gameBoard) {
        gameView.setGameboardDto(gameBoard);
        gameView.drawGameboard();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IGui#refreshScores(java.util.Map)
     */
    @Override
    public void refreshScores(final Map<Integer, Integer> playersScores) {
        gameView.refreshScores(playersScores);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeIhm#displayErrorMessage(java.lang.String)
     */
    @Override
    public void displayErrorMessage(final String message) {
        // showMessageDialog is a blocking method while the user has not closed the dialog so we have to launch it in
        // a new thread
        final Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                JOptionPane.showMessageDialog(Gui.this, message, "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        t.start();
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    /**
     * 
     */
    public void reachServer() {
        client.reachServer();
    }

    /**
     * 
     */
    public void refreshUI() {
        revalidate();
        repaint();
    }

    /**
     * 
     * @param requestedPlayer
     */
    public void requestGame(final PlayerDTO requestedPlayer) {
        client.requestGame(requestedPlayer);
    }

    /**
     * 
     * @param direction
     */
    public void moveSnake(final MoveDirection direction) {
        client.moveSnake(direction);
    }

    /**********************************************************************************************
     * [BLOCK] GETTERS
     **********************************************************************************************/

    /**
     * 
     * @return
     */
    public Dimension getDimension() {
        return dimension;
    }

}
