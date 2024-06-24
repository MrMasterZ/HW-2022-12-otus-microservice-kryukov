package otus.student.kryukov.hw.microservicedocker.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import otus.student.kryukov.hw.microservicedocker.model.Error;

@RestControllerAdvice
public class UserExceptionHandler {

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Error> handleRuntimeException(Exception e){
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR,e);
    }

    private ResponseEntity<Error> buildResponse(HttpStatus status, Exception e){
        return ResponseEntity.status(status).body(new Error().code(500).message(e.getMessage()));
    }

}
