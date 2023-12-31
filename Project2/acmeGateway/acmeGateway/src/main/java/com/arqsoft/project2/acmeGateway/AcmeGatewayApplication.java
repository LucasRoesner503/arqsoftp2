package com.arqsoft.project2.acmeGateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.beans.factory.annotation.Value;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class AcmeGatewayApplication {

	@Value("${monolithic.url}")
	private String monolithicUrl;

	@Value("${acme.user}")
	private String acmeUser;

	@Value("${acme.authentication}")
	private String acmeAuthentication;

	public static void main(String[] args) {
		SpringApplication.run(AcmeGatewayApplication.class, args);
	}

	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {

		Map<String, String> headers = new HashMap<>();

		return builder.routes()
				.route(r -> r.path("/auth/public/login")
						.filters(f -> f
							.addResponseHeader("X-Powered-By","DanSON Gateway Service")
								.modifyResponseBody(String.class, String.class, (exchange, s) -> {
									String authToken = exchange.getResponse().getHeaders().getFirst("Authorization");
									headers.put("authToken", authToken);
									return Mono.just(s);
								})
						)
						.uri(acmeAuthentication)
				)
				.route(r -> r.path("/products/**")
						.filters(f -> f.addRequestHeader("authToken", String.valueOf(new StringBuilder("Bearer ").append(headers.get("authToken"))))
								.addResponseHeader("X-Powered-By", "Response")
						)
						.uri("http://localhost:8080")
				)
				.route(r -> r.path("/admin/user/**")
						.filters(f -> f.addRequestHeader("authToken", String.valueOf(new StringBuilder("Bearer ").append(headers.get("authToken"))))
								.addResponseHeader("X-Powered-By", "Response")
						)
						.uri(acmeUser)
				)
				.route(r -> r.path("/reviews/**")
						.filters(f -> f.addRequestHeader("authToken", String.valueOf(new StringBuilder("Bearer ").append(headers.get("authToken"))))
								.addResponseHeader("X-Powered-By", "Response")
						)
						.uri("http://localhost:8080")
				)
				.route(r -> r.path("/aggregatedrating/**")
						.filters(f -> f.addRequestHeader("authToken", String.valueOf(new StringBuilder("Bearer ").append(headers.get("authToken"))))
								.addResponseHeader("X-Powered-By", "Response")
						)
						.uri("http://localhost:8080")
				)
				.build();

	}

}
