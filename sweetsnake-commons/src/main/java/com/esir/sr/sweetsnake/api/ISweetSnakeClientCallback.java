package com.esir.sr.sweetsnake.api;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.esir.sr.sweetsnake.dto.SweetSnakeGameRequestDTO;
import com.esir.sr.sweetsnake.dto.SweetSnakeGameSessionDTO;

public interface ISweetSnakeClientCallback extends Remote
{

    /**
     * 
     * @param request
     */
    void requestGame(SweetSnakeGameRequestDTO request);

    /**
     * 
     * @param session
     */
    void startGame(SweetSnakeGameSessionDTO session);

    /**
     * 
     * @param element
     * @throws RemoteException
     */
    void addElement(IElement element) throws RemoteException;

    /**
     * 
     * @param element
     * @throws RemoteException
     */
    void updateElement(IElement element) throws RemoteException;

    /**
     * 
     * @param element
     * @throws RemoteException
     */
    void removeElement(IElement element) throws RemoteException;

    /**
     * 
     * @param score
     * @throws RemoteException
     */
    void setScore(long score) throws RemoteException;

    /**
     * 
     * @return
     * @throws RemoteException
     */
    String getName() throws RemoteException;

}
