package be.thebeehive.wouterbauweraerts.bdd.productcatalog.cucumber.steps;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import org.springframework.boot.test.context.SpringBootTest;

import io.cucumber.spring.CucumberContextConfiguration;

@SuppressWarnings("unused")
@CucumberContextConfiguration
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class CucumberTestConfig {
}
