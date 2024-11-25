package com.example.demo.entities;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Localizacion {

    private LocalDateTime fechaHoraInicio;

    private LocalDateTime fechaHoraFin;
}
