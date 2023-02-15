package tcp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class AsyncTCPClient {

    public void send(String host, int port, String message) throws IOException {
        // Create a new socket channel and connect to the specified host and port
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress(host, port));

        // Wait for the connection to be established
        while (!socketChannel.finishConnect()) {
            // Do nothing
        }

        // Send the message
        ByteBuffer buffer = ByteBuffer.wrap(message.getBytes());
        socketChannel.write(buffer);

        // Wait for the response
        buffer.clear();
        int bytesRead = socketChannel.read(buffer);

        if (bytesRead > 0) {
            // Print the response to the console
            buffer.flip();
            byte[] bytes = new byte[buffer.remaining()];
            buffer.get(bytes);
            System.out.println("Received: " + new String(bytes));
        }

        // Close the socket channel
        socketChannel.close();
    }
}