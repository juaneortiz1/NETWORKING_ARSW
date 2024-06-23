package edu.escuelaing.arsw.ASE.app.RMI;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

/**
 * Implementation of a chat client that connects to a remote chat server using RMI
 */
public class ChatClientImpl extends UnicastRemoteObject implements ChatClient {
    private static final long serialVersionUID = 1L;

    /**
     * Constructor for the chat client implementation.
     *
     * @throws RemoteException If there is a communication-related exception.
     */
    protected ChatClientImpl() throws RemoteException {
        super();
    }

    /**
     * Receives a message from the chat server and prints it to the console.
     *
     * @param message The message received from the server.
     * @throws RemoteException If there is a communication-related exception.
     */
    @Override
    public void receiveMessage(String message) throws RemoteException {
        System.out.println("Received message: " + message);
    }

    /**
     * Main method to start the chat client.
     *
     * @param args Command line arguments. If provided, should be the server IP and port.
     */
    public static void main(String[] args) {
        String serverIP;
        int serverPort;

        // Check if server IP and port are provided
        if (args.length != 2) {
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter server IP: ");
            serverIP = scanner.nextLine();
            System.out.print("Enter server port: ");
            serverPort = Integer.parseInt(scanner.nextLine());
        } else {
            serverIP = args[0];
            serverPort = Integer.parseInt(args[1]);
        }

        try {
            // Construct the RMI URL for the chat server
            String serverUrl = "rmi://" + serverIP + ":" + serverPort + "/ChatServer";

            // Look up the remote chat server object
            ChatServer server = (ChatServer) Naming.lookup(serverUrl);

            // Create an instance of the chat client implementation
            ChatClientImpl client = new ChatClientImpl();

            // Register the client with the chat server
            server.registerClient(client);
            System.out.println("Chat client started.");

            // Start reading messages from the console and send them to the server
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.print("Enter message: ");
                String message = scanner.nextLine();
                server.sendMessage(message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
