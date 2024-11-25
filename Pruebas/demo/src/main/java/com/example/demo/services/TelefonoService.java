package com.example.demo.services;

import com.example.demo.repositories.TelefonoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TelefonoService {

    private final TelefonoRepository telefonoRepository;

    @Autowired
    public TelefonoService(TelefonoRepository telefonoRepository) {
        this.telefonoRepository = telefonoRepository;
    }

}
