package com.esir.sr.sweetsnake.view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.annotation.PostConstruct;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.esir.sr.sweetsnake.component.SweetSnakeImagePanel;
import com.esir.sr.sweetsnake.constants.SweetSnakeIhmConstants;
import com.esir.sr.sweetsnake.exception.UnableToConnectException;

@Component
public class SweetSnakeHomeView extends SweetSnakeAbstractView
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    private static final long             serialVersionUID = -2207414981436525337L;
    private static final org.slf4j.Logger log              = LoggerFactory.getLogger(SweetSnakeHomeView.class);
    private static final String           USERNAME_TF_TEXT = "Choose an username";
    private static final String           CONNECT_BTN_TEXT = "Connect";

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    private SweetSnakeImagePanel          logoPL;
    private JTextField                    usernameTF;
    private JButton                       connectBTN;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    public SweetSnakeHomeView() {
        super();
    }

    /**********************************************************************************************
     * [BLOCK] INIT METHOD
     **********************************************************************************************/

    @PostConstruct
    public void init() {
        log.info("Initializing a new SweetSnakeHomeView");
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    @Override
    public void build() {
        setLayout(new GridBagLayout());
        final GridBagConstraints gbc = new GridBagConstraints();

        initLogoPL();
        initUsernameTF();
        initConnectBTN();

        // logo
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 30, 0);
        add(logoPL, gbc);

        // text field
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 0, 0, 5);
        add(usernameTF, gbc);

        // button
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(connectBTN, gbc);
    }

    /**********************************************************************************************
     * [BLOCK] PRIVATE METHODS
     **********************************************************************************************/

    private void initLogoPL() {
        logoPL = new SweetSnakeImagePanel(SweetSnakeIhmConstants.LOGO_PATH);
    }

    private void initUsernameTF() {
        usernameTF = new JTextField(new String(USERNAME_TF_TEXT));
        usernameTF.addFocusListener(new FocusClearListener());
        usernameTF.setSize(new Dimension(200, 30));
        usernameTF.setPreferredSize(new Dimension(200, 30));
    }

    private void initConnectBTN() {
        connectBTN = new JButton(CONNECT_BTN_TEXT);
        connectBTN.addActionListener(new ConnectListener());
        connectBTN.setSize(new Dimension(100, 30));
        connectBTN.setPreferredSize(new Dimension(100, 30));
    }

    /**********************************************************************************************
     * [BLOCK] INTERNAL LISTENERS
     **********************************************************************************************/

    private class FocusClearListener implements FocusListener
    {
        @Override
        public void focusGained(final FocusEvent arg0) {
            if (usernameTF.getText().equals(USERNAME_TF_TEXT)) {
                usernameTF.setText("");
            }
        }

        @Override
        public void focusLost(final FocusEvent arg0) {
            if (usernameTF.getText().isEmpty()) {
                usernameTF.setText(USERNAME_TF_TEXT);
            }
        }
    }

    private class ConnectListener implements ActionListener
    {
        @Override
        public void actionPerformed(final ActionEvent e) {
            final String username = usernameTF.getText();
            log.debug("Trying to connect to server with username {}", username);
            try {
                client.connect(username);
                ihm.successfullyConnected();
            } catch (final UnableToConnectException e1) {
                JOptionPane.showMessageDialog((java.awt.Component) ihm, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

}
