package edu.escuelaing.arsw.ASE.app;

import java.io.*;
import java.net.*;

public class URLReader {

    public static void main(String[] args) throws Exception {
         URL google = new URL("http://www.google.com/");
         try (BufferedReader reader
                       = new BufferedReader(new InputStreamReader(google.openStream()))) {
             String authority = google.getAuthority();
             String host = google.getHost();
             Integer port = google.getPort();
             String path = google.getPath();
             String query = google.getQuery();
             String file = google.getFile();
             String ref = google.getRef();

             String inputLine = null;

            while ((inputLine = reader.readLine()) != null) {
                 System.out.println(inputLine);
            }
             } catch (IOException x) {
            System.err.println(x);
            }
    }
 }
