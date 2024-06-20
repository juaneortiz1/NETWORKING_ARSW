package edu.escuelaing.arsw.ASE.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class TrigoServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(35000);
            System.out.println("Server listening on port 35000...");
        } catch (IOException e) {
            System.err.println("Could not listen on port: 35000.");
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
                String currentFunction = "cos";

                while ((inputLine = in.readLine()) != null) {
                    if (inputLine.startsWith("fun:")) {
                        currentFunction = inputLine.substring(4).trim();
                        out.println("Función cambiada a: " + currentFunction);
                    } else {
                        try {
                            Double number = parseInput(inputLine);
                            if (number != null) {
                                Double res = null;

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
                                String outputLine = "Respuesta: " + ans;
                                out.println(outputLine);
                            }
                        } catch (NumberFormatException e) {
                            out.println("Entrada no válida: " + inputLine);
                        }
                    }

                    if (inputLine.equalsIgnoreCase("Bye")) {
                        break;
                    }
                }
            } catch (IOException e) {
                System.err.println("Error handling client request: " + e.getMessage());
            } finally {
                if (out != null) out.close();
                if (in != null) in.close();
                if (clientSocket != null) clientSocket.close();
            }
        }
    }
    private static Double parseInput(String input) {
        input = input.trim().toLowerCase().replace("π", String.valueOf(Math.PI));

        if (input.contains("/")) {
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
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                return null;
            }
        }
    }

}
