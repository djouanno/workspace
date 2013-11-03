package com.esir.sr.sweetsnake.component;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import org.slf4j.LoggerFactory;

public class SweetSnakeImagePanel extends JPanel
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    private static final long             serialVersionUID = -5965163246192991484L;
    private static final org.slf4j.Logger log              = LoggerFactory.getLogger(SweetSnakeImagePanel.class);

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    private Image                         image;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    public SweetSnakeImagePanel(final String imagePath) {
        try {
            final BufferedImage bimage = ImageIO.read(SweetSnakeImagePanel.class.getResource(imagePath));
            final Dimension dimension = new Dimension(bimage.getWidth(), bimage.getHeight());
            setOpaque(false);
            setSize(dimension);
            setPreferredSize(dimension);
            image = bimage;
        } catch (final IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    @Override
    protected void paintComponent(final Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, null);
    }

}
