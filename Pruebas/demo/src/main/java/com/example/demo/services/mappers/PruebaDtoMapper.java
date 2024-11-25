package com.example.demo.services.mappers;

import com.example.demo.entities.Prueba;
import com.example.demo.entities.dto.PruebaDTO;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class PruebaDtoMapper implements Function<Prueba, PruebaDTO> {


    @Override
    public PruebaDTO apply(Prueba prueba) {
        return new PruebaDTO(
                prueba.getId(),
                prueba.getIdVehiculo(),
                prueba.getInteresado().getDocumento(),
                prueba.getEmpleado().getLegajo(),
                prueba.getFechaHoraInicio(),
                prueba.getFechaHoraFin(),
                prueba.getComentarios(),
                prueba.isIncidencia()
        );
    }
}
