package com.example.demo.services.mappers;

import com.example.demo.entities.Notificacion;
import com.example.demo.entities.Prueba;
import com.example.demo.entities.dto.NotificacionDto;
import com.example.demo.entities.dto.PruebaDTO;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class NotificacionDtoMapper implements Function<Notificacion, NotificacionDto> {


    @Override
    public NotificacionDto apply(Notificacion notificacion) {
        return new NotificacionDto(
                notificacion.getId_notificacion(),
                notificacion.getTelefono().getNum_telefono(),
                notificacion.getDescripcion(),
                notificacion.getTelefonoEmpleado()
        );
    }
}

