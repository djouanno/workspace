package com.esir.sr.sweetsnake.component;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;

/**
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
public class Toast extends JDialog
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    /** The serial version UID */
    private static final long serialVersionUID = -9201199499095469781L;

    /** The displayed duration */
    private static final int  DURATION         = 2000;

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    /** The GUI */
    private JFrame            gui;

    /** The message */
    private String            message;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * 
     */
    private Toast() {
    }

    /**
     * 
     * @param _gui
     * @param _msg
     */
    private Toast(final JFrame _gui, final String _msg) {
        super(_gui, false);
        gui = _gui;
        message = _msg;
        initComponents();
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC STATIC METHODS
     **********************************************************************************************/

    /**
     * 
     * @param _gui
     * @param _msg
     */
    public static void displayToast(final JFrame _gui, final String _msg) {
        final JDialog dialog = new Toast(_gui, _msg);
        final Timer timer = new Timer(DURATION, new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                dialog.setVisible(false);
                dialog.dispose();
            }
        });
        timer.setRepeats(false);
        timer.start();
        dialog.setVisible(true);
    }

    /**********************************************************************************************
     * [BLOCK] PRIVATE METHODS
     **********************************************************************************************/

    /**
     * 
     */
    private void initComponents() {
        setLayout(new GridBagLayout());

        setFocusableWindowState(false);
        setUndecorated(true);
        setSize(new Dimension(300, 50));
        setLocationRelativeTo(gui);
        getContentPane().setBackground(Color.BLACK);

        final GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        final GraphicsDevice gd = ge.getDefaultScreenDevice();
        final boolean isTranslucencySupported = gd.isWindowTranslucencySupported(GraphicsDevice.WindowTranslucency.TRANSLUCENT);

        if (isTranslucencySupported) {
            setOpacity(0.5f);
        }

        final JLabel label = new JLabel();
        label.setForeground(Color.WHITE);
        label.setText(message);
        add(label);
    }
}
