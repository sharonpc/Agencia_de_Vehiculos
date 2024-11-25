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
@Table(name = "Modelos")
@Data
@NoArgsConstructor
@AllArgsConstructor
//sirve para excluir del ToString la lista de vehiculos y que no haya recursividad
@ToString(exclude = {"vehiculos", "marca"})
public class Modelo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @ManyToOne
    @JoinColumn(name = "ID_MARCA")
    //@JsonBackReference
    private Marca marca;

    @Column(name = "DESCRIPCION")
    private String descripcion;

    @OneToMany(mappedBy = "modelo")
    //JsonManagedReference
    private List<Vehiculo> vehiculos = new ArrayList<>();

}
