package edu.escuelaing.arsw.ASE.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class WebSocketClient {
    public static void main(String[] args) throws IOException {
        Socket echoSocket = null;
        PrintWriter out = null;
        BufferedReader in = null;

        try {
            echoSocket = new Socket("127.0.0.1", 35000);
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

        System.out.println("Enter commands to send to the server ('fun:...' to change function, numbers to compute):");
        while ((userInput = stdIn.readLine()) != null) {
            out.println(userInput);
            String serverResponse = in.readLine();
            if (serverResponse != null) {
                System.out.println("Server response: " + serverResponse);
            }
        }

        out.close();
        in.close();
        stdIn.close();
        echoSocket.close();
    }
}
