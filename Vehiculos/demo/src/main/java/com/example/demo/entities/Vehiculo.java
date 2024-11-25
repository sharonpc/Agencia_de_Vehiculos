package com.example.demo.entities;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Vehiculos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "pruebas")
public class Vehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @OneToMany(mappedBy = "vehiculo")
    private List<Posicion> posiciones = new ArrayList<>();

    @Column(name = "PATENTE")
    private String patente;

    @ManyToOne
    @JoinColumn(name = "ID_MODELO")
    //@JsonBackReference
    private Modelo modelo;

    @Column(name = "ANIO")
    private int anio;

}
