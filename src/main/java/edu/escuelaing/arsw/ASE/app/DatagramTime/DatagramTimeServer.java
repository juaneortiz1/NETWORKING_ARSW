package edu.escuelaing.arsw.ASE.app.DatagramTime;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * UDP server that responds to client requests with the current date and time.
 */
public class DatagramTimeServer {

    DatagramSocket socket;

    /**
     * Constructs a DatagramTimeServer and initializes it to listen on port 4445.
     */
    public DatagramTimeServer() {
        try {
            socket = new DatagramSocket(4445); // Server socket bound to port 4445
        } catch (SocketException ex) {
            Logger.getLogger(DatagramTimeServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Starts the UDP server to respond to client requests with the current date and time.
     */
    public void startServer() {
        byte[] buf = new byte[256];
        try {
            // Receive client request
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            socket.receive(packet);

            // Get current date and time as a string
            String dString = new Date().toString();
            buf = dString.getBytes();

            // Send response back to the client
            InetAddress address = packet.getAddress();
            int port = packet.getPort();
            packet = new DatagramPacket(buf, buf.length, address, port);
            socket.send(packet);

        } catch (IOException ex) {
            Logger.getLogger(DatagramTimeServer.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Close the socket after serving the client
        socket.close();
    }

    /**
     * Main method to continuously run the DatagramTimeServer.
     *
     * @param args Command-line arguments (not used)
     */
    public static void main(String[] args) {
        while(true) {
            DatagramTimeServer ds = new DatagramTimeServer();
            ds.startServer();
        }
    }
}

