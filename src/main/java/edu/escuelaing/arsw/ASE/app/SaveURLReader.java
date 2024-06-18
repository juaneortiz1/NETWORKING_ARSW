package edu.escuelaing.arsw.ASE.app;

import java.io.*;
import java.net.URL;
import java.util.Scanner;

public class SaveURLReader {
    public static void main(String[] args) throws Exception {
        String ans = "";
        System.out.println("Enter url");
        Scanner obj = new Scanner(System.in);
        String res =  obj.nextLine();
        URL url = new URL(res);
        try (BufferedReader reader
                     = new BufferedReader(new InputStreamReader(url.openStream()))) {

            String inputLine = null;

            while ((inputLine = reader.readLine()) != null) {
                ans += inputLine;
            }
        } catch (IOException x) {
            System.err.println(x);
        }
        saveData(ans);

    }
    public static void saveData(String ans){
        File file = new File("results/result.html");
        String data = ans;
        try {FileOutputStream fos = new FileOutputStream(file);
            BufferedOutputStream res = new BufferedOutputStream(fos);
            byte[] bytes = data.getBytes();
            res.write(bytes);
            res.close();
            fos.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
