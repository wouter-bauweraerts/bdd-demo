package be.thebeehive.wouterbauweraerts.bdd.ordersystem.api.request;

import static be.thebeehive.wouterbauweraerts.bdd.ordersystem.api.request.OrderlineFixtures.ORDERLINE_MODEL;
import static java.lang.Math.abs;
import static java.util.Collections.emptyList;
import static org.instancio.Select.allInts;
import static org.instancio.Select.field;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.instancio.Instancio;
import org.instancio.Model;

import io.github.wouterbauweraerts.instancio.fixture.builder.AbstractFixtureBuilder;

public class CreateOrderRequestFixtures {
    private static final Model<CreateOrderRequest> CREATE_ORDER_REQUEST_MODEL = Instancio.of(CreateOrderRequest.class)
            .generate(allInts(), gen -> gen.ints().min(1))
            .supply(field(CreateOrderRequest::orderlines),
                    () -> Instancio.ofList(ORDERLINE_MODEL)
                            .size(3)
                            .create()
            )
            .toModel();

    public static CreateOrderRequest aCreateOrderRequest() {
        return Instancio.create(CREATE_ORDER_REQUEST_MODEL);
    }

    public static FixtureBuilder fixtureBuilder() {
        return new FixtureBuilder();
    }

    public static CreateOrderRequest aCreateOrderRequestWithSingleProduct() {
        return fixtureBuilder()
                .withOrderlines(List.of(OrderlineFixtures.anOrderline()))
                .build();
    }

    public static class FixtureBuilder extends AbstractFixtureBuilder<CreateOrderRequest, FixtureBuilder> {

        @Override
        public CreateOrderRequest build() {
            return buildInternal(CREATE_ORDER_REQUEST_MODEL);
        }

        @Override
        public FixtureBuilder self() {
            return this;
        }

        public FixtureBuilder withCustomerId(Integer customerId) {
            return setField(CreateOrderRequest::customerId, customerId);
        }

        public FixtureBuilder ignoreCustomerId() {
            return ignoreField(CreateOrderRequest::customerId);
        }

        public FixtureBuilder withOrderlines(List<Orderline> orderlines) {
            return setField(CreateOrderRequest::orderlines, orderlines);
        }

        public FixtureBuilder ignoreOrderlines() {
            return setField(CreateOrderRequest::orderlines, emptyList());

        }
    }
}