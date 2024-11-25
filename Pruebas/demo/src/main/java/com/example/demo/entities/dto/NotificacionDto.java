package com.example.demo.entities.dto;

import com.example.demo.entities.Telefono;
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

    private String telefonoEmpleado;



}
