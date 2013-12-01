package com.esir.sr.sweetsnake.component;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

import com.esir.sr.sweetsnake.constants.ClientGuiConstants;

/**
 * This class provides a graphical Toast (Android-like) by extending the JDialog class.
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 * @see javax.swing.JDialog
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

    /** The toast (only one at a time) */
    private static Toast      toast;

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
     * Private empty constructor to prevent instiation of Toast
     */
    private Toast() {
    }

    /**
     * Creates a new toast
     * 
     * @param _gui
     *            The main parent GUI
     * @param _msg
     *            The message to display
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
     * This method displays a toast with the specified message
     * 
     * @param _gui
     *            The main parent GUI
     * @param _msg
     *            The message to display
     */
    public static void displayToast(final JFrame _gui, final String _msg) {
        if (toast != null) {
            toast.dispose();
        }

        toast = new Toast(_gui, _msg);
        toast.setVisible(true);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                toast.setVisible(false);
                toast.dispose();
            }
        }, DURATION);
    }

    /**********************************************************************************************
     * [BLOCK] PRIVATE METHODS
     **********************************************************************************************/

    /**
     * This method initializes the toast
     */
    private void initComponents() {
        setLayout(new GridBagLayout());

        setFocusableWindowState(false);
        setUndecorated(true);
        setSize(new Dimension(ClientGuiConstants.TOAST_WIDTH, ClientGuiConstants.TOAST_HEIGHT));
        setLocationRelativeTo(gui);
        setLocation(getLocation().x, getLocation().y - ClientGuiConstants.GUI_HEIGHT / 2 + ClientGuiConstants.TOAST_HEIGHT + ClientGuiConstants.TOAST_MARGIN);
        getContentPane().setBackground(Color.BLACK);
        setOpacity(0.6F);

        final JLabel label = new JLabel();
        label.setFont(new Font("sans-serif", Font.PLAIN, 18));
        label.setForeground(Color.WHITE);
        label.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));
        label.setText(message);
        add(label);
    }

}
