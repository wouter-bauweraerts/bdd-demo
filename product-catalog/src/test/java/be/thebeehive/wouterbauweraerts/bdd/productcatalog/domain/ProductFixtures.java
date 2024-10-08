package be.thebeehive.wouterbauweraerts.bdd.productcatalog.domain;


import static org.instancio.Select.allInts;

import org.instancio.Instancio;
import org.instancio.Model;

import io.github.wouterbauweraerts.instancio.fixture.builder.AbstractFixtureBuilder;


public class ProductFixtures {
    public static final Model<Product> PRODUCT_MODEL = Instancio.of(Product.class)
            .generate(allInts(), gen -> gen.ints().min(1))
            .toModel();

    public static Product aProduct() {
        return Instancio.create(PRODUCT_MODEL);
    }

    public static FixtureBuilder fixtureBuilder(){
        return new FixtureBuilder();
    }

    public static class FixtureBuilder extends AbstractFixtureBuilder<Product, FixtureBuilder> {

        @Override
        public Product build() {
            return buildInternal(PRODUCT_MODEL);
        }

        @Override
        public FixtureBuilder self() {
            return this;
        }

        public FixtureBuilder ignoreId() {
            return withId(null);
        }

        public FixtureBuilder ignoreBrand() {
            return setField(Product::getBrand, null);
        }

        public FixtureBuilder ignoreType() {
            return setField(Product::getType, null);
        }

        public FixtureBuilder withId(Integer productId) {
            return setField(Product::getId, productId);
        }
    }
}