package com.example.demo.services;

import com.example.demo.entities.Interesado;
import com.example.demo.repositories.InteresadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class InteresadoService {

    private final InteresadoRepository interesadoRepository;

    @Autowired
    public InteresadoService(InteresadoRepository interesadoRepository) {
        this.interesadoRepository = interesadoRepository;
    }


    public boolean validarFechaVencimientoLicencia2(Interesado interesado) {
        LocalDateTime fechaAValidar = interesado.getFechaVencimientoLicencia();
        LocalDateTime fechaActual = LocalDateTime.now();
        if (fechaAValidar.isAfter(fechaActual) || fechaAValidar.isEqual(fechaActual)) {
            return false;
        } else {
            return true;
        }
    }


    public Interesado consultarInteresado(String documento) {
        return interesadoRepository.findByDocumento(documento);
    }

    public boolean verificiarRestringido(String documento) {
        Interesado interesado = this.consultarInteresado(documento);
        return interesado.isRestringido();
    }




}
