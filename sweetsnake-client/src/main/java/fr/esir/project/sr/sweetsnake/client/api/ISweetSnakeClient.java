package fr.esir.project.sr.sweetsnake.client.api;

import fr.esir.project.sr.sweetsnake.commons.api.IElement;
import fr.esir.project.sr.sweetsnake.commons.api.ISweetSnakeGameSessionRequest;

public interface ISweetSnakeClient
{

    void connect();

    void disconnect();

    void requestGame(ISweetSnakeGameSessionRequest request);

    void addElement(IElement element);

    void updateElement(IElement element);

    void removeElement(IElement element);

    String getName();

    long getScore();

    void setName(String name);

    void setScore(long score);

}
