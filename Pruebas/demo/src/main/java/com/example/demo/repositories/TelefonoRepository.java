package com.example.demo.repositories;

import com.example.demo.entities.Telefono;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TelefonoRepository extends CrudRepository<Telefono, Long> {

}
