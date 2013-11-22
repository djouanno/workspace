package fr.esir.project.sr.sweetsnake.client;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * 
 */
class RmiClient extends UnicastRemoteObject
{
    protected RmiClient() throws RemoteException {
        super();
    }

    /**
     * @param args
     */
    static public void main(String args[]) {
        ReceiveMessageInterface rmiServer;
        Registry registry;
        String serverAddress = args[0];
        String serverPort = args[1];
        String text = args[2];
        System.out.println("sending " + text + " to " + serverAddress + ":" + serverPort);
        try { // Get the server's stub
            registry = LocateRegistry.getRegistry(serverAddress, (new Integer(serverPort)).intValue());
            rmiServer = (ReceiveMessageInterface) (registry.lookup("rmiServer"));

            // RMI client will give a stub of itself to the server
            Remote aRemoteObj = UnicastRemoteObject.exportObject(new RmiClient(), 0);
            rmiServer.addObserver(aRemoteObj);

            // call the remote method
            rmiServer.receiveMessage(text);
            // update method will be notified
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            System.err.println(e);
        }
    }

    public void update(String a) throws RemoteException {
        // update should take some serializable object as param NOT Observable
        // and Object
        // Server callsbacks here
    }
}