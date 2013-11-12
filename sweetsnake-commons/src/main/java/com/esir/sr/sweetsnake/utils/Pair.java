package com.esir.sr.sweetsnake.utils;

import java.io.Serializable;

/**
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 * @param <T>
 * @param <E>
 */
public class Pair<T, E> implements Serializable
{

    /**********************************************************************************************
     * [BLOCK] STATIC FIELDS
     **********************************************************************************************/

    /** The serial version UID */
    private static final long serialVersionUID = -835416453579183853L;

    /**********************************************************************************************
     * [BLOCK] FIELDS
     **********************************************************************************************/

    /** The first element */
    private T                 first;

    /** The second element */
    private E                 second;

    /**********************************************************************************************
     * [BLOCK] CONSTRUCTOR
     **********************************************************************************************/

    /**
     * 
     * @param _first
     * @param _second
     */
    public Pair(final T _first, final E _second) {
        first = _first;
        second = _second;
    }

    /**********************************************************************************************
     * [BLOCK] GETTERS
     **********************************************************************************************/

    /**
     * 
     * @return
     */
    public T getFirst() {
        return first;
    }

    /**
     * 
     * @return
     */
    public E getSecond() {
        return second;
    }

    /**********************************************************************************************
     * [BLOCK] SETTERS
     **********************************************************************************************/

    /**
     * 
     * @param _first
     */
    public void setFirst(final T _first) {
        first = _first;
    }

    /**
     * 
     * @param _second
     */
    public void setSecond(final E _second) {
        second = _second;
    }

}
