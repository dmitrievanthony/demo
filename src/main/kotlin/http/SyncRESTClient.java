package http;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SyncRESTClient {

    public static void main(String[] args) {
        try {
            // Set up a connection to the server
            URL url = new URL("http://localhost:8080/hello");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Read the response from the server
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            // Print the response from the server
            System.out.println(response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}