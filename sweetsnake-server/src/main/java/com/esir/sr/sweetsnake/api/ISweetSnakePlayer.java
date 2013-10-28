package com.esir.sr.sweetsnake.api;

import com.esir.sr.sweetsnake.enumeration.Status;


public interface ISweetSnakePlayer
{

    /**
     * 
     * @return
     */
    ISweetSnakeClientCallback getClientCallback();

    /**
     * 
     * @return
     */
    String getName();

    /**
     * 
     * @return
     */
    Status getStatus();

    /**
     * 
     * @param status
     */
    void setStatus(Status status);

}
