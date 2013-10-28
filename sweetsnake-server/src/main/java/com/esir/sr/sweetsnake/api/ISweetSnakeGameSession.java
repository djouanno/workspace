package com.esir.sr.sweetsnake.api;

public interface ISweetSnakeGameSession
{

    /**
     * 
     * @return
     */
    public ISweetSnakePlayer getPlayer1();

    /**
     * 
     * @return
     */
    public ISweetSnakePlayer getPlayer2();

    /**
     * 
     */
    public void startGame();

}
