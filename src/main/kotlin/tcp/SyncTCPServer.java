package tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SyncTCPServer {

    public void start(int port) throws IOException {
        // Create a new server socket and bind it to the specified port
        ServerSocket serverSocket = new ServerSocket(port);

        // Main server loop
        while (true) {
            // Wait for a new client connection
            Socket clientSocket = serverSocket.accept();

            // Handle the client connection in a separate thread
            Thread thread = new Thread(() -> {
                try {
                    // Get the input and output streams for the client socket
                    InputStream inputStream = clientSocket.getInputStream();
                    OutputStream outputStream = clientSocket.getOutputStream();

                    // Read the request from the client
                    byte[] buffer = new byte[1024];
                    int bytesRead = inputStream.read(buffer);

                    if (bytesRead > 0) {
                        // Print the request to the console
                        String request = new String(buffer, 0, bytesRead);
                        System.out.println("Received: " + request);

                        // Send a response to the client
                        String response = "Hello, client!";
                        outputStream.write(response.getBytes());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        // Close the client socket
                        clientSocket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            // Start the client thread
            thread.start();
        }
    }
}