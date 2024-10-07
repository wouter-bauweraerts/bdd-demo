package be.thebeehive.wouterbauweraerts.bdd.productcatalog.api.requests;

import org.instancio.Instancio;
import org.instancio.Model;

import be.thebeehive.wouterbauweraerts.bdd.productcatalog.domain.ProductFixtures;
import io.github.wouterbauweraerts.instancio.fixture.builder.AbstractFixtureBuilder;

public class AddProductRequestFixtures {
    private static final Model<AddProductRequest> ADD_PRODUCT_REQUEST_MODEL = Instancio.of(AddProductRequest.class)
            .toModel();

    public static AddProductRequest anAddProductRequest() {
        return Instancio.create(ADD_PRODUCT_REQUEST_MODEL);
    }

    public static FixtureBuilder fixtureBuilder() {
        return new FixtureBuilder();
    }

    public static class FixtureBuilder extends AbstractFixtureBuilder<AddProductRequest, FixtureBuilder> {
        @Override
        public AddProductRequest build() {
            return buildInternal(ADD_PRODUCT_REQUEST_MODEL);
        }

        @Override
        public FixtureBuilder self() {
            return this;
        }

        public FixtureBuilder ignoreBrand() {
            return ignoreField(AddProductRequest::brand);
        }

        public FixtureBuilder ignoreType() {
            return ignoreField(AddProductRequest::type);
        }
    }
}
