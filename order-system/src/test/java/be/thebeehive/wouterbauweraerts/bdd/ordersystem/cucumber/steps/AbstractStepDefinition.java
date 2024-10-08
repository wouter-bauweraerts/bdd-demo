package be.thebeehive.wouterbauweraerts.bdd.ordersystem.cucumber.steps;

import org.springframework.boot.test.web.client.TestRestTemplate;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractStepDefinition {
    protected final TestRestTemplate restTemplate;
}
