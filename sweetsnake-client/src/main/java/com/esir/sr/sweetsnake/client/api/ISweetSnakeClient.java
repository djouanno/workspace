package com.esir.sr.sweetsnake.client.api;

import com.esir.sr.sweetsnake.commons.api.IElement;
import com.esir.sr.sweetsnake.commons.dto.SweetSnakeGameSessionDTO;
import com.esir.sr.sweetsnake.commons.dto.SweetSnakeGameSessionRequestDTO;

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
