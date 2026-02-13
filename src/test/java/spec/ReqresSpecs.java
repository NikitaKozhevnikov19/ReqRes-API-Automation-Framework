package spec;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static helpers.CustomApiListener.withCustomTemplates;
import static io.restassured.http.ContentType.JSON;

public class ReqresSpecs {

    public static RequestSpecification requestSpecification = new RequestSpecBuilder()
            .setContentType(JSON)
            .addHeader("x-api-key", "reqres_e55e36d5f2f54397a8690b2a6aa325da")
            .addFilter(withCustomTemplates())
            .log(LogDetail.ALL)
            .build();


    public static ResponseSpecification responseSpec(int statusCode) {
        return new ResponseSpecBuilder()
                .expectStatusCode(statusCode)
                .log(LogDetail.ALL)
                .build();
    }
}
