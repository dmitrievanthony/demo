package http;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class SyncRESTServer {

    // Define a REST endpoint to handle GET requests to /hello
    @GetMapping("/hello")
    public String hello() {
        return "Hello, world!";
    }

    public static void main(String[] args) {
        SpringApplication.run(SyncRESTServer.class, args);
    }
}