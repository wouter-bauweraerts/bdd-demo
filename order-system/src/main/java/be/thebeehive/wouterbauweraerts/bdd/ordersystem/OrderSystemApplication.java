package be.thebeehive.wouterbauweraerts.bdd.ordersystem;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class OrderSystemApplication {

    @Value("${product.catalog.url:unknown}")
    private String catalogUrl;

    @PostConstruct
    void initialize() {
        log.info("Catalog url: {}", catalogUrl);
    }

    public static void main(String[] args) {
        SpringApplication.run(OrderSystemApplication.class, args);
    }

}
