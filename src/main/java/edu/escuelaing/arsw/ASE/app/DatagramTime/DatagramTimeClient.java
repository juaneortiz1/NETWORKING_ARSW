package edu.escuelaing.arsw.ASE.app.DatagramTime;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * UDP client that sends requests to a DatagramTimeServer to fetch the current date and time.
 */
public class DatagramTimeClient {

    public static void main(String[] args) {
        byte[] sendBuf = new byte[256];
        String lastone = "Not yet";
        try {
            DatagramSocket socket = new DatagramSocket(); // Client socket
            socket.setSoTimeout(5000); // Timeout for socket
            byte[] buf = new byte[256];
            InetAddress address = InetAddress.getByName("127.0.0.1");

            while(true) {
                try {
                    // Send request to server
                    DatagramPacket packet = new DatagramPacket(sendBuf, sendBuf.length, address, 4445);
                    socket.send(packet);

                    // Receive response from server
                    packet = new DatagramPacket(buf, buf.length);
                    socket.receive(packet);
                    String received = new String(packet.getData(), 0, packet.getLength());
                    lastone = received;
                    System.out.println("Date: " + received);
                } catch(IOException ex) {
                    // Handle timeout or other IO exceptions
                    System.out.println("Last date:" + lastone);
                }

                // Wait before sending the next request
                Thread.sleep(5000);
            }
        } catch (SocketException | UnknownHostException ex) {
            Logger.getLogger(DatagramTimeClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(DatagramTimeClient.class.getName()).log(Level.SEVERE, null, ex);
            Thread.currentThread().interrupt();
        }
    }
}
