package com.esir.sr.sweetsnake.api;

import com.esir.sr.sweetsnake.enumeration.MoveDirection;

public interface IGameSession
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
    IPlayer getPlayer1();

    /**
     * 
     * @return
     */
    IPlayer getPlayer2();

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
    void movePlayer(IPlayer player, MoveDirection direction);

}
