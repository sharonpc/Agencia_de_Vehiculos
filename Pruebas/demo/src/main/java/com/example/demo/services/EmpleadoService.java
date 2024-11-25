package com.example.demo.services;

import com.example.demo.entities.Empleado;
import com.example.demo.entities.Prueba;
import com.example.demo.entities.dto.EmpleadoDto;
import com.example.demo.repositories.EmpleadoRepository;
import com.example.demo.repositories.PruebaRepository;
import com.example.demo.services.mappers.EmpleadoDtoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmpleadoService{

    private final EmpleadoRepository empleadoRepository;
    private final PruebaRepository pruebaRepository;
    private final EmpleadoDtoMapper empleadoDtoMapper;

    @Autowired
    public EmpleadoService(EmpleadoRepository empleadoRepository, PruebaRepository pruebaRepository, EmpleadoDtoMapper empleadoDtoMapper) {
        this.empleadoRepository = empleadoRepository;
        this.pruebaRepository = pruebaRepository;
        this.empleadoDtoMapper = empleadoDtoMapper;
    }


    public EmpleadoDto consultarEmpleadoPorLegajo(int legajo) {
        Empleado empleado = empleadoRepository.findById(legajo).orElse(null);
        return empleadoDtoMapper.apply(empleado);
    }

    //metodo para devolver una lista de pruebas que contenga un empleado pasado como parametro (bsuca por el legajo)
    public List<Prueba> buscarPruebaPorEmpleado(Empleado empleado) {
        return pruebaRepository.findByEmpleado_Legajo(empleado.getLegajo());
    }

    public boolean isEmpleadoDisponible(Empleado empleado, Prueba prueba) {
        List<Prueba> listaPruebasPorEmpleadoLegajo = this.buscarPruebaPorEmpleado(empleado); // Trae la lista de pruebas que tengan al empleado pasado como parámetro
        for (Prueba prueba1 : listaPruebasPorEmpleadoLegajo) { // Recorre todas las listas para ver si hay una prueba que coincida con el momento actual
            // Verifica si hay superposición de horarios con el empleado
            if (prueba.getFechaHoraInicio().isBefore(prueba1.getFechaHoraFin()) && prueba.getFechaHoraFin().isAfter(prueba1.getFechaHoraInicio())) {
                return false; // Retorna false si hay superposición de horarios, es decir, el empleado está ocupado
            }
        }
        return true; // Retorna true si no hay superposición de horarios, es decir, el empleado está disponible
    }


}
