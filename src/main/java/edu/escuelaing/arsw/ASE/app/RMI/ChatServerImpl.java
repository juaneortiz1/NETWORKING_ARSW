package edu.escuelaing.arsw.ASE.app.RMI;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of a chat server using RMI
 */
public class ChatServerImpl extends UnicastRemoteObject implements ChatServer {
    private List<ChatClient> clients;

    /**
     * Constructor for the chat server implementation.
     *
     * @throws RemoteException If there is a communication-related exception.
     */
    public ChatServerImpl() throws RemoteException {
        clients = new ArrayList<>();
    }

    /**
     * Sends a message to all connected clients.
     *
     * @param message The message to send.
     * @throws RemoteException If there is a communication-related exception.
     */
    @Override
    public void sendMessage(String message) throws RemoteException {
        System.out.println("New message: " + message);
        for (ChatClient client : clients) {
            try {
                client.receiveMessage(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Registers a client with the chat server.
     *
     * @param client The client to register.
     * @throws RemoteException If there is a communication-related exception.
     */
    @Override
    public void registerClient(ChatClient client) throws RemoteException {
        clients.add(client);
        System.out.println("A new client has connected.");
    }

    /**
     * Main method to start the chat server.
     *
     * @param args Command line arguments. If provided, should be the RMI registry port.
     */
    public static void main(String[] args) {
        try {
            // Determine the RMI registry port
            int port;
            if (args.length > 0) {
                port = Integer.parseInt(args[0]);
            } else {
                port = 1099;
            }

            // Create the RMI registry
            LocateRegistry.createRegistry(port);

            // Create an instance of the chat server implementation
            ChatServerImpl server = new ChatServerImpl();

            // Bind the server instance to the RMI registry
            Naming.rebind("rmi://localhost:" + port + "/ChatServer", server);

            System.out.println("Chat server started on port " + port + ".");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
