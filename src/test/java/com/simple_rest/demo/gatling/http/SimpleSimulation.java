package com.simple_rest.demo.gatling.http;

import com.simple_rest.demo.service.dto.SimpleDto;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;
import org.springframework.web.client.RestClient;

import java.util.concurrent.atomic.AtomicInteger;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class SimpleSimulation extends Simulation {
    private static final int  NUMBER_OF_USERS = 100;

    private static final int NUMBER_OF_REQUESTS = 1_000_000;

    private static final String BASE_URL = "http://localhost:8080/api";

    private static final HttpProtocolBuilder HTTP_PROTOCOL_BUILDER = http.baseUrl(BASE_URL);

    private final AtomicInteger counter = new AtomicInteger();

    private final ScenarioBuilder SCENARIO_BUILDER = scenario("basic")
            .asLongAs(session -> counter.incrementAndGet() <= NUMBER_OF_REQUESTS)
            .on(exec(http("random get").get("/simple/#{randomInt(1,100000)}")));

    public SimpleSimulation() {
        setUp(SCENARIO_BUILDER.injectOpen(atOnceUsers(NUMBER_OF_USERS)).protocols(HTTP_PROTOCOL_BUILDER));
    }

    @Override
    public void before() {
        RestClient restClient = RestClient.create();
        for(int i = 0; i < 100_000; i++) {
            SimpleDto dto = new SimpleDto();
            dto.setName("name " + i);
            restClient.post()
                    .uri(BASE_URL + "/simple/create")
                    .body(dto)
                    .retrieve()
                    .toBodilessEntity();
        }
    }
}
