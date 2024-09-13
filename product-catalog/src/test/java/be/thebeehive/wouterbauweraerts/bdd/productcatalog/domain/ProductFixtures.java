package be.thebeehive.wouterbauweraerts.bdd.productcatalog.domain;


import static org.instancio.Select.allInts;

import org.instancio.Instancio;
import org.instancio.Model;

public class ProductFixtures {
    public static final Model<Product> PRODUCT_MODEL = Instancio.of(Product.class)
            .generate(allInts(), gen -> gen.ints().min(1))
            .toModel();

    public Product aProduct() {
        return Instancio.create(PRODUCT_MODEL);
    }
}