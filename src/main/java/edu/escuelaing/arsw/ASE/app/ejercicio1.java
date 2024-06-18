package edu.escuelaing.arsw.ASE.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Scanner;

public class ejercicio1 {
    public static void main(String[] args) throws Exception {
        System.out.println("Enter url");
        Scanner obj = new Scanner(System.in);
        String res =  obj.nextLine();
        URL url = new URL(res);
        try (BufferedReader reader
                     = new BufferedReader(new InputStreamReader(url.openStream()))) {
            String authority = url.getAuthority();
            String host = url.getHost();
            Integer port = url.getPort();
            String path = url.getPath();
            String query = url.getQuery();
            String file = url.getFile();
            String ref = url.getRef();

            String inputLine = null;

            while ((inputLine = reader.readLine()) != null) {
                System.out.println(inputLine);
                System.out.println(authority);
                System.out.println(host);
                System.out.printf(String.valueOf(port));
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
