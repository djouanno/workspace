package fr.esir.project.sr.sweetsnake.client;

import java.rmi.Remote;
import java.rmi.RemoteException;

interface ReceiveMessageInterface extends Remote
{
    /**
     * @param x
     * @throws RemoteException
     */
    void receiveMessage(String x) throws RemoteException;

    /**
     * @param observer
     * @throws RemoteException
     */
    void addObserver(Remote observer) throws RemoteException;
}