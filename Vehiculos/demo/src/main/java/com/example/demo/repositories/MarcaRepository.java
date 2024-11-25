package com.example.demo.repositories;

import com.example.demo.entities.Marca;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarcaRepository extends CrudRepository<Marca, Integer> {
}
