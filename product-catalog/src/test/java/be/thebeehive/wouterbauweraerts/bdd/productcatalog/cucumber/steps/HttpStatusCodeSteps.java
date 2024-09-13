package be.thebeehive.wouterbauweraerts.bdd.productcatalog.cucumber.steps;

import static be.thebeehive.wouterbauweraerts.bdd.productcatalog.assertions.HttpStatusAssert.assertStatusCode;

import org.springframework.beans.factory.annotation.Autowired;

import be.thebeehive.wouterbauweraerts.bdd.productcatalog.cucumber.state.HttpStatusState;
import io.cucumber.java.en.Then;

public class HttpStatusCodeSteps {
    @Autowired
    HttpStatusState statusInformation;

    @Then("I receive status {int}")
    public void validateStatus(int expectedStatus) {
        assertStatusCode(statusInformation.getStatusCode()).isStatus(expectedStatus);
    }
}
