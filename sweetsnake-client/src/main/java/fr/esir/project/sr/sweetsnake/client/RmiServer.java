package fr.esir.project.sr.sweetsnake.client;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.Observable;
import java.util.Observer;

/**
 * 
 */
class RmiServer extends Observable implements ReceiveMessageInterface
{
    String   address;
    Registry registry;

    /**
     * {@inheritDoc}
     */
    public void receiveMessage(String x) throws RemoteException {
        System.out.println(x);
        setChanged();
        notifyObservers(x + "invoked me");
    }

    /**
     * {@inheritDoc}
     */
    public void addObserver(final Remote observer) throws RemoteException {
        // This is where you plug in client's stub
        super.addObserver(new Observer() {
            public void update(Observable o, Object arg) {
                try {
                    ((RmiClient) observer).update((String) arg);
                } catch (RemoteException e) {

                }
            }
        });
    }

    /**
     * @throws RemoteException
     */
    public RmiServer() throws RemoteException {
        try {
            address = (InetAddress.getLocalHost()).toString();
        } catch (Exception e) {
            System.out.println("can't get inet address.");
        }
        int port = 3232;
        System.out.println("this address=" + address + ",port=" + port);
        try {
            Naming.rebind("rmiServer", this);
        } catch (RemoteException e) {
            System.out.println("remote exception" + e);
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 
     * @param args
     */
    static public void main(String args[]) {
        try {
            RmiServer server = new RmiServer();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}