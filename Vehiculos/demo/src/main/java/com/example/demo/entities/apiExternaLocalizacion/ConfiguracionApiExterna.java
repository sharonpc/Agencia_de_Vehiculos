package com.example.demo.entities.apiExternaLocalizacion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfiguracionApiExterna {

    private Coordenada coordenadasAgencia;
    private double radioAdmitidoKm;
    private List<ZonaRestringida> zonasRestringidas;

}
