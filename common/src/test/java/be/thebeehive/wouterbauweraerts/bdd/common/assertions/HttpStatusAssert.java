package be.thebeehive.wouterbauweraerts.bdd.common.assertions;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.internal.Failures;

@SuppressWarnings("unused")
public class HttpStatusAssert extends AbstractAssert<HttpStatusAssert, Integer> {
    protected HttpStatusAssert(Integer statusCode) {
        super(statusCode, HttpStatusAssert.class);
    }

    public static HttpStatusAssert assertStatusCode(Integer statusCode){
        return new HttpStatusAssert(statusCode);
    }

    public HttpStatusAssert isStatus(int status) {
        if(this.actual.equals(status)) {
            return this.myself;
        }
        else throw Failures.instance().failure("Expected %d to be Http %d".formatted(this.actual, status));
    }
}
