package com.example.demo.clients;

import com.example.demo.entities.dto.PruebaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NotificacionServiceClient {


    private final RestTemplate restTemplate;
    private static final String NOTIFICACION_SERVICE_URL = "http://localhost:8083/api/notificaciones"; // Ajusta esta URL según corresponda


    @Autowired
    public NotificacionServiceClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String generarNotificacion(int idVehiculo, String motivo, String telefonoEmpleado, String documentoInteresado) {
        try {

            String url = NOTIFICACION_SERVICE_URL + "/generarNotificacion/" + idVehiculo + "/" + telefonoEmpleado + "/" + documentoInteresado;

            ResponseEntity<String> response = restTemplate.postForEntity(url, motivo, String.class); //

            // Verificar el código de respuesta
            if (response.getStatusCode().is2xxSuccessful()) {
                return "Notificacion Generada";
            }
        } catch (Exception e) {
            System.err.println("Error al generar notificacion: " + e.getMessage());
        }

        return "Fallo al generar notificacion";
    }

}
