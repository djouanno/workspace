package com.esir.sr.sweetsnake.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListModel;
import javax.swing.ToolTipManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.esir.sr.sweetsnake.component.ImagePanel;
import com.esir.sr.sweetsnake.component.SweetSnakeList;
import com.esir.sr.sweetsnake.constants.GuiConstants;
import com.esir.sr.sweetsnake.dto.PlayerDTO;
import com.esir.sr.sweetsnake.enumeration.PlayerStatus;

/**
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
@Component
public class PlayersView extends AbstractView
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    /** The serial version UID */
    private static final long         serialVersionUID = -5820091417435340407L;

    /** The logger */
    private static final Logger       log              = LoggerFactory.getLogger(PlayersView.class);

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    /** The view title panel */
    private ImagePanel                playersListIPL;

    /** The top panel */
    private JPanel                    topPL;

    /** The center panel */
    private JPanel                    centerPL;

    /** The bottom panel */
    private JPanel                    bottomPL;

    /** The players list */
    private SweetSnakeList<PlayerDTO> playersLST;

    /** The refresh button */
    private JButton                   refreshListBTN;

    /** The request button */
    private JButton                   requestBTN;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR & INIT
     **********************************************************************************************/

    /**
     * 
     */
    protected PlayersView() {
        super();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.view.SweetSnakeAbstractView#init()
     */
    @Override
    @PostConstruct
    protected void init() {
        super.init();
        log.info("Initializing a new SweetSnakePlayersView");
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

        initPlayersListIPL();
        topPL.add(playersListIPL);

        // center panel
        initCenterPL();
        add(centerPL, BorderLayout.CENTER);

        List<PlayerDTO> players = new LinkedList<PlayerDTO>();

        if (client != null) {
            players = new LinkedList<PlayerDTO>(client.getPlayersList());
        }

        initPlayersLST(players);
        centerPL.add(new JScrollPane(playersLST));

        // bottom panel
        initBottomPL();
        add(bottomPL, BorderLayout.SOUTH);

        final GridBagConstraints gbc = new GridBagConstraints();

        initRefreshListBTN();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(5, 0, 0, 0);
        bottomPL.add(refreshListBTN, gbc);

        initRequestBTN();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.NONE;
        bottomPL.add(requestBTN, gbc);
    }

    /**
     * 
     * @param playersList
     */
    public void refreshPlayersList(final List<PlayerDTO> playersList) {
        if (isBuilded) {
            playersLST.removeAllElements();
            final List<PlayerDTO> players = new LinkedList<PlayerDTO>(playersList);
            for (final PlayerDTO player : players) {
                playersLST.addElement(player);
            }
        }
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
    private void initPlayersListIPL() {
        playersListIPL = new ImagePanel(GuiConstants.PLAYERS_LIST_PATH);
    }

    /**
     * 
     */
    private void initCenterPL() {
        centerPL = new JPanel();
        centerPL.setOpaque(false);
        centerPL.setLayout(new BorderLayout());
    }

    /**
     * 
     * @param players
     */
    private void initPlayersLST(final List<PlayerDTO> players) {
        playersLST = new SweetSnakeList<PlayerDTO>();
        for (final PlayerDTO player : players) {
            playersLST.addElement(player);
        }
        ToolTipManager.sharedInstance().registerComponent(playersLST);

        playersLST.setCellRenderer(new DefaultListCellRenderer() {
            private static final long serialVersionUID = 5859407590339448327L;

            @SuppressWarnings("rawtypes")
            @Override
            public java.awt.Component getListCellRendererComponent(final JList list, final Object value, final int index, final boolean isSelected, final boolean cellHasFocus) {
                final JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                final PlayerDTO player = (PlayerDTO) value;
                ImageIcon imageIcon = new ImageIcon(PlayersView.class.getResource(GuiConstants.UNAVAILABLE_ICON_PATH));
                if (player.getStatus() == PlayerStatus.AVAILABLE) {
                    imageIcon = new ImageIcon(PlayersView.class.getResource(GuiConstants.AVAILABLE_ICON_PATH));
                }
                if (player.getStatus() == PlayerStatus.INVITED || player.getStatus() == PlayerStatus.INVITING) {
                    imageIcon = new ImageIcon(PlayersView.class.getResource(GuiConstants.INVITE_ICON_PATH));
                }
                label.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0), BorderFactory.createEmptyBorder(3, 10, 3, 10)));
                label.setFont(new Font("sans-serif", Font.PLAIN, 16));
                label.setIcon(imageIcon);
                return label;
            }
        });

        playersLST.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(final MouseEvent e) {
                @SuppressWarnings("unchecked")
                final JList<PlayerDTO> list = (JList<PlayerDTO>) e.getSource();
                final ListModel<PlayerDTO> model = list.getModel();
                final int index = list.locationToIndex(e.getPoint());
                list.setToolTipText(null);
                if (index > -1) {
                    final PlayerDTO player = model.getElementAt(index);
                    String text = player.getName();
                    switch (player.getStatus()) {
                        case AVAILABLE:
                            text += " is ready to play";
                            break;
                        case DISCONNECTED:
                            text += " is not connected";
                            break;
                        case INVITED:
                            text += " has already been invited by someone else";
                            break;
                        case INVITING:
                            text += " has already invited someone else";
                            break;
                        case PLAYING:
                            text += " is playing with someone else";
                            break;
                        default:
                            break;
                    }
                    list.setToolTipText(text);
                }
            }
        });
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
    private void initRefreshListBTN() {
        refreshListBTN = new JButton("refresh list");
        refreshListBTN.addActionListener(new RefreshListener());
    }

    /**
     * 
     */
    private void initRequestBTN() {
        requestBTN = new JButton("request game");
        requestBTN.addActionListener(new RequestGameListener());
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
    private class RefreshListener implements ActionListener
    {

        /*
         * (non-Javadoc)
         * 
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        @Override
        public void actionPerformed(final ActionEvent e) {
            List<PlayerDTO> playersList = new LinkedList<PlayerDTO>();
            if (client != null) {
                playersList = new LinkedList<PlayerDTO>(client.getPlayersList());
            }
            refreshPlayersList(playersList);
        }

    }

    /**
     * 
     * @author Herminaël Rougier
     * @author Damien Jouanno
     * 
     */
    private class RequestGameListener implements ActionListener
    {

        /*
         * (non-Javadoc)
         * 
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        @Override
        public void actionPerformed(final ActionEvent e) {
            final PlayerDTO selectedPlayer = playersLST.getSelectedValue();
            if (selectedPlayer == null) {
                gui.displayErrorMessage("Please select an opponent first");
            } else {
                gui.requestGame(selectedPlayer);
            }
        }

    }

}
