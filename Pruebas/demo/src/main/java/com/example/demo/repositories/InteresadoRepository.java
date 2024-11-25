package com.example.demo.repositories;

import com.example.demo.entities.Interesado;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InteresadoRepository extends CrudRepository<Interesado, Integer> {

    Interesado findByDocumento(String documento);



}
