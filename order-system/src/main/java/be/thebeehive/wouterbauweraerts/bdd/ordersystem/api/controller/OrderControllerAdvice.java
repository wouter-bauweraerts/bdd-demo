package be.thebeehive.wouterbauweraerts.bdd.ordersystem.api.controller;

import static org.springframework.http.HttpStatus.CONFLICT;

import org.springframework.http.ResponseEntity;
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
}
