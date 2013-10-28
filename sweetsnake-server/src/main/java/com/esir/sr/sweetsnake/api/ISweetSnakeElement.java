package com.esir.sr.sweetsnake.api;

import com.esir.sr.sweetsnake.enumeration.Direction;

public interface ISweetSnakeElement
{

    void move(Direction direction);

    void setX(int x);

    void setY(int y);

    void setXY(int x, int y);

}
