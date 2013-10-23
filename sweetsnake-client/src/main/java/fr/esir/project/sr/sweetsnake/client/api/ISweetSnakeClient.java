package fr.esir.project.sr.sweetsnake.client.api;

import fr.esir.project.sr.sweetsnake.commons.api.IElement;

public interface ISweetSnakeClient
{

    void addElement(IElement element);

    void updateElement(IElement element);

    void removeElement(IElement element);

    void setScore(long score);

    long getScore();

    String getName();

}
