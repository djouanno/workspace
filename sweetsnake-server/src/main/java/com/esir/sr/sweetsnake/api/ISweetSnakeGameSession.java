package com.esir.sr.sweetsnake.api;

import com.esir.sr.sweetsnake.enumeration.SweetSnakeDirection;

public interface ISweetSnakeGameSession
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
    ISweetSnakePlayer getPlayer1();

    /**
     * 
     * @return
     */
    ISweetSnakePlayer getPlayer2();

    /**
     * 
     */
    void startGame();

    void stopGame();

    /**
     * 
     * @return
     */
    boolean isGameStarted();

    /**
     * 
     * @param player
     * @param direction
     */
    void movePlayer(ISweetSnakePlayer player, SweetSnakeDirection direction);

}
