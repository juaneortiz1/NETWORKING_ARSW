package edu.escuelaing.arsw.ASE.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Scanner;

/**
 * retrieves the content from the specified URL
 */
public class Ejercicio1 {
    /**
     * Main method to execute the program.
     * Prompts the user to enter a URL, retrieves the content from the URL,
     * and prints various components of the URL.
     *
     * @param args Command line arguments (not used)
     * @throws Exception if an error occurs during URL processing
     */
    public static void main(String[] args) throws Exception {
        // Requests URL
        System.out.println("Enter url");
        Scanner obj = new Scanner(System.in);
        String res = obj.nextLine();
        URL url = new URL(res);

        // Ensure the BufferedReader is closed after use, try-catch
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()))) {

            String authority = url.getAuthority();
            String host = url.getHost();
            Integer port = url.getPort();
            String path = url.getPath();
            String query = url.getQuery();
            String file = url.getFile();
            String ref = url.getRef();

            String inputLine;

            // Read and print each line from the URL's content
            while ((inputLine = reader.readLine()) != null) {
                System.out.println(inputLine);
                System.out.println(authority);
                System.out.println(host);
                System.out.println(port);
                System.out.println(path);
                System.out.println(query);
                System.out.println(file);
                System.out.println(ref);
            }
        } catch (IOException x) {
            System.err.println(x);
        }
    }
}

