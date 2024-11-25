package com.example.demo.controllers;

import com.example.demo.entities.dto.ComentarioDTO;
import com.example.demo.entities.dto.PruebaDTO;
import com.example.demo.repositories.PruebaRepository;
import com.example.demo.services.PruebaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.format.DateTimeParseException;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/pruebas")
public class PruebaController {

    private final PruebaService pruebaService;


    @Autowired
    public PruebaController(PruebaService pruebaService, PruebaRepository pruebaRepository) {
        this.pruebaService = pruebaService;
    }


    //Este endpoint funciona para agregar la prueba pasandole en el body un json como el siguiente:
    /*
    {
  "fechaHoraInicio": "2024-10-22T10:00:00",
  "fechaHoraFin": "2024-10-22T13:00:00",
  "comentarios": "Prueba de manejo en condiciones de lluvia",
  "vehiculoPatente": "ZZ012FT",
  "interesadoDocumento": "12345678",
  "empleadoLegajo": 1
}
*/

    //para el punto a
    @PostMapping("/")
    public ResponseEntity<?> crearPrueba2(@RequestBody PruebaDTO pruebaDTO) {
        PruebaDTO pruebaDTO1 = pruebaService.agregarPruebaValidandoLicenciaYRestringido(pruebaDTO);
        if (pruebaDTO1 != null) {
            return ResponseEntity.ok(pruebaDTO1);
        } else {
            return ResponseEntity.badRequest().body("No se pudo agregar la prueba");
        }
    }


    //para el punto b
    @GetMapping("/fecha/{fecha}")
    public ResponseEntity<List<PruebaDTO>> listarPruebasEnUnMomento(@PathVariable String fecha) {
        try{
            LocalDateTime fechaParseada = LocalDateTime.parse(fecha);
            System.out.println(fechaParseada);
            List<PruebaDTO> pruebaDTOList = pruebaService.listarPruebasEnUnMomento(fechaParseada);
            return ResponseEntity.ok(pruebaDTOList);
        } catch (DateTimeParseException e){
            return ResponseEntity.badRequest().body(Collections.emptyList());
        }
    }


    //Reporte I
    @GetMapping("/incidencias")
    public ResponseEntity<List<PruebaDTO>> obtenerPruebasIncidentes() {
        try{
            List<PruebaDTO> pruebasConIncidentes = pruebaService.obtenerPruebasIncidentes();
            return ResponseEntity.ok(pruebasConIncidentes);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }

    //Reporte II
    @GetMapping("/incidencias/empleado/{legajo}")
    public ResponseEntity<List<PruebaDTO>> obtenerDetalleIncidentesPorEmpleado(@PathVariable int legajo) {
        try{
            List<PruebaDTO> detallesIncidentes = pruebaService.obtenerDetalleIncidentesPorEmpleado(legajo);
            return ResponseEntity.ok(detallesIncidentes);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }

    //reporte IV
    @GetMapping("/vehiculo/{id}")
    public ResponseEntity<List<PruebaDTO>> obtenerPruebas(@PathVariable Integer id) {
        try{
            List<PruebaDTO> pruebas = pruebaService.obtenerPruebas(id);
            return ResponseEntity.ok(pruebas);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.emptyList());
        }
    }


    //punto C
    @PutMapping("/{id}/finalizar")
    public ResponseEntity<PruebaDTO> finalizarPrueba(@PathVariable int id, @RequestBody ComentarioDTO comentarioDTO){
        try{
            PruebaDTO pruebaFinalizada = pruebaService.finalizarPrueba(id, comentarioDTO.getComentarios());
            return ResponseEntity.ok(pruebaFinalizada);
        }catch(IllegalStateException e){ // La prueba no está en estado finalizable.
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }catch(Exception e){ // Cualquier otra excepción general.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }




}
