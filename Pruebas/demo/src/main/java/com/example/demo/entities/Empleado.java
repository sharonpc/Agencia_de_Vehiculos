package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "Empleados")
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "pruebas")
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LEGAJO")
    private int legajo;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "APELLIDO")
    private String apellido;

    @Column(name = "TELEFONO_CONTACTO")
    private String telefono_contacto;

    @OneToMany(mappedBy = "empleado")
    //@JsonBackReference
    private List<Prueba> pruebas = new ArrayList<>();


}
