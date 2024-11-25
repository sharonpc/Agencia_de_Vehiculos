package com.example.demo.services;

import com.example.demo.entities.apiExternaLocalizacion.ConfiguracionApiExterna;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class ConfiguracionServiceApiExterna {


    private static final String API_URL = "https://labsys.frc.utn.edu.ar/apps-disponibilizadas/backend/api/v1/configuracion/";

    public ConfiguracionApiExterna obtenerConfiguracion() {
        RestTemplate restTemplate = new RestTemplate();

        try {
            // Realizamos la solicitud GET para obtener la configuración de la API externa
            return restTemplate.getForObject(API_URL, ConfiguracionApiExterna.class);
        } catch (HttpClientErrorException | HttpServerErrorException e) {
            // Manejo de errores de cliente o servidor
            System.err.println("Error al consumir la API externa: " + e.getMessage());
            return null;
        } catch (Exception e) {
            // Manejo de otros errores generales
            System.err.println("Error inesperado: " + e.getMessage());
            return null;
        }
    }


}
