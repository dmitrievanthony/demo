package tcp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Set;

public class AsyncTCPServer {
    private Selector selector;

    public void start(int port) throws IOException {
        // Create a new selector
        selector = Selector.open();

        // Create a new server socket channel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);

        // Bind the server socket channel to the specified port
        serverSocketChannel.socket().bind(new InetSocketAddress(port));

        // Register the server socket channel with the selector and specify that we are interested in accepting new connections
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        // Main server loop
        while (true) {
            // Wait for events
            selector.select();

            // Get the set of selected keys
            Set<SelectionKey> selectedKeys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = selectedKeys.iterator();

            while (keyIterator.hasNext()) {
                SelectionKey key = keyIterator.next();

                if (key.isAcceptable()) {
                    // A new connection is available
                    ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
                    SocketChannel socketChannel = serverChannel.accept();
                    socketChannel.configureBlocking(false);

                    // Register the new socket channel with the selector and specify that we are interested in reading from it
                    socketChannel.register(selector, SelectionKey.OP_READ);
                } else if (key.isReadable()) {
                    // A socket channel has data to read
                    SocketChannel socketChannel = (SocketChannel) key.channel();

                    // Read the data into a buffer
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    int bytesRead = socketChannel.read(buffer);

                    if (bytesRead > 0) {
                        // Print the received data to the console
                        buffer.flip();
                        byte[] bytes = new byte[buffer.remaining()];
                        buffer.get(bytes);
                        System.out.println("Received: " + new String(bytes));
                    }
                }

                // Remove the key from the selected key set to avoid processing it again
                keyIterator.remove();
            }
        }
    }
}