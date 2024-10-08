package be.thebeehive.wouterbauweraerts.bdd.ordersystem.util;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withResourceNotFound;

import org.springframework.test.web.client.MockRestServiceServer;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProductOverviewStub {


    public void setupNonExistingProduct(int productId) {
    }
}
