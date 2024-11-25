package com.example.demo.entities.apiExternaLocalizacion;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ZonaRestringida {

    private Coordenada noroeste;
    private Coordenada sureste;


}
