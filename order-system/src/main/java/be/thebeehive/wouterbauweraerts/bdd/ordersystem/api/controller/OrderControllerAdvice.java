package be.thebeehive.wouterbauweraerts.bdd.ordersystem.api.controller;

import static org.springframework.http.HttpStatus.CONFLICT;

import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import be.thebeehive.wouterbauweraerts.bdd.common.api.response.ServerErrorResponse;
import be.thebeehive.wouterbauweraerts.bdd.ordersystem.api.exception.ProductNotFoundException;

@ControllerAdvice
public class OrderControllerAdvice {

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ServerErrorResponse> handleProductNotFound(ProductNotFoundException e) {
        return ResponseEntity.status(CONFLICT)
                .body(new ServerErrorResponse(e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ServerErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        String errorMessage = "Invalid request. Errors:%n- %s".formatted(
                e.getBindingResult().getFieldErrors().stream()
                        .map(fe -> "%s: %s".formatted(fe.getField(), fe.getDefaultMessage()))
                        .collect(Collectors.joining("%n- "))
        );
        return ResponseEntity.badRequest()
                .body(new ServerErrorResponse(errorMessage));
    }
}
