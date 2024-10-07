package be.thebeehive.wouterbauweraerts.bdd.odersystem.util;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withResourceNotFound;

import org.springframework.test.web.client.MockRestServiceServer;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProductOverviewStub {
    private final MockRestServiceServer serviceServer;


    public void setupNonExistingProduct(int productId) {
        serviceServer.expect(requestTo("/api/product-overview/%d".formatted(productId)))
                .andRespond(
                        withResourceNotFound()
                                .body("Product with id %d not found".formatted(productId))
                );
    }
}
