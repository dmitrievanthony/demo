package tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class SyncTCPClient {

    public void send(String host, int port, String message) throws IOException {
        // Create a new socket and connect to the specified host and port
        Socket socket = new Socket(host, port);

        try {
            // Get the input and output streams for the socket
            InputStream inputStream = socket.getInputStream();
            OutputStream outputStream = socket.getOutputStream();

            // Send the message to the server
            byte[] buffer = message.getBytes();
            outputStream.write(buffer);

            // Wait for the response from the server
            buffer = new byte[1024];
            int bytesRead = inputStream.read(buffer);

            if (bytesRead > 0) {
                // Print the response to the console
                String response = new String(buffer, 0, bytesRead);
                System.out.println("Received: " + response);
            }
        } finally {
            // Close the socket
            socket.close();
        }
    }
}