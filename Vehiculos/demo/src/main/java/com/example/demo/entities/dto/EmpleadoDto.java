package com.example.demo.entities.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmpleadoDto {

    private int legajo;

    private String nombre;

    private String apellido;

    private String telefono_contacto;

}
