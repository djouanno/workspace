package com.esir.sr.sweetsnake.api;

import java.rmi.Remote;
import java.rmi.RemoteException;

import com.esir.sr.sweetsnake.dto.SweetSnakeGameRequestDTO;
import com.esir.sr.sweetsnake.dto.SweetSnakeGameSessionDTO;
import com.esir.sr.sweetsnake.enumeration.SweetSnakeDirection;

public interface ISweetSnakeClientCallback extends Remote
{

    /**
     * 
     * @param request
     * @throws RemoteException
     */
    void requestGame(SweetSnakeGameRequestDTO request) throws RemoteException;

    /**
     * 
     * @param session
     * @throws RemoteException
     */
    void startGame(SweetSnakeGameSessionDTO session) throws RemoteException;


    /**
     * 
     * @param direction
     * @throws RemoteException
     */
    void confirmMove(SweetSnakeDirection direction) throws RemoteException;

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
