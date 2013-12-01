package com.esir.sr.sweetsnake.gui;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedHashMap;
import java.util.LinkedList;
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
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.esir.sr.sweetsnake.api.IClientForGui;
import com.esir.sr.sweetsnake.api.IGuiForClient;
import com.esir.sr.sweetsnake.component.ImagePanel;
import com.esir.sr.sweetsnake.component.Toast;
import com.esir.sr.sweetsnake.constants.ClientGuiConstants;
import com.esir.sr.sweetsnake.constants.GameConstants;
import com.esir.sr.sweetsnake.dto.ComponentDTO;
import com.esir.sr.sweetsnake.dto.GameBoardDTO;
import com.esir.sr.sweetsnake.dto.GameBoardRefreshDTO;
import com.esir.sr.sweetsnake.dto.GameEngineDTO;
import com.esir.sr.sweetsnake.dto.GameRequestDTO;
import com.esir.sr.sweetsnake.dto.GameSessionDTO;
import com.esir.sr.sweetsnake.dto.PlayerDTO;
import com.esir.sr.sweetsnake.enumeration.ComponentType;
import com.esir.sr.sweetsnake.enumeration.PlayerStatus;
import com.esir.sr.sweetsnake.enumeration.RefreshAction;
import com.esir.sr.sweetsnake.enumeration.SoundEffect;
import com.esir.sr.sweetsnake.sound.SoundPlayer;
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

    public static void main(final String[] args) {
        @SuppressWarnings("resource")
        final ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath*:spring/sweetsnake-client-gui-context.xml");
        context.registerShutdownHook();
        final ClientGui gui = context.getBean(ClientGui.class);
        final List<PlayerDTO> players = new LinkedList<PlayerDTO>();
        players.add(new PlayerDTO("toto", PlayerStatus.PLAYING, "1", 1, 0));
        players.add(new PlayerDTO("titi", PlayerStatus.PLAYING, "2", 2, 0));
        // players.add(new PlayerDTO("tata", PlayerStatus.PLAYING, "3", 3, 0));
        // players.add(new PlayerDTO("tutu", PlayerStatus.PLAYING, "4", 4, 0));
        final List<GameBoardRefreshDTO> components = new LinkedList<GameBoardRefreshDTO>();
        components.add(new GameBoardRefreshDTO(new ComponentDTO("1", 0, 0, ComponentType.SNAKE), RefreshAction.ADD));
        components.add(new GameBoardRefreshDTO(new ComponentDTO("2", GameConstants.GRID_SIZE - 1, GameConstants.GRID_SIZE - 1, ComponentType.SNAKE), RefreshAction.ADD));
        // components.add(new GameBoardRefreshDTO(new ComponentDTO("3", 0, GameConstants.GRID_SIZE - 1, ComponentType.SNAKE),
        // RefreshAction.ADD));
        // components.add(new GameBoardRefreshDTO(new ComponentDTO("4", GameConstants.GRID_SIZE - 1, 0, ComponentType.SNAKE),
        // RefreshAction.ADD));
        components.add(new GameBoardRefreshDTO(new ComponentDTO("5", 10, 10, ComponentType.SWEET), RefreshAction.ADD));
        components.add(new GameBoardRefreshDTO(new ComponentDTO("6", 10, 20, ComponentType.SWEET), RefreshAction.ADD));
        components.add(new GameBoardRefreshDTO(new ComponentDTO("7", 11, 0, ComponentType.SWEET), RefreshAction.ADD));
        components.add(new GameBoardRefreshDTO(new ComponentDTO("8", 25, 17, ComponentType.SWEET), RefreshAction.ADD));
        final GameBoardDTO gameBoard = new GameBoardDTO(GameConstants.GRID_SIZE, GameConstants.GRID_SIZE, GameConstants.NUMBER_OF_SWEETS, components);
        final Map<PlayerDTO, ComponentDTO> snakesMapping = new LinkedHashMap<PlayerDTO, ComponentDTO>();
        snakesMapping.put(players.get(0), components.get(0).getComponentDto());
        snakesMapping.put(players.get(1), components.get(1).getComponentDto());
        // snakesMapping.put(players.get(2), components.get(2).getComponentDto());
        // snakesMapping.put(players.get(3), components.get(3).getComponentDto());
        final GameEngineDTO gameEngine = new GameEngineDTO(gameBoard, snakesMapping);
        final GameSessionDTO session = new GameSessionDTO("id", players, gameEngine, null, true, players.get(0));
        // gui.sessionStarted(session);
        gui.sessionJoined(session, 1);
        // gui.serverReachable();
        // gui.serverReachable();
    }

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
        currentView = reachingServerView;
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                log.info("Initializing the Client GUI");
                initFrameParameters();
                reachingServerView.build();
                unreachableServerView.build();
                connectionView.build();
                sessionsView.build();
                playersView.build();
                lobbyView.build();
                gameView.build();
                SoundPlayer.play(SoundEffect.AMBIANCE);
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
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                switchView(reachingServerView);
            }
        });
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeIhm#serverReachable()
     */
    @Override
    public void serverReachable() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                if (!currentView.equals(connectionView)) {
                    switchView(connectionView);
                }
            }
        });
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeIhm#serverNotReachable()
     */
    @Override
    public void serverNotReachable() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                if (!currentView.equals(unreachableServerView)) {
                    switchView(unreachableServerView);
                } else {
                    displayErrorMessage("server is not reachable");
                }
            }
        });
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IClientGui#disconnectedFromServer()
     */
    @Override
    public void disconnectedFromServer() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                if (!currentView.equals(connectionView)) {
                    switchView(connectionView);
                }
            }
        });
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.ISweetSnakeIhm#successfullyConnected()
     */
    @Override
    public void connectedToServer() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                switchView(sessionsView);
            }
        });
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
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                if (!currentView.equals(lobbyView) && !currentView.equals(gameView)) {
                    switchView(lobbyView);
                }
                refreshLobbyView(session, session.isStarted());
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
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                switchView(gameView);
                gameView.drawGameboard(session.getGameEngineDto());
                gameView.refreshScores(session.getPlayersDto());
                refreshUI();
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
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                if (stopped && !finished) {
                    if (leaver.getName().equals(client.getUsername())) {
                        switchView(sessionsView);
                    } else {
                        switchView(lobbyView);
                        refreshLobbyView(session, session.isStarted());
                        refreshUI();
                    }
                } else if (finished) {
                    switchView(sessionsView);
                } else {
                    displayToast(leaver + " has left the game :(");
                    if (currentView.equals(gameView)) {
                        gameView.hideScore(leaver.getNumber());
                    } else if (currentView.equals(lobbyView)) {
                        refreshLobbyView(session, session.isStarted());
                    }
                    refreshUI();
                }
            }
        });
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IGuiForClient#sessionFinished(com.esir.sr.sweetsnake.dto.GameSessionDTO)
     */
    @Override
    public void sessionFinished(final GameSessionDTO session) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                switchView(lobbyView);
                refreshLobbyView(session, session.isStarted());
                refreshUI();
            }
        });
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.api.IGuiForClient#refreshSession(com.esir.sr.sweetsnake.dto.GameSessionDTO)
     */
    @Override
    public void refreshSession(final GameSessionDTO session) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                if (currentView.equals(gameView)) {
                    gameView.drawGameboard(session.getGameEngineDto());
                    gameView.refreshScores(session.getPlayersDto());
                    refreshUI();
                }
            }
        });
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
     */
    private void switchView(final AbstractView view) {
        currentView.clear();
        getContentPane().removeAll();
        getContentPane().add(view);
        refreshUI();
        currentView = view;
        log.debug("View switched to {}", view.getClass().getSimpleName());
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
     * @param session
     *            The DTO representing the session
     * @param isStarted
     *            True if the game session is started, false otherwise
     */
    private void refreshLobbyView(final GameSessionDTO session, final boolean isStarted) {
        lobbyView.refreshPlayers(session.getLeader().getName(), session.getPlayersDto());
        lobbyView.refreshButtons(session.getLeader().getName(), isStarted);
    }

}
