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
@Table(name = "Marcas")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "modelos") //sirve para que en el toString excluya mostrar la lista de modelos y no se haga una recursividad
public class Marca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "NOMBRE")
    private String nombre;

    @OneToMany(mappedBy = "marca")
    //@JsonManagedReference
    private List<Modelo> modelos = new ArrayList<>();



}
