package com.example.demo.entities.dto;

import com.example.demo.entities.Vehiculo;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class PosicionDto {

    private int id;

    private String patenteVehiculo;
    
    private double latitud;

    private double longitud;


}
