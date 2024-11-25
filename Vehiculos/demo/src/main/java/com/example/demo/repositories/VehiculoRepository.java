package com.example.demo.repositories;

import com.example.demo.entities.Vehiculo;
import com.example.demo.services.mappers.VehiculoMapper;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehiculoRepository extends CrudRepository<Vehiculo, Integer> {

    Vehiculo findByPatente(String patente);

    Integer findIdByPatente(String patente);

    Vehiculo findById(int id);


}
