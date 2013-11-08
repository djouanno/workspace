package com.esir.sr.sweetsnake.api;

public interface IGameRequest
{

    /**
     * 
     * @return
     */
    String getId();

    /**
     * 
     * @return
     */
    IPlayer getRequestingPlayer();

    /**
     * 
     * @return
     */
    IPlayer getRequestedPlayer();

    /**
     * 
     */
    void cancel();

}
