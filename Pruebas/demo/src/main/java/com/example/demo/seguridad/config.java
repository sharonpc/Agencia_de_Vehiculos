package com.example.demo.seguridad;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class config {


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize

                //SOLO UN ADMIN PUEDE VER LOS REPORTES
                .requestMatchers(HttpMethod.GET, "/api/pruebas/incidencias")
                .hasRole("ADMIN")

                .requestMatchers(HttpMethod.POST, "/api/pruebas/incidencias/empleado/{legajo}")
                .hasRole("ADMIN")

                .requestMatchers(HttpMethod.POST, "/api/pruebas/vehiculo/{id}")
                .hasRole("ADMIN")


                // SOLO UN EMPLEADO PUEDE CREAR PRUEBAS
                .requestMatchers(HttpMethod.POST, "/api/pruebas/")
                .hasRole("EMPLEADO")

                .requestMatchers(HttpMethod.PUT, "/api/pruebas/{id}/finalizar")
                .hasRole("EMPLEADO")



                // SOLO UN EMPLEADO PUEDE GENERAR NOTIFICACIONES
                .requestMatchers(HttpMethod.POST, "/api/notificaciones/")
                .hasRole("EMPLEADO")

                .requestMatchers(HttpMethod.POST, "/api/notificaciones/all")
                .hasRole("EMPLEADO")

                .requestMatchers(HttpMethod.POST, "/api/notificaciones/generarNotificacion/{idVehiculo}/{telefonoEmpleado}/{documentoInteresado}")
                .hasRole("EMPLEADO")

                .requestMatchers("/api/pruebas/fecha/{fecha}").permitAll()



                .anyRequest()
                .authenticated()
        ).oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));

        return http.build();
    }



    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        var jwtAuthenticationConverter = new JwtAuthenticationConverter();
        var grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();

        grantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");

        grantedAuthoritiesConverter.setAuthoritiesClaimName("authorities");

        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }
}
