package org.redhat.fusesource;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;

public class CamelR extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        restConfiguration().component("jetty").host("localhost").port(9091).bindingMode(RestBindingMode.auto);

        rest("/say")
                .get("/hello").produces("application/json").to("direct:hello");

        from("direct:hello")
                .to("sqlComponent:select * from users")
                .log("Message: ${body}");
    }
}
