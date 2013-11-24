package com.esir.sr.sweetsnake.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.esir.sr.sweetsnake.component.SessionsList;
import com.esir.sr.sweetsnake.dto.GameSessionDTO;
import com.esir.sr.sweetsnake.dto.PlayerDTO;

/**
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
@Component
public class SessionsView extends AbstractView
{
    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    /** The serial version UID */
    private static final long   serialVersionUID = 1418303591166353768L;

    /** The logger */
    private static final Logger log              = LoggerFactory.getLogger(SessionsView.class);

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    /** The title label */
    private JLabel              titleLB;

    /** The center panel */
    private JPanel              centerPL;

    /** The sessions list */
    private SessionsList        sessionsLST;

    /** The bottom panel */
    private JPanel              bottomPL;

    /** The stop button */
    private JButton             stopBTN;

    /** The remove button */
    private JButton             removeBTN;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * 
     */
    public SessionsView() {
        super();
        log.info("Initializing the Sessions View");
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.view.AbstractView#buildImpl()
     */
    @Override
    protected void buildImpl() {
        setLayout(new BorderLayout());

        initTitleLB();
        add(titleLB, BorderLayout.NORTH);

        initCenterPL();
        add(centerPL, BorderLayout.CENTER);

        initSessionsLST();
        centerPL.add(new JScrollPane(sessionsLST), BorderLayout.CENTER);

        initBottomPL();
        add(bottomPL, BorderLayout.SOUTH);

        initStopBTN();
        final GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.weightx = 1000;
        gbc.weighty = 1;
        bottomPL.add(stopBTN, gbc);

        initRemoveBTN();
        gbc.gridx = 1;
        gbc.weightx = 0.1;
        gbc.insets = new Insets(0, 10, 0, 0);
        bottomPL.add(removeBTN, gbc);
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    /**
     * 
     * @param _sessions
     */
    public void refreshSessions(final List<GameSessionDTO> _sessions) {
        sessionsLST.removeAllElements();
        final List<GameSessionDTO> sessions = new LinkedList<GameSessionDTO>(_sessions);

        if (sessions.isEmpty()) {
            sessionsLST.disableSelection();
            sessionsLST.addElement(new GameSessionDTO("No available game for the moment", new LinkedList<PlayerDTO>(), null, null, false));
        } else {
            sessionsLST.enableSelection();
            for (final GameSessionDTO session : sessions) {
                sessionsLST.addElement(session);
            }
        }

        titleLB.setText("Running sessions (" + sessions.size() + ")");
    }

    /**********************************************************************************************
     * [BLOCK] PRIVATE METHODS
     **********************************************************************************************/

    /**
     * 
     */
    private void initTitleLB() {
        titleLB = new JLabel("Running sessions (0)");
        titleLB.setFont(new Font("sans-serif", Font.BOLD, 14));
    }

    /**
     * 
     */
    private void initCenterPL() {
        centerPL = new JPanel(new BorderLayout());
        centerPL.setBorder(new EmptyBorder(10, 0, 10, 0));
    }

    /**
     * 
     */
    private void initBottomPL() {
        bottomPL = new JPanel(new GridBagLayout());
    }

    /**
     * 
     */
    private void initStopBTN() {
        stopBTN = new JButton("stop session");
        stopBTN.addActionListener(new StopListener());
    }

    /**
     * 
     */
    private void initRemoveBTN() {
        removeBTN = new JButton("remove session");
        removeBTN.addActionListener(new RemoveListener());
    }

    /**
     * 
     */
    private void initSessionsLST() {
        sessionsLST = new SessionsList();
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
    private class StopListener implements ActionListener
    {

        /*
         * (non-Javadoc)
         * 
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        @Override
        public void actionPerformed(final ActionEvent e) {
            final GameSessionDTO session = sessionsLST.getSelectedValue();
            if (session != null) {
                server.stopSession(session);
            }
        }

    }

    /**
     * 
     * @author Herminaël Rougier
     * @author Damien Jouanno
     * 
     */
    private class RemoveListener implements ActionListener
    {

        /*
         * (non-Javadoc)
         * 
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        @Override
        public void actionPerformed(final ActionEvent e) {
            final GameSessionDTO session = sessionsLST.getSelectedValue();
            if (session != null) {
                server.removeSession(session);
            }
        }

    }

}
