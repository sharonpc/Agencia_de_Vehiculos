package com.example.demo.controllers;

import com.example.demo.entities.Interesado;
import com.example.demo.services.InteresadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/interesados")
public class InteresadoController {

    private final InteresadoService interesadoService;

    @Autowired
    public InteresadoController(InteresadoService interesadoService) {
        this.interesadoService = interesadoService;
    }


    //Valida que la licencia no este vencida, comparando con la fecha actual. recibe un documento como parametro
    @GetMapping("/validarLicenciaFecha/{documento}")
    public boolean consultarFecha(@PathVariable String documento) {
        Interesado interesado = interesadoService.consultarInteresado(documento);
        boolean valida = interesadoService.validarFechaVencimientoLicencia2(interesado);
        System.out.println(interesado.getFechaVencimientoLicencia());
        System.out.println(LocalDateTime.now());
        return valida;
    }

    //verificar si esta restringido o no, devuelve un boolean: false si no esta restringido...true si esta restringido
    @GetMapping("/verificarRestringido/{documento}")
    public boolean verificarRestringido(@PathVariable String documento) {
        return interesadoService.verificiarRestringido(documento);
    }

}
