package com.esir.sr.sweetsnake.api;

public interface ISweetSnakeGameRequest
{

    /**
     * 
     * @return
     */
    ISweetSnakePlayer getRequestingPlayer();

    /**
     * 
     * @return
     */
    ISweetSnakePlayer getRequestedPlayer();

    /**
     * 
     */
    void cancel();

}
