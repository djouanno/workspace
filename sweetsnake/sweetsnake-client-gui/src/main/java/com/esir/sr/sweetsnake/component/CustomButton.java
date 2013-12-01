package com.esir.sr.sweetsnake.component;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

/**
 * This class provides a custom look & feel button by extending the JButton class.
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 * @see javax.swing.JButton
 */
public class CustomButton extends JButton
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    /** The serial version UID */
    private static final long   serialVersionUID = -8571126044147222882L;

    /** The empty border */
    private static final Border emptyBorder      = BorderFactory.createEmptyBorder(4, 16, 4, 16);

    /** The light green color */
    public static final Color   lightGreen       = new Color(120, 220, 120);

    /** The dark green color */
    public static final Color   darkGreen        = new Color(100, 200, 90);

    /** The light gray color */
    public static final Color   lightGray        = new Color(238, 238, 238);

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * Creates a new button
     */
    public CustomButton() {
        super();
        initButtonParameters();
    }

    /**
     * Creates a new button with the specified text
     * 
     * @param text
     *            The text to display on the button
     */
    public CustomButton(final String text) {
        this();
        setText(text);
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    /*
     * (non-Javadoc)
     * 
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     */
    @Override
    protected void paintComponent(final Graphics g) {
        Color color1 = lightGreen, color2 = darkGreen;
        if (model.isPressed()) {
            color1 = darkGreen;
            color2 = darkGreen;
        } else if (!model.isEnabled()) {
            color1 = lightGray;
            color2 = lightGray;
        } else if (model.isRollover()) {
            color1 = lightGreen;
            color2 = lightGreen;
        }
        paintComponent((Graphics2D) g, color1, color2);
        super.paintComponent(g);
    }

    /**********************************************************************************************
     * [BLOCK] PRIVATE METHODS
     **********************************************************************************************/

    /**
     * 
     * @param g2d
     * @param color1
     * @param color2
     */
    private void paintComponent(final Graphics2D g2d, final Color color1, final Color color2) {
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        final int w = getWidth();
        final int h = getHeight();
        g2d.setPaint(new GradientPaint(0, 0, color1, 0, h, color2));
        g2d.fillRect(0, 0, w, h);
    }

    /**
     * This method initializes the button parameters
     */
    private void initButtonParameters() {
        setContentAreaFilled(false);
        setFocusPainted(false);
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setBorder(BorderFactory.createCompoundBorder(new LineBorder(Color.gray), emptyBorder));
    }

}
