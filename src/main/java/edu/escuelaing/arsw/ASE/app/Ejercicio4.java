package edu.escuelaing.arsw.ASE.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Main class for a server that performs trigonometric calculations based on the function selected by the client.
 */
public class Ejercicio4 {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(45000); // Server listens on port 45000
            System.out.println("Server listening on port 45000...");
        } catch (IOException e) {
            System.err.println("Could not listen on port: 45000.");
            System.exit(1);
        }

        while (true) {
            Socket clientSocket = null;
            BufferedReader in = null;
            PrintWriter out = null;

            try {
                clientSocket = serverSocket.accept();
                System.out.println("Accepted connection from client.");

                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new PrintWriter(clientSocket.getOutputStream(), true);

                String inputLine;
                String currentFunction = "cos"; // Default function

                while ((inputLine = in.readLine()) != null) {
                    if (inputLine.startsWith("fun:")) {
                        // Change trigonometric function
                        currentFunction = inputLine.substring(4).trim();
                        out.println("Función cambiada a: " + currentFunction);
                    } else {
                        try {
                            Double number = parseInput(inputLine); // Parse user input
                            if (number != null) {
                                Double res = null;

                                // Perform calculation on the selected function
                                switch (currentFunction) {
                                    case "sin":
                                        res = Math.sin(number);
                                        break;
                                    case "cos":
                                        res = Math.cos(number);
                                        break;
                                    case "tan":
                                        res = Math.tan(number);
                                        break;
                                    default:
                                        out.println("Función desconocida: " + currentFunction);
                                        continue;
                                }

                                if (Math.abs(res) < 1e-10) {
                                    res = 0.0;
                                }

                                String ans = Double.toString(res);
                                out.println("Respuesta: " + ans);
                            }
                        } catch (NumberFormatException e) {
                            out.println("Entrada no válida: " + inputLine); // Invalid input format
                        }
                    }

                    if (inputLine.equalsIgnoreCase("Bye")) {
                        break; // Exit loop if client sends "Bye"
                    }
                }
            } catch (IOException e) {
                System.err.println("Error handling client request: " + e.getMessage());
            } finally {
                // Close resources
                if (out != null) out.close();
                if (in != null) in.close();
                if (clientSocket != null) clientSocket.close();
            }
        }
    }

    /**
     * Parses user input, supporting basic operations.
     *
     * @param input Input as a string.
     * @return The parsed result as Double, or null if input is invalid.
     */
    private static Double parseInput(String input) {
        input = input.trim().toLowerCase().replace("π", String.valueOf(Math.PI));

        if (input.contains("/")) {
            // Division operation
            String[] parts = input.split("/");
            if (parts.length == 2) {
                try {
                    double numerator = Double.parseDouble(parts[0].trim());
                    double denominator = Double.parseDouble(parts[1].trim());
                    return numerator / denominator;
                } catch (NumberFormatException e) {
                    return null;
                }
            } else {
                return null;
            }
        } else if (input.contains("*")) {
            // Multiplication operation
            String[] parts = input.split("\\*");
            if (parts.length == 2) {
                try {
                    double factor1 = Double.parseDouble(parts[0].trim());
                    double factor2 = Double.parseDouble(parts[1].trim());
                    return factor1 * factor2;
                } catch (NumberFormatException e) {
                    return null;
                }
            } else {
                return null;
            }
        } else {
            // Single number or invalid input
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                return null;
            }
        }
    }
}
