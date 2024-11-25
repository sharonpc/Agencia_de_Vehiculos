package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;

@Entity
@Table(name = "Pruebas")
@Data
@NoArgsConstructor
@AllArgsConstructor
//@JsonIgnoreProperties({"vehiculo", "interesado", "empleado"}) // Ignorar las propiedades completas
public class Prueba {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "ID_VEHICULO")
    private int idVehiculo;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "ID_INTERESADO")
    //@JsonManagedReference
    private Interesado interesado;

    @ManyToOne
    @JoinColumn(name = "ID_EMPLEADO")
    //@JsonManagedReference
    private Empleado empleado;

    @Column(name = "FECHA_HORA_INICIO", columnDefinition = "TIMESTAMP")
    private LocalDateTime fechaHoraInicio;

    @Column(name = "FECHA_HORA_FIN", columnDefinition = "TIMESTAMP")
    private LocalDateTime fechaHoraFin;

    @Column(name = "COMENTARIOS")
    private String comentarios;

    @Column(name = "INCIDENCIA")
    private boolean incidencia;

    public Prueba(int idVehiculo, Interesado interesado, Empleado empleado, LocalDateTime fechaHoraInicio) {
        this.idVehiculo = idVehiculo;
        this.interesado = interesado;
        this.empleado = empleado;
        this.fechaHoraInicio = fechaHoraInicio;
    }

    // Métodos adicionales para devolver solo los IDs

    public Integer getInteresadoId() {
        return interesado != null ? interesado.getId() : null;
    }

    public Integer getEmpleadoLegajo() {
        return empleado != null ? empleado.getLegajo() : null;
    }
}
