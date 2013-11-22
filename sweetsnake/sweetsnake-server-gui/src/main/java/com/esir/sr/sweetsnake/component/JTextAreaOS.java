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
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
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
     * 
     * @param _destination
     */
    protected JTextAreaOS() {
        super();
    }

    /**
     * 
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
     * 
     */
    public void clearLogs() {
        logs = new StringBuilder();
    }

    /**********************************************************************************************
     * [BLOCK] GETTERS
     **********************************************************************************************/

    /**
     * 
     * @return
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
     * 
     * @return
     */
    public JTextArea getTextArea() {
        return destination;
    }

    /**********************************************************************************************
     * [BLOCK] SETTERS
     **********************************************************************************************/

    /**
     * 
     * @param _level
     */
    public void setLevel(final String _level) {
        level = _level;
    }

}
