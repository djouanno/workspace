package com.esir.sr.sweetsnake.api;

import com.esir.sr.sweetsnake.dto.SweetSnakePlayerDTO;
import com.esir.sr.sweetsnake.enumeration.SweetSnakeDirection;

public interface ISweetSnakeIhm
{

    void successfullyConnected();

    void requestGame(SweetSnakePlayerDTO requestedPlayer);

    void moveSnake(SweetSnakeDirection direction);

    void displayInfoMessage(String message);

    void displayErrorMessage(String message);

    void refreshUI();

}
