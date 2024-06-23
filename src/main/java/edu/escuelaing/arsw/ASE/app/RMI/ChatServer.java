package edu.escuelaing.arsw.ASE.app.RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Remote interface for a chat server that sends messages to clients and registers clients for message reception.
 */
public interface ChatServer extends Remote {

    /**
     * Sends a message to all registered clients.
     *
     * @param message The message to be sent to clients.
     * @throws RemoteException If there is a communication-related exception.
     */
    void sendMessage(String message) throws RemoteException;

    /**
     * Registers a client with the server for receiving messages.
     *
     * @param client The client to be registered.
     * @throws RemoteException If there is a communication-related exception.
     */
    void registerClient(ChatClient client) throws RemoteException;
}
