package be.thebeehive.wouterbauweraerts.bdd.ordersystem.productcatalog.dto;

import org.instancio.Instancio;
import org.instancio.Model;

public class ProductFixtures {
    private static final Model<Product> PRODUCT_MODEL = Instancio.of(Product.class).toModel();

    public static Product aProduct() {
        return Instancio.create(PRODUCT_MODEL);
    }
}