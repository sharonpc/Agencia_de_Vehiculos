package com.example.demo.repositories;

import com.example.demo.entities.Prueba;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PruebaRepository extends CrudRepository<Prueba, Integer> {

    Prueba findById(int id);

    List<Prueba> findByEmpleado_Legajo(int legajo);

    // Consulta nativa usando SQL directamente
    @Query(value = "SELECT ID, ID_VEHICULO, ID_INTERESADO, ID_EMPLEADO, FECHA_HORA_INICIO, FECHA_HORA_FIN, COMENTARIOS, INCIDENCIA FROM pruebas WHERE :fecha BETWEEN fecha_hora_inicio AND fecha_hora_fin", nativeQuery = true)
    List<Prueba> findPruebasEnUnMomentoNative(@Param("fecha") String fecha);

    //Reporte I
    List<Prueba> findByIncidenciaTrue();

    //Reporte II
    List<Prueba> findByEmpleado_LegajoAndAndIncidenciaTrue(int legajo);

    //Reporte IV
    List<Prueba> findByIdVehiculo(int id);



}
