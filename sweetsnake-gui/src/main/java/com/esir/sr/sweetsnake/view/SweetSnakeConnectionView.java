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

import com.esir.sr.sweetsnake.constants.SweetSnakeIhmConstants;
import com.esir.sr.sweetsnake.exception.UnableToConnectException;
import com.esir.sr.sweetsnake.uicomponent.SweetSnakeImagePanel;

@Component("connectionView")
public class SweetSnakeConnectionView extends SweetSnakeAbstractView
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    private static final long    serialVersionUID = -2207414981436525337L;
    private static final Logger  log              = LoggerFactory.getLogger(SweetSnakeConnectionView.class);
    private static final String  USERNAME_TF_TEXT = "Choose an username";
    private static final String  CONNECT_BTN_TEXT = "Connect";

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    private SweetSnakeImagePanel logoPL;
    private JLabel               connectLB;
    private JTextField           usernameTF;
    private JButton              connectBTN;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR & INIT
     **********************************************************************************************/

    /**
     * 
     */
    protected SweetSnakeConnectionView() {
        super();
    }

    /**
     * 
     */
    @PostConstruct
    @Override
    protected void init() {
        super.init();
        log.info("Initializing a new SweetSnakeHomeView");
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
        logoPL = new SweetSnakeImagePanel(SweetSnakeIhmConstants.LOGO_PATH);
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
        usernameTF.addFocusListener(new FocusClearListener());
        usernameTF.setSize(new Dimension(200, 28));
        usernameTF.setPreferredSize(new Dimension(200, 28));
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
                gui.successfullyConnected();
            } catch (final UnableToConnectException e1) {
                JOptionPane.showMessageDialog(gui, e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

}
