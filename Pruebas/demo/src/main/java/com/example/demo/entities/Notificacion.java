package com.example.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "notificaciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"telefono"})
public class Notificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_NOTIFICACION")
    private int id_notificacion;

    @ManyToOne
    @JoinColumn(name = "NUM_TELEFONO")
    //@JsonBackReference
    private Telefono telefono;

    @Column(name = "DESCRIPCION")
    private String descripcion;


    @Column(name = "TELEFONO_EMPLEADO")
    private String telefonoEmpleado;

    public long getTelefonoNumero() {
        return telefono != null ? telefono.getNum_telefono() : null;
    }

}
