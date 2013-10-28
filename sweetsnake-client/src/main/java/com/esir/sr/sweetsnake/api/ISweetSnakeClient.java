package com.esir.sr.sweetsnake.api;

import com.esir.sr.sweetsnake.api.IElement;
import com.esir.sr.sweetsnake.dto.SweetSnakeGameSessionDTO;
import com.esir.sr.sweetsnake.dto.SweetSnakeGameSessionRequestDTO;

public interface ISweetSnakeClient
{

    void connect();

    void disconnect();

    void requestGame(SweetSnakeGameSessionRequestDTO request);

    void startGame(SweetSnakeGameSessionDTO session);

    void addElement(IElement element);

    void updateElement(IElement element);

    void removeElement(IElement element);

    String getName();

    long getScore();

    void setName(String name);

    void setScore(long score);

}
