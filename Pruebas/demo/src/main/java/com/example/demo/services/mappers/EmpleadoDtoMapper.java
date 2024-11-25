package com.example.demo.services.mappers;

import com.example.demo.entities.Empleado;
import com.example.demo.entities.dto.EmpleadoDto;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class EmpleadoDtoMapper implements Function<Empleado, EmpleadoDto> {
    @Override
    public EmpleadoDto apply(Empleado empleado) {
        return new EmpleadoDto(
                empleado.getLegajo(),
                empleado.getNombre(),
                empleado.getApellido(),
                empleado.getTelefono_contacto()
        );
    }
}
