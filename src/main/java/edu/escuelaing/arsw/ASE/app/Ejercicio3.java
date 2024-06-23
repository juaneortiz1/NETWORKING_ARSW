package edu.escuelaing.arsw.ASE.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Main class to create a server that squares numbers sent by a client.
 */
public class Ejercicio3 {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(45000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 45000.");
            System.exit(1);
        }

        Socket clientSocket = null;
        try {
            clientSocket = serverSocket.accept(); // Wait for a client connection
        } catch (IOException e) {
            System.err.println("Accept failed.");
            System.exit(1);
        }

        // Setup input and output streams for communication with the client
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        String inputLine, outputLine;

        // Read numbers sent by the client, square them, and send the response back
        while ((inputLine = in.readLine()) != null) {
            Double number = Double.valueOf(inputLine);
            Double res = Math.pow(number, 2); // Square the number
            String ans = Double.toString(res);
            outputLine = "Respuesta " + ans;
            out.println(outputLine);
            if (outputLine.equals("Respuestas: Bye.")) {
                break;
            }
        }

        // Close streams and sockets
        out.close();
        in.close();
        clientSocket.close();
        serverSocket.close();
    }
}

