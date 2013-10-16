package fr.esir.project.sr.sweetsnake.commons.api;

public interface ISweetSnakeClient
{
    void addElement(IElement element);

    void updateElement(IElement element);

    void removeElement(IElement element);

    void updateScore(long score);
}
