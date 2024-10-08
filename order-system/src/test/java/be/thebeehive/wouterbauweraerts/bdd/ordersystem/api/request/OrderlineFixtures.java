package be.thebeehive.wouterbauweraerts.bdd.ordersystem.api.request;

import static org.instancio.Select.allInts;
import static org.instancio.Select.field;

import org.instancio.Instancio;
import org.instancio.Model;

public class OrderlineFixtures {
    protected static final Model<Orderline> ORDERLINE_MODEL = Instancio.of(Orderline.class)
            .generate(allInts(), gen -> gen.ints().min(1))
            .toModel();

    public static Orderline anOrderline() {
        return Instancio.create(ORDERLINE_MODEL);
    }

    public static Orderline orderline(Integer productId, Integer quantity) {
        return Instancio.of(ORDERLINE_MODEL)
                .set(field(Orderline::productId), productId)
                .set(field(Orderline::quantity), quantity)
                .create();
    }
}