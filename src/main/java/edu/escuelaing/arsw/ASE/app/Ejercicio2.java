package edu.escuelaing.arsw.ASE.app;

import java.io.*;
import java.net.URL;
import java.util.Scanner;

/**
 * Main class to read content from a URL and save it to a file.
 */
public class Ejercicio2 {

    /**
     * Main method to execute the program.
     * Prompts the user to enter a URL, reads its content, and saves it to a file.
     *
     * @param args Command line arguments (not used)
     * @throws Exception if an error occurs during URL processing or file saving
     */
    public static void main(String[] args) throws Exception {
        String content = "";

        // Prompt for a URL
        System.out.println("Enter url");
        Scanner scanner = new Scanner(System.in);
        String urlString = scanner.nextLine();
        URL url = new URL(urlString);

        // Read content from the URL
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()))) {
            String inputLine;
            while ((inputLine = reader.readLine()) != null) {
                content += inputLine;
            }
        } catch (IOException e) {
            System.err.println("Error reading URL content: " + e.getMessage());
        }

        // Save content to a file
        saveData(content);
    }

    /**
     * Saves the provided data to a file.
     *
     * @param data Content to be saved in the file
     */
    public static void saveData(String data) {
        File file = new File("results/result.html");  // File path
        try (FileOutputStream fos = new FileOutputStream(file);
             BufferedOutputStream bos = new BufferedOutputStream(fos)) {
            byte[] bytes = data.getBytes();
            bos.write(bytes);  // Write bytes to the file
            System.out.println("Data saved successfully to: " + file.getAbsolutePath());
        } catch (IOException e) {
            throw new RuntimeException("Error saving data to file: " + e.getMessage(), e);
        }
    }
}

