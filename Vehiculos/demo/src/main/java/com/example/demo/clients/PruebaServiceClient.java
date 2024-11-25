package com.example.demo.clients;

import com.example.demo.entities.dto.PruebaDTO; // Asegúrate de tener esta clase creada en tu proyecto
import com.example.demo.entities.dto.VehiculoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class PruebaServiceClient {

    private final RestTemplate restTemplate;
    private static final String PRUEBA_SERVICE_URL = "http://localhost:8082/api/pruebas"; // Ajusta esta URL según corresponda

    @Autowired
    public PruebaServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<PruebaDTO> obtenerPruebasPorVehiculoId(int id) {
        try {

            String url = PRUEBA_SERVICE_URL + "/vehiculo/" + id;
            ResponseEntity<PruebaDTO[]> response = restTemplate.getForEntity(url, PruebaDTO[].class);
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return Arrays.asList(response.getBody());
            }
        } catch (Exception ex) {
            System.err.println("Error al consultar el microservicio de pruebas: " + ex.getMessage());
        }
        return Collections.emptyList();
    }
}
