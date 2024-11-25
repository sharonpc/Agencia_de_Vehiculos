package com.example.demo.clients;

import com.example.demo.entities.dto.VehiculoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

@Service
public class VehiculoServiceClient {

    private final RestTemplate restTemplate;
    private static final String VEHICULO_SERVICE_URL = "http://localhost:8081/api/vehiculos"; // Cambia esta URL si es necesario

    @Autowired
    public VehiculoServiceClient(RestTemplate restTemplate ) {
        this.restTemplate = restTemplate;
    }

    // Metodo para obtener un vehículo por id
    public VehiculoDTO obtenerVehiculoPorId(int id) {
        try {
            String url = VEHICULO_SERVICE_URL + "/" + id;
            ResponseEntity<VehiculoDTO> response = restTemplate.getForEntity(url, VehiculoDTO.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                return response.getBody();
            }
        } catch (Exception e) {
            System.err.println("Error al obtener vehículo: " + e.getMessage());
        }
        return null;
    }

}
