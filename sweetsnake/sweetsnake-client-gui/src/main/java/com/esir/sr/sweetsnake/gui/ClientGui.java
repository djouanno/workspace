package com.esir.sr.sweetsnake.gui;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.esir.sr.sweetsnake.api.IClientForGui;
import com.esir.sr.sweetsnake.api.IGuiForClient;
import com.esir.sr.sweetsnake.component.ImagePanel;
import com.esir.sr.sweetsnake.component.Toast;
import com.esir.sr.sweetsnake.constants.ClientGuiConstants;
import com.esir.sr.sweetsnake.dto.GameBoardDTO;
import com.esir.sr.sweetsnake.dto.GameRequestDTO;
import com.esir.sr.sweetsnake.dto.GameSessionDTO;
import com.esir.sr.sweetsnake.dto.PlayerDTO;
import com.esir.sr.sweetsnake.view.AbstractView;
import com.esir.sr.sweetsnake.view.ConnectionView;
import com.esir.sr.sweetsnake.view.GameView;
import com.esir.sr.sweetsnake.view.LobbyView;
import com.esir.sr.sweetsnake.view.PlayersView;
import com.esir.sr.sweetsnake.view.ReachingServerView;
import com.esir.sr.sweetsnake.view.SessionsView;
import com.esir.sr.sweetsnake.view.UnreachableServerView;

/**
 * This class implements the IGuiForClient interface.<br />
 * It extends the JFrame class to provide the main frame GUI.
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 * @see com.esir.sr.sweetsnake.api.IGuiForClient
 * @see javax.swing.JFrame
 */
@Component
public class ClientGui extends JFrame implements IGuiForClient
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    /** The serial version UID */
    private static final long     serialVersionUID = -4189434181017519666L;

    /** The logger */
    private static final Logger   log              = LoggerFactory.getLogger(ClientGui.class);

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    /** The client */
    @Autowired(required = false)
    private IClientForGui         client;

    /** The reaching server view */
    @Autowired
    private ReachingServerView    reachingServerView;

    /** The unreachable server view */
    @Autowired
    private UnreachableServerView unreachableServerView;

    /** The connection view */
    @Autowired
    private ConnectionView        connectionView;

    /** The sessions view */
    @Autowired
    private SessionsView          sessionsView;

    /** The players view */
    @Autowired
    private PlayersView           playersView;

    /** The lobby view */
    @Autowired
    private LobbyView             lobbyView;

    /** The game view */
    @Autowired
    private GameView              gameView;

    /** The gui current view */
    private AbstractView          currentView;

    /** The GUI dimension */
    private Dimension             dimension;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR & INIT
     **********************************************************************************************/

    /**
     * Creates a new client GUI
     */
    protected ClientGui() {
        super();
    }

    /**
     * Initializes a new client GUI
     */
    @PostConstruct
    protected void init() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                log.info("Initializing the Client GUI");
                initFrameParameters();
                playersView.build();
                sessionsView.build();
            }
        });
    }

    /**
     * Called before destroying the client GUI
     */
    @PreDestroy
    protected void destroy() {
        log.info("Destroying the Client GUI");
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC IMPLEMENTED METHODS
     **********************************************************************************************/

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IGuiForClient#reachingServer()
     */
    @Override
    public void reachingServer() {
        if (!currentView.equals(reachingServerView)) {
            switchView(reachingServerView, true);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeIhm#serverReachable()
     */
    @Override
    public void serverReachable() {
        if (!currentView.equals(connectionView)) {
            switchView(connectionView, true);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeIhm#serverNotReachable()
     */
    @Override
    public void serverNotReachable() {
        if (!currentView.equals(unreachableServerView)) {
            switchView(unreachableServerView, true);
        } else {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    displayErrorMessage("server is not reachable");
                }
            });
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IClientGui#disconnectedFromServer()
     */
    @Override
    public void disconnectedFromServer() {
        if (!currentView.equals(connectionView)) {
            switchView(connectionView, true);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeIhm#successfullyConnected()
     */
    @Override
    public void connectedToServer() {
        switchView(sessionsView, true);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IGui#refreshPlayersList(java.util.List)
     */
    @Override
    public void refreshPlayersList(final List<PlayerDTO> playersList) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                playersView.refreshPlayersList(playersList);
                refreshUI();
            }
        });
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IGui#refreshSessionsList(java.util.List)
     */
    @Override
    public void refreshSessionsList(final List<GameSessionDTO> sessionsList) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                sessionsView.refreshSessionsList(sessionsList);
                refreshUI();
            }
        });
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IGui#gameRequested(com.esir.sr.sweetsnake.dto.GameRequestDTO)
     */
    @Override
    public int requestReceived(final GameRequestDTO request) {
        return displayCustomQuestion("Someone wants to play with you", request.getRequestingPlayerDto().getName() + " wants to play with you", new String[] { "accept", "deny" });
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IGui#requestSent(com.esir.sr.sweetsnake.dto.GameRequestDTO)
     */
    @Override
    public void requestSent(final GameRequestDTO request) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                displayToast("Your request has been sent to " + request.getRequestedPlayerDto().getName());
            }
        });
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IGui#requestRefused(com.esir.sr.sweetsnake.dto.GameRequestDTO)
     */
    @Override
    public void requestDenied(final GameRequestDTO request) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                displayToast(request.getRequestedPlayerDto().getName() + " has denied your request");
            }
        });
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IGuiForClient#sessionJoined(com.esir.sr.sweetsnake.dto.GameSessionDTO, int)
     */
    @Override
    public void sessionJoined(final GameSessionDTO session, final int playerNb) {
        if (!currentView.equals(lobbyView) && !currentView.equals(gameView)) {
            switchView(lobbyView, true);
        }
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                lobbyView.setPlayerNb(playerNb);
                lobbyView.setPlayers(session.getPlayersDto());
                lobbyView.refreshPlayers();
                lobbyView.refreshButtons(session.isStarted());
                refreshUI();
            }
        });
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IGuiForClient#sessionStarted(com.esir.sr.sweetsnake.dto.GameSessionDTO)
     */
    @Override
    public void sessionStarted(final GameSessionDTO session) {
        final Map<Integer, String> playersSnakes = new LinkedHashMap<Integer, String>();
        for (final PlayerDTO player : session.getPlayersDto()) {
            playersSnakes.put(player.getNumber(), player.getSnakeId());
        }
        gameView.setPlayersSnakesMap(playersSnakes);
        gameView.setGameboardDto(session.getGameBoardDto());

        switchView(gameView, true);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                gameView.requestFocusInWindow();
            }
        });
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IGuiForClient#sessionLeft(com.esir.sr.sweetsnake.dto.GameSessionDTO,
     * com.esir.sr.sweetsnake.dto.PlayerDTO, boolean, boolean)
     */
    @Override
    public void sessionLeft(final GameSessionDTO session, final PlayerDTO leaver, final boolean stopped, final boolean finished) {
        if (stopped && !finished) {
            if (leaver.getName().equals(client.getUsername())) {
                switchView(sessionsView, true);
            } else {
                switchView(lobbyView, false);
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        refreshLobbyView(session.getPlayersDto(), session.isStarted());
                        refreshUI();
                    }
                });
            }
        } else if (finished) {
            switchView(sessionsView, true);
        } else {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    displayToast(leaver + " has left the game :(");
                    if (currentView.equals(gameView)) {
                        gameView.hideScore(leaver.getNumber());
                    } else if (currentView.equals(lobbyView)) {
                        refreshLobbyView(session.getPlayersDto(), session.isStarted());
                    }
                    refreshUI();
                }
            });
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IGuiForClient#sessionFinished(com.esir.sr.sweetsnake.dto.GameSessionDTO)
     */
    @Override
    public void sessionFinished(final GameSessionDTO session) {
        switchView(lobbyView, true);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                lobbyView.setPlayers(session.getPlayersDto());
                lobbyView.refreshPlayers();
                lobbyView.refreshButtons(session.isStarted());
                refreshUI();
            }
        });
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IGui#refreshGameboard(com.esir.sr.sweetsnake.dto.GameBoardDTO)
     */
    @Override
    public void refreshGameboard(final GameBoardDTO gameBoard) {
        if (currentView.equals(gameView)) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    gameView.setGameboardDto(gameBoard);
                    gameView.drawGameboard();
                    refreshUI();
                }
            });
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IGui#refreshScores(java.util.Map)
     */
    @Override
    public void refreshScores(final List<PlayerDTO> players) {
        if (currentView.equals(gameView)) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    gameView.refreshScores(players);
                    refreshUI();
                }
            });
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeIhm#displayErrorMessage(java.lang.String)
     */
    @Override
    public void displayErrorMessage(final String message) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                JOptionPane.showMessageDialog(ClientGui.this, message, "Error", JOptionPane.ERROR_MESSAGE);
            }
        }).start();
    }

    /**********************************************************************************************
     * [BLOCK] GETTERS
     **********************************************************************************************/

    /**
     * This method returns the current dimension of the GUI
     * 
     * @return The dimension of the GUI
     */
    public Dimension getDimension() {
        return dimension;
    }

    /**********************************************************************************************
     * [BLOCK] PRIVATE METHODS
     **********************************************************************************************/

    /**
     * This method initializes the frame parameters
     */
    private void initFrameParameters() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("SweetSnake");

        final Image icon = Toolkit.getDefaultToolkit().getImage(ClientGui.class.getResource(ClientGuiConstants.ICON_PATH));
        setIconImage(icon);

        dimension = new Dimension(ClientGuiConstants.GUI_WIDTH, ClientGuiConstants.GUI_HEIGHT);
        setSize(dimension);
        setPreferredSize(dimension);

        final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        final int X = screen.width / 2 - dimension.width / 2;
        final int Y = screen.height / 2 - dimension.height / 2;
        setBounds(X, Y, dimension.width, dimension.height);
        setContentPane(new ImagePanel(ClientGuiConstants.BG_PATH));

        pack();

        setVisible(true);
    }

    /**
     * This method switch the current view displayed in the frame
     * 
     * @param view
     *            The new view to display
     * @param unbuildPrevious
     *            True to unbuild the previous view, false otherwise
     */
    private void switchView(final AbstractView view, final boolean unbuildPrevious) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                if (currentView != null && unbuildPrevious) {
                    currentView.unbuild();
                }
                view.build();
                getContentPane().removeAll();
                getContentPane().add(view);
                refreshUI();
                currentView = view;
                log.debug("View switched to {}", view.getClass().getSimpleName());
            }
        });
    }

    /**
     * This method refreshes the GUI
     */
    private void refreshUI() {
        revalidate();
        repaint();
    }

    /**
     * This method retrieves the parent option pane of a component
     * 
     * @param parent
     *            The first parent of the component
     * @return The parent option pane if it is found, false otherwise
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
     * This method displays a toast on the GUI
     * 
     * @param message
     *            The message to display
     */
    private void displayToast(final String message) {
        Toast.displayToast(this, message);
    }

    /**
     * This method shows a multiple choice dialog with the specified buttons
     * 
     * @param title
     *            The title of the dialog
     * @param message
     *            The question to display
     * @param buttonsText
     *            The buttons choice
     * @return An integer representing the clicked button (starting by 0 from the left)
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

    /**
     * This method refreshes the lobby view
     * 
     * @param players
     *            The list of the players to display
     * @param isStarted
     *            True if the game session is started, false otherwise
     */
    private void refreshLobbyView(final List<PlayerDTO> players, final boolean isStarted) {
        lobbyView.setPlayers(players);
        lobbyView.refreshPlayers();
        for (final PlayerDTO player : players) {
            if (player.getName().equals(client.getUsername())) {
                lobbyView.setPlayerNb(player.getNumber());
            }
        }
        lobbyView.refreshButtons(isStarted);
    }

}
