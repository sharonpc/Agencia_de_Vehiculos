package com.example.demo.controllers;

import com.example.demo.entities.dto.PosicionDto;
import com.example.demo.services.PosicionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posiciones")
public class PosicionController {

    private PosicionService posicionService;

    @Autowired
    public PosicionController(PosicionService posicionService) {
        this.posicionService = posicionService;
    }

    @GetMapping("/validar/{patente}")
    public ResponseEntity<?> validar(@PathVariable String patente){
        String resultado = posicionService.localizacion(patente);
        System.out.println(resultado);
        try {
            System.out.println(resultado);
            //devolvemos un 200 OK con el resultado de la evaluación
            return new ResponseEntity<>(resultado, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            // Si la patente es inválida o no se puede procesar, retornamos un 400 Bad Request con un mensaje
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Patente inválida o no encontrada");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al procesar la solicitud");
        }
    }


    @PostMapping("/posicionVehiculo")
    public ResponseEntity<?> agregarPosicionVehiculo(@RequestBody PosicionDto PosicionDto) {
        PosicionDto posicionDto = posicionService.insertarPosicion(PosicionDto);
        if (posicionDto != null) {
            return ResponseEntity.ok(posicionDto);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }


}
