package be.thebeehive.wouterbauweraerts.bdd.odersystem.cucumber;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.client.MockRestServiceServer;

import io.cucumber.spring.CucumberContextConfiguration;

@CucumberContextConfiguration
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class CucumberSpringContextConfig {
    @Bean
    public MockRestServiceServer mockServer(TestRestTemplate testRestTemplate) {
        return MockRestServiceServer.bindTo(testRestTemplate.getRestTemplate()).build();
    }
}
