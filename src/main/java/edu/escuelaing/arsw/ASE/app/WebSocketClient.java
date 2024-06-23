package edu.escuelaing.arsw.ASE.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * client program that connects to a server
 */
public class WebSocketClient {
    public static void main(String[] args) throws IOException {
        Socket echoSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try {

            echoSocket = new Socket("127.0.0.1", 45000);
            out = new PrintWriter(echoSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Don’t know about host 127.0.0.1.");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: 127.0.0.1.");
            System.exit(1);
        }

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String userInput;

        // Read commands from the user and send them to the server
        System.out.println("Enter commands to send to the server");
        while ((userInput = stdIn.readLine()) != null) {
            out.println(userInput); // Send command to the server
            String serverResponse = in.readLine(); // Read response from the server
            if (serverResponse != null) {
                System.out.println("Server response: " + serverResponse); // Print server response
            }
        }

        // Close all resources
        out.close();
        in.close();
        stdIn.close();
        echoSocket.close();
    }
}
