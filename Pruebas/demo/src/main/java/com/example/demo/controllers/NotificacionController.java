package com.example.demo.controllers;


import com.example.demo.entities.dto.NotificacionDto;
import com.example.demo.services.NotificacionService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Collections;


@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionController {

    @Autowired
    private NotificacionService notificacionService;

    @PostMapping("/generarNotificacion/{idVehiculo}/{telefonoEmpleado}/{documentoInteresado}")
    public ResponseEntity<?> generarNotificacion(@PathVariable int idVehiculo, @PathVariable String telefonoEmpleado, @PathVariable String documentoInteresado ,@RequestBody String motivo) {
        try{
            String respuesta = notificacionService.generarNotificacion(idVehiculo, motivo, telefonoEmpleado, documentoInteresado);
            return ResponseEntity.ok(respuesta);
        }catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.emptyList());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }

    // Endpoint para crear una notificación utilizando IDs de interesados y retornar todas las notificaciones creadas
    @PostMapping("/")
    public ResponseEntity<List<NotificacionDto>> crearNotificacion(@RequestBody NotificacionRequest request) {
         try {
             List<NotificacionDto> notificaciones = notificacionService.crearNotificacion(request);
             return ResponseEntity.ok(notificaciones);  // Retornar todas las notificaciones creadas
         }catch (IllegalArgumentException e) {
            // Maneja errores de argumentos inválidos
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.emptyList());
        } catch (Exception e) {
            // Maneja cualquier otra excepción inesperada
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }

    // Endpoint para enviar un mensaje a todos los interesados y retornar las notificaciones creadas
    @PostMapping("/all")
    public ResponseEntity<List<NotificacionDto>> enviarATodos(@RequestBody NotificacionController.NotificacionRequest request) {
        try {
            // Extraemos el mensaje del objeto de solicitud
            String mensaje = request.getMensaje();
            List<NotificacionDto> notificaciones = notificacionService.enviarATodos(mensaje);
            return ResponseEntity.ok(notificaciones);  // Retornar todas las notificaciones creadas
        }catch (IllegalArgumentException e) {
            // Maneja errores de argumentos inválidos
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Collections.emptyList());
        } catch (Exception e) {
            // Maneja cualquier otra excepción inesperada
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }

    // Clase interna para manejar el cuerpo de la solicitud al crear notificaciones
    @Setter
    @Getter
    public static class NotificacionRequest {
        // Getters y Setters
        private String mensaje;
        private List<Integer> idsInteresados; // Cambiado para enviar IDs de interesados

    }

}
