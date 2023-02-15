package http;

import org.springframework.http.ResponseEntity;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.client.AsyncRestTemplate;

public class AsyncRESTClient {

    public static void main(String[] args) {
        // Create an AsyncRestTemplate to make asynchronous REST calls
        AsyncRestTemplate asyncRestTemplate = new AsyncRestTemplate();

        // Make an asynchronous GET request to the /hello endpoint
        ListenableFuture<ResponseEntity<String>> futureResponse = asyncRestTemplate.getForEntity("http://localhost:8080/hello", String.class);

        // Set up a callback to handle the response
        futureResponse.addCallback(new ListenableFutureCallback<ResponseEntity<String>>() {
            @Override
            public void onSuccess(ResponseEntity<String> responseEntity) {
                // Handle a successful response
                System.out.println("Response received: " + responseEntity.getBody());
            }

            @Override
            public void onFailure(Throwable throwable) {
                // Handle a failed response
                System.out.println("Error: " + throwable.getMessage());
            }
        });

        // Wait for the response to be received (in a real application, this would be done in a separate thread)
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}