package com.example.demo.entities.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificacionDto {

    private int id_notificacion;

    private long telefono;

    private String descripcion;



}
