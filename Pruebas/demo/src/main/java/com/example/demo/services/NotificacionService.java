package com.example.demo.services;

import com.example.demo.clients.VehiculoServiceClient;
import com.example.demo.controllers.NotificacionController;
import com.example.demo.entities.Interesado;
import com.example.demo.entities.Notificacion;
import com.example.demo.entities.Telefono;
import com.example.demo.entities.dto.NotificacionDto;
import com.example.demo.entities.dto.VehiculoDTO;
import com.example.demo.repositories.InteresadoRepository;
import com.example.demo.repositories.NotificacionRepository;
import com.example.demo.repositories.TelefonoRepository;
import com.example.demo.services.mappers.NotificacionDtoMapper;
import com.example.demo.services.mappers.NotificacionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificacionService {

    private final NotificacionRepository notificacionRepository;


    private final InteresadoRepository interesadoRepository;


    private final VehiculoServiceClient vehiculoServiceClient;

    private final NotificacionDtoMapper notificacionDtoMapper;


    @Autowired
    public NotificacionService(NotificacionRepository notificacionRepository, TelefonoRepository telefonoRepository, InteresadoRepository interesadoRepository, NotificacionMapper notificacionMapper, VehiculoServiceClient vehiculoServiceClient, NotificacionDtoMapper notificacionDtoMapper, EmpleadoService empleadoService) {
        this.notificacionRepository = notificacionRepository;
        this.interesadoRepository = interesadoRepository;
        this.vehiculoServiceClient = vehiculoServiceClient;
        this.notificacionDtoMapper = notificacionDtoMapper;
    }


    // Crear una notificación con IDs de interesados en lugar de números de teléfono
    public List<NotificacionDto> crearNotificacion(NotificacionController.NotificacionRequest request) {
        List<Notificacion> notificacionesGuardadas = new ArrayList<>();

        for (Integer interesadoId : request.getIdsInteresados()) {
            Interesado interesado = interesadoRepository.findById(interesadoId)
                    .orElseThrow(() -> new RuntimeException("Interesado no encontrado: " + interesadoId));

            Telefono telefono = interesado.getTelefono();
            if (telefono == null) {
                throw new RuntimeException("No se encontró teléfono para el interesado con ID: " + interesadoId);
            }

            // Crear una nueva instancia de Notificación para cada interesado
            Notificacion notificacion = new Notificacion();
            notificacion.setDescripcion(request.getMensaje());
            notificacion.setTelefono(telefono);

            // Guardar la notificación para cada interesado
            Notificacion notificacionGuardada = notificacionRepository.save(notificacion);
            notificacionesGuardadas.add(notificacionGuardada); // Agregar la notificación guardada a la lista
        }

        return notificacionesGuardadas.stream().map(notificacionDtoMapper).collect(Collectors.toList());
    }

    // Enviar mensaje a todos los interesados
    public List<NotificacionDto> enviarATodos(String mensaje) {
        List<Notificacion> notificacionesGuardadas = new ArrayList<>();
        List<Interesado> interesados = (List<Interesado>) interesadoRepository.findAll();

        for (Interesado interesado : interesados) {
            Telefono telefono = interesado.getTelefono();
            if (telefono != null) {
                // Crear una nueva instancia de Notificación
                Notificacion notificacion = new Notificacion();
                notificacion.setDescripcion(mensaje); // Solo el mensaje, no el objeto completo
                notificacion.setTelefono(telefono);

                // Guardar la notificación en la base de datos
                Notificacion notificacionGuardada = notificacionRepository.save(notificacion);
                notificacionesGuardadas.add(notificacionGuardada);
            }
        }

        return notificacionesGuardadas.stream().map(notificacionDtoMapper).collect(Collectors.toList());
    }

    public String generarNotificacion(int idVehiculo, String motivo, String telefonoEmpleado, String documentoInteresado) {

        VehiculoDTO vehiculoDTO = vehiculoServiceClient.obtenerVehiculoPorId(idVehiculo);

        // Crear la notificación
        Notificacion notificacion = new Notificacion();
        notificacion.setDescripcion(motivo);
        notificacion.setTelefonoEmpleado(telefonoEmpleado);
        Interesado interesado = interesadoRepository.findByDocumento(documentoInteresado); //traemos al interesado para marcarlo como restringido

        interesado.setRestringido(true); //marcamos al interesado como restringido por no cumplir las condiciones
        interesadoRepository.save(interesado); //guardamos el interesado en la base de datos

        // Guardar la notificación en la base de datos
        notificacionRepository.save(notificacion);

        // Mensaje de éxito
        return "Notificación generada y guardada para el vehículo " + vehiculoDTO.getPatente();
    }

}
