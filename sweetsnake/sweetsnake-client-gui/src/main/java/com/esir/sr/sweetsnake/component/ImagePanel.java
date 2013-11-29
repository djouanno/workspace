package com.esir.sr.sweetsnake.component;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class extends the JPanel class in order to provide a JPanel with a background image.
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 * @see javax.swing.JPanel
 */
public class ImagePanel extends JPanel
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    /** The serial version UID */
    private static final long   serialVersionUID = -5965163246192991484L;

    /** The logger */
    private static final Logger log              = LoggerFactory.getLogger(ImagePanel.class);

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    /** The image */
    private Image               image;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * Creates a new image panel
     * 
     * @param imagePath
     *            The image path to set in background
     */
    public ImagePanel(final String imagePath) {
        try {
            final BufferedImage bimage = ImageIO.read(ImagePanel.class.getResource(imagePath));
            final Dimension dimension = new Dimension(bimage.getWidth(), bimage.getHeight());
            setOpaque(false);
            setSize(dimension);
            setPreferredSize(dimension);
            setLayout(new GridBagLayout());
            image = bimage;
        } catch (final IOException e) {
            log.error(e.getMessage(), e);
        }
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
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null);
    }

}
