package com.esir.sr.sweetsnake.api;

/**
 * This interface represents which methods a client must be able to provide to a sever and a GUI.<br />
 * All the methods below are intented to be called by the server or the GUI according to the events they processed.
 * 
 * @author Herminaël Rougier
 * @author Damien Jouanno
 * 
 */
public interface IClient
{

    /**
     * This method returns the username chosen by the client during the connection
     * 
     * @return A String containing the username chosen by the client
     */
    String getUsername();

}
