package be.thebeehive.wouterbauweraerts.bdd.odersystem.cucumber.steps;

import static be.thebeehive.wouterbauweraerts.bdd.common.assertions.HttpStatusAssert.assertStatusCode;
import static org.assertj.core.api.Assertions.assertThat;

import be.thebeehive.wouterbauweraerts.bdd.odersystem.cucumber.state.HttpResponseState;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class HttpResponseSteps {
    private final HttpResponseState responseState;

    @Then("I receive status {int}")
    public void validateStatusCode(int statusCode) {
        assertStatusCode(responseState.getStatusCode().value()).isStatus(statusCode);
    }

    @And("I receive the message {}")
    public void iReceiveTheMessageErrormessage(String errorMessage) {
        assertThat(responseState.getMessage()).withFailMessage(() -> "Expected Http error message '%s' to be '%s'".formatted(
                responseState.getMessage(),
                errorMessage
        )).isEqualTo(errorMessage);
    }
}
