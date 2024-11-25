package com.example.demo.services.mappers;


import com.example.demo.entities.Posicion;
import com.example.demo.entities.dto.EmpleadoDto;
import com.example.demo.entities.dto.PosicionDto;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class PosicionDtoMapper implements Function<Posicion, PosicionDto> {

    @Override
    public PosicionDto apply(Posicion posicion) {
        return new PosicionDto(
                0,
                posicion.getVehiculo().getPatente(),
                posicion.getLatitud(),
                posicion.getLongitud()
        );
    }
}
