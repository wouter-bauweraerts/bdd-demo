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

    public HttpStatusAssert isStatus(int status) {
        if(this.actual.isSameCodeAs(HttpStatusCode.valueOf(status))) {
            return this.myself;
        }
        else throw Failures.instance().failure("Expected %d to be Http %d".formatted(this.actual.value(), status));
    }
}
