package com.example.demo.controllers;

import com.example.demo.entities.dto.EmpleadoDto;
import com.example.demo.services.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/empleados")
public class EmpleadoController {

    private final EmpleadoService empleadoService;

    @Autowired
    public EmpleadoController(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }

    @GetMapping("/{legajo}")
    public EmpleadoDto consultarEmpleado(@PathVariable int legajo) {
        return empleadoService.consultarEmpleadoPorLegajo(legajo);
    }

}
