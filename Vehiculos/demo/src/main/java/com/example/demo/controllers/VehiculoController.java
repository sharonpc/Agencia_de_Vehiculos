package com.example.demo.controllers;

import com.example.demo.entities.Vehiculo;
import com.example.demo.entities.dto.PosicionDto;
import com.example.demo.entities.dto.PruebaDTO;
import com.example.demo.entities.dto.VehiculoDTO;
import com.example.demo.services.VehiculoService;
import com.example.demo.services.mappers.VehiculoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/vehiculos")
public class  VehiculoController {

    private final VehiculoService vehiculoService;
    private final VehiculoMapper vehiculoMapper;

    @Autowired
    public VehiculoController(VehiculoService vehiculoService, VehiculoMapper vehiculoMapper) {
        this.vehiculoService = vehiculoService;
        this.vehiculoMapper = vehiculoMapper;
    }

    @GetMapping("/patente/{patente}")
    public VehiculoDTO consultarVehiculo(@PathVariable String patente) {
        return vehiculoMapper.toDTO(vehiculoService.consultarPorPatente(patente));
    }

    @GetMapping("/{id}")
    public VehiculoDTO consultarVehiculo(@PathVariable int id) {
        return vehiculoService.buscarPorId(id);
    }

    @PostMapping("/disponibilidad")
    public boolean isVehiculoDisponible(@RequestBody VehiculoDTO vehiculoDTO, @RequestBody PruebaDTO pruebaDTO) {
        // Aquí se verifica si el vehículo está disponible, usando ambos objetos
        Vehiculo vehiculo = vehiculoMapper.toEntity(vehiculoDTO);
        return vehiculoService.isVehiculoDisponible(vehiculo, pruebaDTO);
    }


    //Reporte III
    @GetMapping("/prueba/{patente}/{fechaInicio}/{fechaFinal}")
    public ResponseEntity<?> cantidadKilometros(@PathVariable String patente,
                                                @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaInicio,
                                                @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date fechaFinal) {
        try {
            double kilometrosRecorridos = vehiculoService.obtenerKilometrosRecorridos(patente, fechaInicio, fechaFinal);

            return ResponseEntity.ok(kilometrosRecorridos + " Kilometros recorridos");

        } catch (Exception e) {
            // Si ocurre un error, se retorna un mensaje de error con el código 404 Not Found
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Vehículo con patente " + patente + " no encontrado.");
        }
    }
}


