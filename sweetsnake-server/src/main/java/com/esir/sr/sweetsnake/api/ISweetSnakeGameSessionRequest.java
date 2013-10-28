package com.esir.sr.sweetsnake.api;

public interface ISweetSnakeGameSessionRequest
{

    ISweetSnakePlayer getRequestingPlayer();

    ISweetSnakePlayer getRequestedPlayer();

}
