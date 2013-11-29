package com.esir.sr.sweetsnake.utils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
public class UnclosableBufferedInputStream extends BufferedInputStream
{

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * Creates a new unclosable buffered input stream
     * 
     * @param in
     *            The file input stream
     */
    public UnclosableBufferedInputStream(final InputStream in) {
        super(in);
        super.mark(Integer.MAX_VALUE);
    }

    /**********************************************************************************************
     * [BLOCK] PUBLIC OVERRIDED METHODS
     **********************************************************************************************/

    /*
     * (non-Javadoc)
     * 
     * @see java.io.BufferedInputStream#close()
     */
    @Override
    public void close() throws IOException {
        super.reset();
    }

}