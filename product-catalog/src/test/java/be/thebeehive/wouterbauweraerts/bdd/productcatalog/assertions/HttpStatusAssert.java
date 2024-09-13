package be.thebeehive.wouterbauweraerts.bdd.productcatalog.assertions;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.internal.Failures;
import org.springframework.http.HttpStatusCode;

@SuppressWarnings("unused")
public class HttpStatusAssert extends AbstractAssert<HttpStatusAssert, HttpStatusCode> {
    protected HttpStatusAssert(HttpStatusCode statusCode) {
        super(statusCode, HttpStatusAssert.class);
    }

    public static HttpStatusAssert assertStatusCode(HttpStatusCode statusCode){
        return new HttpStatusAssert(statusCode);
    }

    public HttpStatusAssert is2xxCode() {
        if(this.actual.is2xxSuccessful())
                return this.myself;
                else throw Failures.instance().failure("Expected %d to be within 2xx range".formatted(this.actual.value()));
    }
}
