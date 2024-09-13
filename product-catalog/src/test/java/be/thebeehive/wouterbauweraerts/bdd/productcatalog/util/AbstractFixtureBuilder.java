package be.thebeehive.wouterbauweraerts.bdd.productcatalog.util;

import static org.instancio.Select.field;

import java.util.HashMap;
import java.util.Map;

import org.instancio.GetMethodSelector;
import org.instancio.Instancio;
import org.instancio.InstancioApi;
import org.instancio.Model;
import org.instancio.Selector;

public abstract class AbstractFixtureBuilder<TYPE, SELF extends AbstractFixtureBuilder<TYPE, SELF>> {
    private final Map<Selector, Object> fieldValues = new HashMap<>();

    protected <PROPTYPE> SELF setField(GetMethodSelector<TYPE, PROPTYPE> methodReference, PROPTYPE value) {
        set(field(methodReference), value);
        return self();
    }

    protected void set(Selector selector, Object value) {
        fieldValues.put(selector, value);
    }

    protected TYPE buildInternal(Model<TYPE> model) {
        InstancioApi<TYPE> instancioApi = Instancio.of(model);

        fieldValues.forEach(instancioApi::set);

        return instancioApi.create();
    }

    public abstract TYPE build();

    public abstract SELF self();
}