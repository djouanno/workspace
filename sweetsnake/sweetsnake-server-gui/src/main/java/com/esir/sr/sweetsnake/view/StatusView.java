package com.esir.sr.sweetsnake.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * This class graphically reprents the status view by extending the AbstractView class.
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 * @see com.esir.sr.sweetsnake.view.AbstractView
 */
@Component
public class StatusView extends AbstractView
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    /** The serial version UID */
    private static final long   serialVersionUID = -6328912476252064621L;

    /** The logger */
    private static final Logger log              = LoggerFactory.getLogger(StatusView.class);

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    /** The title label */
    private JLabel              titleLB;

    /** The center panel */
    private JPanel              centerPL;

    /** The status label */
    private JLabel              statusLB;

    /** The running time label */
    private JLabel              runningTimeLB;

    /** The running time values */
    private int                 days, hours, minutes, seconds;

    /** The running time timer */
    private Timer               timer;

    /** The bottom panel */
    private JPanel              bottomPL;

    /** The start button */
    private JButton             startBTN;

    /** The stop button */
    private JButton             stopBTN;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * Creates a new status view
     */
    public StatusView() {
        super();
        log.info("Initializing the Manage View");
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

        GridBagConstraints gbc = new GridBagConstraints();

        initStatusLB();
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.weighty = 0.1;
        centerPL.add(statusLB, gbc);

        initRunningTimeLB();
        gbc.gridy = 1;
        gbc.weighty = 1000;
        centerPL.add(runningTimeLB, gbc);

        initBottomPL();
        add(bottomPL, BorderLayout.SOUTH);

        initStartBTN();
        gbc = new GridBagConstraints();
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.fill = GridBagConstraints.VERTICAL;
        gbc.weightx = 1000;
        gbc.weighty = 1;
        bottomPL.add(startBTN, gbc);

        initStopBTN();
        gbc.gridx = 1;
        gbc.weightx = 0.1;
        gbc.insets = new Insets(0, 10, 0, 0);
        bottomPL.add(stopBTN, gbc);
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    /**
     * This method disables the start button
     */
    public void disableStartBTN() {
        startBTN.setEnabled(false);
    }

    /**
     * This method enables the start button
     */
    public void enableStartBTN() {
        startBTN.setEnabled(true);
        statusLB.setText("Status : Stopped");
    }

    /**
     * This method disables the stop button
     */
    public void disableStopBTN() {
        stopBTN.setEnabled(false);
    }

    /**
     * This method enables the start button
     */
    public void enableStopBTN() {
        stopBTN.setEnabled(true);
        statusLB.setText("Status : Started");
    }

    /**
     * This method starts the uptime timer
     */
    public void startTimer() {
        if (!timer.isRunning()) {
            timer.start();
        }
    }

    /**
     * This method stops the uptime timer
     */
    public void stopTimer() {
        if (timer.isRunning()) {
            timer.stop();
        }
    }

    /**********************************************************************************************
     * [BLOCK] PRIVATE METHODS
     **********************************************************************************************/

    /**
     * This method initializes the title label
     */
    private void initTitleLB() {
        titleLB = new JLabel("Server status");
        titleLB.setFont(new Font("sans-serif", Font.BOLD, 14));
    }

    /**
     * This method initializes the center panel
     */
    private void initCenterPL() {
        centerPL = new JPanel(new GridBagLayout());
        centerPL.setBorder(new EmptyBorder(10, 0, 10, 0));
    }

    /**
     * This method initializes the status label
     */
    private void initStatusLB() {
        statusLB = new JLabel("Status : Started");
        statusLB.setFont(new Font("sans-serif", Font.BOLD, 13));
    }

    /**
     * This method initializes the running time label
     */
    private void initRunningTimeLB() {
        runningTimeLB = new JLabel("Uptime : " + days + " day(s) " + intToString(hours, 2) + " hour(s) " + intToString(minutes, 2) + " minute(s) " + intToString(seconds, 2) + " second(s)");
        runningTimeLB.setFont(new Font("sans-serif", Font.BOLD, 13));
        timer = new Timer(1000, new TimerListener());
    }

    /**
     * This method initializes the bottom panel
     */
    private void initBottomPL() {
        bottomPL = new JPanel(new GridBagLayout());
    }

    /**
     * This method initializes the start button
     */
    private void initStartBTN() {
        startBTN = new JButton("start server");
        startBTN.addActionListener(new StartListener());
    }

    /**
     * This method initializes the stop button
     */
    private void initStopBTN() {
        stopBTN = new JButton("stop server");
        stopBTN.addActionListener(new StopListener());
    }

    /**
     * This method converts an integer to a string containing the specified number of zeros before the integer
     * 
     * @param num
     *            The number to convert
     * @param digits
     *            The number of 0 to display before the number
     * @return A string representing the number filled with zero
     */
    private static String intToString(final int num, final int digits) {
        final char[] zeros = new char[digits];
        Arrays.fill(zeros, '0');
        final DecimalFormat df = new DecimalFormat(String.valueOf(zeros));

        return df.format(num);
    }

    /**********************************************************************************************
     * [BLOCK] INTERNAL LISTENERS
     **********************************************************************************************/

    /**
     * This class provides an action listener on the start button by implementing the ActionListener interface.
     * 
     * @author Herminaël Rougier
     * @author Damien Jouanno
     * 
     * @see java.awt.event.ActionListener
     */
    private class StartListener implements ActionListener
    {

        /*
         * (non-Javadoc)
         * 
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        @Override
        public void actionPerformed(final ActionEvent e) {
            server.startServer();
            days = 0;
            hours = 0;
            minutes = 0;
            seconds = 0;
            startTimer();
            disableStartBTN();
            enableStopBTN();
        }

    }

    /**
     * This class provides an action listener on the stop button by implementing the ActionListener interface.
     * 
     * @author Herminaël Rougier
     * @author Damien Jouanno
     * 
     * @see java.awt.event.ActionListener
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
            server.stopServer();
            stopTimer();
            disableStopBTN();
            enableStartBTN();
        }

    }

    /**
     * This class provides an action listener on the uptime timer by implementing the ActionListener interface.
     * 
     * @author Herminaël Rougier
     * @author Damien Jouanno
     * 
     * @see java.awt.event.ActionListener
     */
    private class TimerListener implements ActionListener
    {

        /*
         * (non-Javadoc)
         * 
         * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
         */
        @Override
        public void actionPerformed(final ActionEvent e) {
            seconds++;
            if (seconds == 60) {
                seconds = 0;
                minutes++;
            }
            if (minutes == 60) {
                minutes = 0;
                hours++;
            }
            if (hours == 24) {
                hours = 0;
                days++;
            }
            runningTimeLB.setText("Uptime : " + days + " day(s) " + intToString(hours, 2) + " hour(s) " + intToString(minutes, 2) + " minute(s) " + intToString(seconds, 2) + " second(s)");
        }

    }

}
