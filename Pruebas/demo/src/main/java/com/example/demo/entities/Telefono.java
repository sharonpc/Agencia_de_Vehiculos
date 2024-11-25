package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "telefonos")
public class Telefono {
    @Id
    @Column(name = "NUM_TELEFONO")
    private long num_telefono;

    @OneToOne
    @JoinColumn(name = "ID_INTERESADO")
   //@JsonBackReference
    private Interesado interesado;

    @OneToMany(mappedBy = "telefono")
    //@JsonManagedReference
    private List<Notificacion> notificaciones = new ArrayList<>();
}
