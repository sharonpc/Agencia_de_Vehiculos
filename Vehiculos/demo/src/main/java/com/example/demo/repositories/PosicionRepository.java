package com.example.demo.repositories;

import com.example.demo.entities.Posicion;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PosicionRepository extends CrudRepository<Posicion, Integer> {
}
