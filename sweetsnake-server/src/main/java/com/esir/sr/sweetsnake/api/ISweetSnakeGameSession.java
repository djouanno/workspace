package com.esir.sr.sweetsnake.api;

import com.esir.sr.sweetsnake.enumeration.Direction;

public interface ISweetSnakeGameSession
{

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
    void movePlayer(ISweetSnakePlayer player, Direction direction);

}
