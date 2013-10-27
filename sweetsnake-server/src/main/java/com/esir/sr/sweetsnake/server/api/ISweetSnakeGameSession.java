package com.esir.sr.sweetsnake.server.api;

public interface ISweetSnakeGameSession
{

    public ISweetSnakePlayer getPlayer1();

    public ISweetSnakePlayer getPlayer2();

    public void startGame();

}
