package com.esir.sr.sweetsnake.component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;

import javax.annotation.PostConstruct;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * This class provides a JTextArea displaying the the default output stream stream by extending the OutputStream class.
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 * @see javax.io.OutputStream
 */
@Component
public class JTextAreaOS extends OutputStream
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    /** The logger */
    private static final Logger log = LoggerFactory.getLogger(JTextAreaOS.class);

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    /** The internal storage */
    private StringBuilder       logs;

    /** The destination text area */
    private JTextArea           destination;

    /** The current level to display */
    private String              level;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR & INIT
     **********************************************************************************************/

    /**
     * Creates a new text area output stream
     */
    protected JTextAreaOS() {
        super();
    }

    /**
     * Initializes a new text area output stream
     */
    @PostConstruct
    protected void init() {
        log.info("Initialazing the Text Area Output Stream");
        logs = new StringBuilder();
        destination = new JTextArea();
        level = "ALL";
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC METHODS
     **********************************************************************************************/

    /*
     * (non-Javadoc)
     * 
     * @see java.io.OutputStream#write(byte[], int, int)
     */
    @Override
    public void write(final byte[] buffer, final int offset, final int length) throws IOException {
        final String text = new String(buffer, offset, length);
        logs.append(text);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                if (level.equals("ALL") || text.contains("[" + level + "]")) {
                    destination.append(text);
                }
            }
        });
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.io.OutputStream#write(int)
     */
    @Override
    public void write(final int b) throws IOException {
        write(new byte[] { (byte) b }, 0, 1);
    }

    /**
     * This method clears the recorded logs since the last clear
     */
    public void clearLogs() {
        logs = new StringBuilder();
    }

    /**********************************************************************************************
     * [BLOCK] GETTERS
     **********************************************************************************************/

    /**
     * This method returns the recorded logs since the last clear
     * 
     * @return A string representing the recorded logs
     */
    public String getLogs() {
        if (level.equals("ALL")) {
            return logs.toString();
        }

        final BufferedReader br = new BufferedReader(new StringReader(logs.toString()));
        final StringBuilder sb = new StringBuilder();
        String line = null;

        try {
            while ((line = br.readLine()) != null) {
                if (line.contains("[" + level + "]")) {
                    sb.append(line + "\n");
                }
            }
            return sb.toString();
        } catch (final IOException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * This method returns the text area in which the logs are appened
     * 
     * @return A jtextarea in which the logs are appened
     */
    public JTextArea getTextArea() {
        return destination;
    }

    /**********************************************************************************************
     * [BLOCK] SETTERS
     **********************************************************************************************/

    /**
     * This method sets the level of logs which will be appened in the text area
     * 
     * @param _level
     *            A string representing the level of logs which will be appened in the text area
     */
    public void setLevel(final String _level) {
        level = _level;
    }

}
