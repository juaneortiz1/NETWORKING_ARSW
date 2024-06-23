package edu.escuelaing.arsw.ASE.app.RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Remote interface for a chat client to receive messages from a server.
 */
public interface ChatClient extends Remote {

    /**
     * Receives a message from the chat server.
     *
     * @param message The message received from the server.
     * @throws RemoteException If there is a communication-related exception.
     */
    void receiveMessage(String message) throws RemoteException;
}
