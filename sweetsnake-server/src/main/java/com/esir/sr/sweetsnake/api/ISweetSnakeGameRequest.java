package com.esir.sr.sweetsnake.api;

public interface ISweetSnakeGameRequest
{

    ISweetSnakePlayer getRequestingPlayer();

    ISweetSnakePlayer getRequestedPlayer();

    void cancel();

}
