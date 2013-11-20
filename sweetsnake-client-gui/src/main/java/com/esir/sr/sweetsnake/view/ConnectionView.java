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
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.esir.sr.sweetsnake.component.ImagePanel;
import com.esir.sr.sweetsnake.constants.ClientGuiConstants;
import com.esir.sr.sweetsnake.exception.UnableToConnectException;

/**
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
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
    private static final String USERNAME_TF_TEXT = " choose an username";

    /** The connect button text */
    private static final String CONNECT_BTN_TEXT = "connect";

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    /** The logo panel */
    private ImagePanel          logoPL;

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
     * 
     */
    protected ConnectionView() {
        super();
    }

    /**
     * 
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
    public void buildImpl() {
        setLayout(new GridBagLayout());
        final GridBagConstraints gbc = new GridBagConstraints();

        initLogoPL();
        initConnectLB();
        initUsernameTF();
        initConnectBTN();

        // logo
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 30, 0);
        add(logoPL, gbc);

        // label
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 10, 0);
        add(connectLB, gbc);

        // text field
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 0, 0, 5);
        add(usernameTF, gbc);

        // button
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        add(connectBTN, gbc);
    }

    /**********************************************************************************************
     * [BLOCK] PRIVATE METHODS
     **********************************************************************************************/

    /**
     * 
     */
    private void initLogoPL() {
        logoPL = new ImagePanel(ClientGuiConstants.LOGO_PATH);
    }

    /**
     * 
     */
    private void initConnectLB() {
        connectLB = new JLabel("Connect to server");
        connectLB.setForeground(Color.white);
        connectLB.setFont(new Font("sans-serif", Font.BOLD, 16));
    }

    /**
     * 
     */
    private void initUsernameTF() {
        usernameTF = new JTextField(new String(USERNAME_TF_TEXT));
        usernameTF.setBorder(new LineBorder(Color.black));
        usernameTF.setSize(new Dimension(200, 28));
        usernameTF.setPreferredSize(new Dimension(200, 28));
        usernameTF.addFocusListener(new FocusClearListener());
        usernameTF.addActionListener(new ConnectListener());
    }

    /**
     * 
     */
    private void initConnectBTN() {
        connectBTN = new JButton(CONNECT_BTN_TEXT);
        connectBTN.addActionListener(new ConnectListener());
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
    private class FocusClearListener implements FocusListener
    {

        /*
         * (non-Javadoc)
         * 
         * @see java.awt.event.FocusListener#focusGained(java.awt.event.FocusEvent)
         */
        @Override
        public void focusGained(final FocusEvent arg0) {
            if (usernameTF.getText().equals(USERNAME_TF_TEXT)) {
                usernameTF.setText(" ");
            }
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.awt.event.FocusListener#focusLost(java.awt.event.FocusEvent)
         */
        @Override
        public void focusLost(final FocusEvent arg0) {
            if (usernameTF.getText().isEmpty() || usernameTF.getText().equals(" ")) {
                usernameTF.setText(USERNAME_TF_TEXT);
            }
        }

    }

    /**
     * 
     * @author Herminaël Rougier
     * @author Damien Jouanno
     * 
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
                gui.connectedToServer();
            } catch (final UnableToConnectException e1) {
                JOptionPane.showMessageDialog(gui, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

    }

}
