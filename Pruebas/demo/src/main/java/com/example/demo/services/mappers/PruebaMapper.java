package com.example.demo.services.mappers;

import com.example.demo.entities.Empleado;
import com.example.demo.entities.Interesado;
import com.example.demo.entities.Prueba;
import com.example.demo.entities.dto.PruebaDTO;
import com.example.demo.repositories.EmpleadoRepository;
import com.example.demo.repositories.InteresadoRepository;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class PruebaMapper implements Function<PruebaDTO, Prueba> {

    private final InteresadoRepository interesadoRepository;
    private final EmpleadoRepository empleadoRepository;

    public PruebaMapper(InteresadoRepository interesadoRepository, EmpleadoRepository empleadoRepository) {
        this.interesadoRepository = interesadoRepository;
        this.empleadoRepository = empleadoRepository;
    }

    @Override
    public Prueba apply(PruebaDTO pruebaDTO) {
        Interesado interesado = interesadoRepository.findByDocumento(pruebaDTO.getInteresadoDocumento());
        Empleado empleado = empleadoRepository.findById(pruebaDTO.getEmpleadoLegajo()).orElse(null);

        return new Prueba(
                pruebaDTO.getId(),
                pruebaDTO.getIdVehiculo(),
                interesado,
                empleado,
                pruebaDTO.getFechaHoraInicio(),
                pruebaDTO.getFechaHoraFin(),
                pruebaDTO.getComentarios(),
                pruebaDTO.isIncidencia()
        );
    }

}
