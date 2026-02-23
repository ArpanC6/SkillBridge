package com.skillbridge.esb.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class UserEventRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        // Error Handler
        errorHandler(deadLetterChannel("log:dead-letter?level=ERROR")
                .maximumRedeliveries(3)
                .redeliveryDelay(1000));

        // User Register হলে AI Service-কে notify করবে
        from("timer:user-check?period=30000")
                .routeId("user-registration-route")
                .log("ESB: Checking for new users...")
                .setHeader("Content-Type", constant("application/json"))
                .to("http://localhost:8081/api/users/arpan@gmail.com")
                .log("ESB: User data received: ${body}")
                .to("log:user-event?level=INFO");

        // Job Market Data প্রতি ৬ ঘণ্টায় collect করবে
        from("timer:job-market-sync?period=21600000")
                .routeId("job-market-route")
                .log("ESB: Syncing job market data...")
                .setBody(constant("{\"status\": \"sync started\"}"))
                .log("ESB: Job market sync completed");
    }
}