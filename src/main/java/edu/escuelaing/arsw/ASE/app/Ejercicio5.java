package edu.escuelaing.arsw.ASE.app;

import java.net.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

/**
 * Main class for a server that handles HTTP requests to serve files and images.
 */
public class Ejercicio5 {
    private static String path = "src/main/resources/index.html";
    private static String pathSearch = "src/main/resources/"; // Path for search requests

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(45000);
            System.out.println("Listo para recibir ...");
        } catch (IOException e) {
            System.err.println("Could not listen on port: 45000.");
            System.exit(1);
        }

        while (true) {
            Socket clientSocket = null;
            try {
                clientSocket = serverSocket.accept();
                handleClient(clientSocket);
            } catch (IOException e) {
                System.err.println("Accept failed.");
                System.exit(1);
            }
        }
    }

    /**
     * Handles the client request.
     *
     * @param clientSocket Socket connected to the client
     * @throws IOException if an I/O error occurs while reading or writing
     */
    private static void handleClient(Socket clientSocket) throws IOException {
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        String inputLine;
        StringBuilder request = new StringBuilder();

        // Read the HTTP request from the client
        while ((inputLine = in.readLine()) != null) {
            request.append(inputLine).append("\n");
            if (!in.ready()) {
                break;
            }
        }

        // Extract the requested path from the HTTP request
        String requestLine = request.toString().split(" ")[1];
        System.out.println("Received: " + requestLine);

        // Determine the response based on the request path
        String response;
        if (requestLine.startsWith("/search")) {
            String file = pathSearch + requestLine.split("=")[1];

            // Handle image files (png, jpg, jpeg) separately with base64 encoding
            if (file.endsWith(".png") || file.endsWith(".jpg") || file.endsWith(".jpeg")) {
                byte[] imageData = getImageContent(file);
                String base64Image = Base64.getEncoder().encodeToString(imageData);

                String htmlResponse = "<!DOCTYPE html>\r\n"
                        + "<html>\r\n"
                        + "    <head>\r\n"
                        + "        <title>Imagen</title>\r\n"
                        + "    </head>\r\n"
                        + "    <body>\r\n"
                        + "         <center><img src=\"data:image/jpeg;base64," + base64Image + "\" alt=\"image\"></center>\r\n"
                        + "    </body>\r\n"
                        + "</html>";

                // Send HTML response with embedded base64 image
                out.println("HTTP/1.1 200 OK");
                out.println("Content-Type: text/html");
                out.println();
                out.println(htmlResponse);
            } else {

                // Send text-based file content (html, css, js, txt)
                response = getFileContent(file);

                out.println("HTTP/1.1 200 OK");
                out.println("Content-Type: " + getContentType(file));
                out.println();
                out.println(response);

            }
        } else {
            // Serve the default index.html
            response = getFile();
            out.println("HTTP/1.1 200 OK");
            out.println("Content-Type: " + getContentType(path));
            out.println();
            out.println(response);
        }

        // Close resources
        out.close();
        in.close();
        clientSocket.close();
    }

    /**
     * Determines the content type based on the file extension.
     *
     * @param fileName Name of the file
     * @return Content type as a string
     */
    private static String getContentType(String fileName) {
        if (fileName.endsWith(".html")) {
            return "text/html";
        } else if (fileName.endsWith(".js")) {
            return "application/javascript";
        } else if (fileName.endsWith(".css")) {
            return "text/css";
        } else if (fileName.endsWith(".txt")) {
            return "text/plain";
        } else if (fileName.endsWith(".png")) {
            return "image/png";
        } else if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
            return "image/jpeg";
        } else {
            return "text/plain";
        }
    }

    /**
     * Reads the content of an image file into a byte array.
     *
     * @param file Path to the image file
     * @return Byte array containing image data
     * @throws IOException if an I/O error occurs while reading the file
     */
    private static byte[] getImageContent(String file) throws IOException {
        Path filePath = Paths.get(file);
        return Files.readAllBytes(filePath);
    }

    /**
     * Reads the content of a text-based file into a string.
     *
     * @param file Path to the file
     * @return Content of the file as a string
     * @throws IOException if an I/O error occurs while reading the file
     */
    private static String getFileContent(String file) throws IOException {
        try {
            return new String(Files.readAllBytes(Paths.get(file)));
        } catch (IOException e) {
            return "File not found";
        }
    }

    /**
     * Reads the content of the default index.html file into a string.
     *
     * @return Content of the index.html file as a string
     * @throws IOException if an I/O error occurs while reading the file
     */
    public static String getFile() throws IOException {
        return new String(Files.readAllBytes(Paths.get(path)));
    }
}
