package com.example.demo.clients;

import com.example.demo.entities.dto.EmpleadoDto;
import com.example.demo.entities.dto.PruebaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class EmpleadoServiceClient {

    private final RestTemplate restTemplate;
    private static final String EMPLEADO_SERVICE_URL = "http://localhost:8083/api/empleados"; // Ajusta esta URL según corresponda

    @Autowired
    public EmpleadoServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public EmpleadoDto obtenerEmpleadoPorId(int legajo) {
        try {

            String url = EMPLEADO_SERVICE_URL + "/" + legajo;
            ResponseEntity<EmpleadoDto> response = restTemplate.getForEntity(url, EmpleadoDto.class);
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return response.getBody();
            }
        } catch (Exception ex) {
            System.err.println("Error al consultar el microservicio de empleados: " + ex.getMessage());
        }
        return null;
    }

}
