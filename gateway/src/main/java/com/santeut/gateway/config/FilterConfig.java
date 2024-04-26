//package com.santeut.gateway.config;
//
//import com.santeut.gateway.filter.GlobalFilter;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.cloud.gateway.route.RouteLocator;
//import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//
//@Slf4j
//@Configuration
//public class FilterConfig {
//
//  @Bean
//  public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
//
//    log.debug("Route");
//
//    return builder.routes()
//        .route(r -> r.path("/api/auth/**")
//                .filters(f ->  f.addRequestHeader("auth-request", "auth-request-header")
//                               .addResponseHeader("auth-response", "auth-response-header"))
//            .uri("http://localhost:8081"))
//        .build();
////        .route("mountain", r -> r.path("/mountain/**")
////            .uri("http://localhost:8082"))
////        .route("guild", r -> r.path("/guild/**")
////            .uri("http://localhost:8083"))
////        .route("party", r -> r.path("/party/**")
////            .uri("http://localhost:8084"))
////        .route("hiking", r -> r.path("/hiking/**")
////            .uri("http://localhost:8085"))
////        .route("community", r -> r.path("/community/**")
////            .uri("http://localhost:8086"))
//
//  }
//
//}
