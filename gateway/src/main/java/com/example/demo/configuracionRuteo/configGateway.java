package com.example.demo.configuracionRuteo;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class configGateway {

    @Bean
    public RouteLocator configurarRutas(RouteLocatorBuilder builder,
                                        @Value("${gateway-microservicioPruebas.url}") String uriPruebas,
                                        @Value("${gateway-microservicioVehiculos.url}") String uriVehiculos) {
        return builder.routes()
                // Ruteo al Microservicio de pruebas
                .route(p -> p.path("/api/pruebas/**").uri(uriPruebas))
                // Ruteo al Microservicio de vehiculos
                .route(p -> p.path("/api/vehiculos/**").uri(uriVehiculos))
                // Ruteo al Microservicio de notificaciones
                .route(p -> p.path("/api/notificaciones/**").uri(uriPruebas))
                // Ruteo al Microservicio de posiciones
                .route(p -> p.path("/api/posiciones/**").uri(uriVehiculos))
                //Ruteo al microservicio de Empleados
                .route(p -> p.path("/api/empleados/**").uri(uriPruebas))
                .build();


    }

}
