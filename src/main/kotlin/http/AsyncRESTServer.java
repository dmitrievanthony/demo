package http;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

@SpringBootApplication
@RestController
@EnableAsync
public class AsyncRESTServer {

    // Define a REST endpoint to handle GET requests to /hello
    @GetMapping("/hello")
    public DeferredResult<String> hello() {
        // Create a DeferredResult to handle the async response
        DeferredResult<String> deferredResult = new DeferredResult<>();

        // Start an async task to generate the response
        new Thread(() -> {
            // Simulate a long-running task by sleeping for 5 seconds
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Set the response value on the DeferredResult
            deferredResult.setResult("Hello, world!");
        }).start();

        // Return the DeferredResult to the client
        return deferredResult;
    }

    public static void main(String[] args) {
        SpringApplication.run(AsyncRESTServer.class, args);
    }
}