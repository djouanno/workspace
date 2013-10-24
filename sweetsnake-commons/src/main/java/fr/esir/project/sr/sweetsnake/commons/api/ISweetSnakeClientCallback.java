package fr.esir.project.sr.sweetsnake.commons.api;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ISweetSnakeClientCallback extends Remote
{

    void requestGame(ISweetSnakeGameSessionRequest request);

    void addElement(IElement element) throws RemoteException;

    void updateElement(IElement element) throws RemoteException;

    void removeElement(IElement element) throws RemoteException;

    void setScore(long score) throws RemoteException;

    String getName() throws RemoteException;

}
