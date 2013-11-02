package com.esir.sr.sweetsnake.api;

import com.esir.sr.sweetsnake.enumeration.SweetSnakeDirection;

public interface ISweetSnakeIhm
{

    void successfullyConnected();

    void moveSnake(SweetSnakeDirection direction);

    void swithView(ISweetSnakeView view);

    void refreshUI();

}
