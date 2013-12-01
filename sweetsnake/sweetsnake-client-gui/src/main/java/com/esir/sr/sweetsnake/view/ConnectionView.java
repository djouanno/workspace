package com.esir.sr.sweetsnake.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.annotation.PostConstruct;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.remoting.RemoteConnectFailureException;
import org.springframework.stereotype.Component;

import com.esir.sr.sweetsnake.component.CustomButton;
import com.esir.sr.sweetsnake.component.ImagePanel;
import com.esir.sr.sweetsnake.constants.ClientGuiConstants;
import com.esir.sr.sweetsnake.exception.UnableToConnectException;

/**
 * This class graphically reprents the connection view by extending the AbstractView class.
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 * @see com.esir.sr.sweetsnake.view.AbstractView
 */
@Component
public class ConnectionView extends AbstractView
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    /** The serial version UID */
    private static final long   serialVersionUID = -2207414981436525337L;

    /** The logger */
    private static final Logger log              = LoggerFactory.getLogger(ConnectionView.class);

    /** The username textfield text */
    private static final String USERNAME_TF_TEXT = "choose an username";

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    /** The top image panel */
    private ImagePanel          topPL;

    /** The center panel */
    private JPanel              centerPL;

    /** The connect label */
    private JLabel              connectLB;

    /** The username textfield */
    private JTextField          usernameTF;

    /** The connect button */
    private JButton             connectBTN;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR & INIT
     **********************************************************************************************/

    /**
     * Creates a new connection view
     */
    protected ConnectionView() {
        super();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.view.AbstractView#init()
     */
    @PostConstruct
    @Override
    protected void init() {
        super.init();
        log.info("Initializing the Connection View");
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
        setLayout(new GridBagLayout());

        final GridBagConstraints gbc = new GridBagConstraints();

        initTopPL();
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.weightx = 1;
        gbc.weighty = 0.1;
        gbc.insets = new Insets(ClientGuiConstants.VIEW_HEIGHT / 4, 0, 0, 0);
        add(topPL, gbc);

        initCenterPL();
        gbc.gridy = 1;
        gbc.weighty = 1000;
        gbc.insets = new Insets(30, 0, 0, 0);
        add(centerPL, gbc);

        initConnectLB();
        gbc.fill = GridBagConstraints.NONE;
        gbc.gridy = 0;
        gbc.weighty = 0.1;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 10, 0);
        centerPL.add(connectLB, gbc);

        initUsernameTF();
        gbc.gridy = 1;
        gbc.weighty = 1000;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 0, 0, 5);
        centerPL.add(usernameTF, gbc);

        initConnectBTN();
        gbc.gridx = 1;
        centerPL.add(connectBTN, gbc);

        setFocusable(true);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.esir.sr.sweetsnake.view.AbstractView#clear()
     */
    @Override
    public void clear() {
        usernameTF.setText(USERNAME_TF_TEXT);
        usernameTF.setFont(new Font("sans-serif", Font.ITALIC, 12));
        usernameTF.setForeground(Color.gray);
    }

    /**********************************************************************************************
     * [BLOCK] PRIVATE METHODS
     **********************************************************************************************/

    /**
     * This methods initializes the top image panel
     */
    private void initTopPL() {
        topPL = new ImagePanel(ClientGuiConstants.LOGO_PATH);
    }

    /**
     * This methods initializes the center panel
     */
    private void initCenterPL() {
        centerPL = new JPanel();
        centerPL.setLayout(new GridBagLayout());
        centerPL.setOpaque(false);
    }

    /**
     * This methods initializes the connect label
     */
    private void initConnectLB() {
        connectLB = new JLabel("Connect to server");
        connectLB.setForeground(Color.black);
        connectLB.setFont(new Font("sans-serif", Font.BOLD, 16));
    }

    /**
     * This methods initializes the username text field
     */
    private void initUsernameTF() {
        usernameTF = new JTextField(new String(USERNAME_TF_TEXT));
        usernameTF.setPreferredSize(new Dimension(200, 26));
        usernameTF.setFont(new Font("sans-serif", Font.ITALIC, 12));
        usernameTF.setForeground(Color.gray);
        usernameTF.setBorder(BorderFactory.createCompoundBorder(new LineBorder(Color.gray), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        usernameTF.addFocusListener(new FocusClearListener());
        usernameTF.addActionListener(new ConnectListener());
    }

    /**
     * This methods initializes the connect button
     */
    private void initConnectBTN() {
        connectBTN = new CustomButton("connect");
        connectBTN.addActionListener(new ConnectListener());
    }

    /**********************************************************************************************
     * [BLOCK] INTERNAL LISTENERS
     **********************************************************************************************/

    /**
     * This class provides a focus listener on the username textfield by implements the FocusListener interface.
     * 
     * @author Herminaël Rougier
     * @author Damien Jouanno
     * 
     * @see java.awt.event.FocusListener
     */
    private class FocusClearListener implements FocusListener
    {

        /*
         * (non-Javadoc)
         * 
         * @see java.awt.event.FocusListener#focusGained(java.awt.event.FocusEvent)
         */
        @Override
        public void focusGained(final FocusEvent e) {
            if (usernameTF.getText().equals(USERNAME_TF_TEXT)) {
                usernameTF.setText("");
                usernameTF.setFont(new Font("sans-serif", Font.BOLD, 12));
                usernameTF.setForeground(Color.black);
            }
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.awt.event.FocusListener#focusLost(java.awt.event.FocusEvent)
         */
        @Override
        public void focusLost(final FocusEvent e) {
            if (usernameTF.getText().isEmpty()) {
                usernameTF.setText(USERNAME_TF_TEXT);
                usernameTF.setFont(new Font("sans-serif", Font.ITALIC, 12));
                usernameTF.setForeground(Color.gray);
            }
        }

    }

    /**
     * This class provides an action listener on the connect button by implementing the ActionListener interface.
     * 
     * @author Herminaël Rougier
     * @author Damien Jouanno
     * 
     * @see java.awt.event.ActionListener
     */
    private class ConnectListener implements ActionListener
    {

        /*
         * (non-Javadoc)
         * 
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        @Override
        public void actionPerformed(final ActionEvent e) {
            final String username = usernameTF.getText();
            log.debug("Trying to connect to server with username {}", username);
            try {
                client.connect(username);
            } catch (final UnableToConnectException e1) {
                log.warn(e1.getMessage(), e1);
                gui.displayErrorMessage(e1.getMessage());
            } catch (final RemoteConnectFailureException e1) {
                log.error(e1.getMessage(), e1);
                gui.serverNotReachable();
            }
        }

    }

}
