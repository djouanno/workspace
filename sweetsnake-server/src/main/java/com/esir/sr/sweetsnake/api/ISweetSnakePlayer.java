package com.esir.sr.sweetsnake.api;

import com.esir.sr.sweetsnake.enumeration.SweetSnakePlayerStatus;


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
    SweetSnakePlayerStatus getStatus();

    /**
     * 
     * @param status
     */
    void setStatus(SweetSnakePlayerStatus status);

}
