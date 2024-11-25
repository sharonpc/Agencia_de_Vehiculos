package com.example.demo.repositories;

import com.example.demo.entities.Modelo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModeloRepository extends CrudRepository<Modelo, Integer> {
}
