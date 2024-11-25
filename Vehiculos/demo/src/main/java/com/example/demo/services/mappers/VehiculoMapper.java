package com.example.demo.services.mappers;

import com.example.demo.entities.Vehiculo;
import com.example.demo.entities.dto.VehiculoDTO;
import org.springframework.stereotype.Component;

@Component
public class VehiculoMapper {

    public VehiculoDTO toDTO(Vehiculo vehiculo) {
        if (vehiculo == null) {
            return null;
        }

        return new VehiculoDTO(
                vehiculo.getId(),
                vehiculo.getPatente(),
                vehiculo.getModelo() != null ? vehiculo.getModelo().getId() : null, // Asumimos que modelo tiene un id
                vehiculo.getAnio()
        );
    }

    public Vehiculo toEntity(VehiculoDTO vehiculoDTO) {
        if (vehiculoDTO == null) {
            return null;
        }

        Vehiculo vehiculo = new Vehiculo();
        vehiculo.setId(vehiculoDTO.getId());
        vehiculo.setPatente(vehiculoDTO.getPatente());
        vehiculo.setAnio(vehiculoDTO.getAnio());
        // Este es solo un ejemplo, si es necesario asociar el modelo desde otra parte, debes hacerlo aquí
        // vehiculo.setModelo(new Modelo(vehiculoDTO.getIdModelo())); // Este es solo un ejemplo

        return vehiculo;
    }
}
