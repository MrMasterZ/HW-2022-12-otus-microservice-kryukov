package otus.student.kryukov.hw.microservicedocker.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import otus.student.kryukov.hw.microservicedocker.model.Health;

import java.text.ParseException;

@RestController
public class HealthController {

    @GetMapping("/health")
    public ResponseEntity<Health> getHealth() throws ParseException {
        return new ResponseEntity<>(new Health("OK"), HttpStatus.OK);
    }
}

