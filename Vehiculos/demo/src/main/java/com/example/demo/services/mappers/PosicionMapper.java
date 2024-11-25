package com.example.demo.services.mappers;

import com.example.demo.entities.Posicion;
import com.example.demo.entities.Vehiculo;
import com.example.demo.entities.dto.PosicionDto;
import com.example.demo.repositories.VehiculoRepository;
import com.example.demo.services.VehiculoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.function.Function;

@Service
public class PosicionMapper implements Function<PosicionDto, Posicion> {

    private final VehiculoService vehiculoService;

    @Autowired
    public PosicionMapper(VehiculoService vehiculoService) {
        this.vehiculoService = vehiculoService;
    }


    @Override
    public Posicion apply(PosicionDto posicionDto) {
        Vehiculo vehiculo = vehiculoService.consultarPorPatente(posicionDto.getPatenteVehiculo());
        Date date = new Date();

        return new Posicion(
                0,
                vehiculo,
                date,
                posicionDto.getLatitud(),
                posicionDto.getLongitud()
        );
    }
}
