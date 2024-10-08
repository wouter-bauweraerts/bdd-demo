package be.thebeehive.wouterbauweraerts.bdd.ordersystem.cucumber.state;

import java.util.List;

import org.springframework.stereotype.Component;

import be.thebeehive.wouterbauweraerts.bdd.ordersystem.api.request.CreateOrderRequest;
import be.thebeehive.wouterbauweraerts.bdd.ordersystem.domain.OrderlineEntity;
import io.cucumber.spring.ScenarioScope;
import lombok.Data;

@Data
@Component
@ScenarioScope
public class CreateOrderState {
    private Integer orderId;
    private Integer customerId;
    private List<OrderlineEntity> expectedOrderlines;
    private CreateOrderRequest request;
}
