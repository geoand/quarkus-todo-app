package io.quarkus.sample;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;

@RestController
@RequestMapping("/")
public class IndexController {

    @GetMapping
    public ResponseEntity<Void> redirect() {
        URI redirect = UriBuilder.fromUri("todo.html").build();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(redirect);
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }
}
