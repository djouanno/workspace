package com.esir.sr.sweetsnake.view;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.annotation.PostConstruct;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.esir.sr.sweetsnake.constants.SweetSnakeGameConstants;
import com.esir.sr.sweetsnake.exception.UnableToConnectException;

@Component
public class SweetSnakeHomeView extends SweetSnakeAbstractView
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    private static final long             serialVersionUID = -2207414981436525337L;
    private static final org.slf4j.Logger log              = LoggerFactory.getLogger(SweetSnakeHomeView.class);

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

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
        usernameTF = new JTextField("username");
        usernameTF.setPreferredSize(new Dimension(200, 27));
        usernameTF.setAlignmentY(SweetSnakeGameConstants.CELL_SIZE * (SweetSnakeGameConstants.GRID_SIZE + 1) / 2);
        connectBTN = new JButton("connect");
        connectBTN.addActionListener(new ConnectListener());
        add(usernameTF, gbc);
        add(connectBTN, gbc);
    }

    /**********************************************************************************************
     * [BLOCK] INTERNAL LISTENERS
     **********************************************************************************************/

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
                JOptionPane.showMessageDialog((java.awt.Component) ihm, e1.getMessage());
            }
        }
    }

}
