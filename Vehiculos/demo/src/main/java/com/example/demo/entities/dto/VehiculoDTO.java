package com.example.demo.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehiculoDTO {
    private int id;
    private String patente;
    private int idModelo;
    private int anio;
}
