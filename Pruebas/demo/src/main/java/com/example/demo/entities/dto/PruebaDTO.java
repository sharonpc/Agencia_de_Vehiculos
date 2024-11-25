package com.example.demo.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PruebaDTO {
    private int id;
    private int idVehiculo; // Solo el ID del vehículo
    private String interesadoDocumento; // Solo el documento del interesado
    private int empleadoLegajo; // Solo el legajo del empleado
    private LocalDateTime fechaHoraInicio;
    private LocalDateTime fechaHoraFin;
    private String comentarios;
    private boolean incidencia;
}
