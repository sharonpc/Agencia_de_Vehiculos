package com.example.demo.services.mappers;

import com.example.demo.entities.Empleado;
import com.example.demo.entities.dto.EmpleadoDto;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class EmpleadoMapper implements Function<EmpleadoDto, Empleado> {
    @Override
    public Empleado apply(EmpleadoDto empleadoDto) {
        return new Empleado(
                empleadoDto.getLegajo(),
                empleadoDto.getNombre(),
                empleadoDto.getApellido(),
                empleadoDto.getTelefono_contacto(),
                null
        );
    }
}
