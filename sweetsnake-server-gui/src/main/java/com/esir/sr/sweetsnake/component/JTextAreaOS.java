package com.esir.sr.sweetsnake.component;

import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

/**
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
public class JTextAreaOS extends OutputStream
{

    /** The destination text area */
    private final JTextArea destination;

    /**
     * 
     * @param _destination
     */
    public JTextAreaOS(final JTextArea _destination) {
        destination = _destination;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.io.OutputStream#write(byte[], int, int)
     */
    @Override
    public void write(final byte[] buffer, final int offset, final int length) throws IOException {
        final String text = new String(buffer, offset, length);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                destination.append(text);
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

}