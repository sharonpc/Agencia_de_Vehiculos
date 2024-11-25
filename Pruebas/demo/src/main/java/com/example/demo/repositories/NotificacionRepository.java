package com.example.demo.repositories;

import com.example.demo.entities.Notificacion;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificacionRepository extends CrudRepository<Notificacion, Integer> {
    
}
