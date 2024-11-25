package com.example.demo.services.mappers;

import com.example.demo.entities.Notificacion;
import com.example.demo.entities.Telefono;
import com.example.demo.entities.dto.NotificacionDto;
import com.example.demo.repositories.TelefonoRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Function;

@Service
public class NotificacionMapper implements Function<NotificacionDto, Notificacion> {

    private final TelefonoRepository telefonoRepository;

    public NotificacionMapper(TelefonoRepository telefonoRepository) {
        this.telefonoRepository = telefonoRepository;
    }


    @Override
    public Notificacion apply(NotificacionDto notificacionDto) {
        Telefono telefono = telefonoRepository.findById(notificacionDto.getTelefono()).orElse(null);

        return new Notificacion(
                notificacionDto.getId_notificacion(),
                telefono,
                notificacionDto.getDescripcion(),
                notificacionDto.getTelefonoEmpleado()
        );
    }
}
