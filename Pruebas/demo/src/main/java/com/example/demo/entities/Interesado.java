package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Interesados")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "pruebas")
//@JsonIgnoreProperties({"pruebas", "telefono"})
public class Interesado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "TIPO_DOCUMENTO")
    private String tipoDocumento;

    @Column(name = "DOCUMENTO")
    private String documento;

    @Column(name = "NOMBRE")
    private String nombre;

    @Column(name = "APELLIDO")
    private String apellido;

    @Column(name = "RESTRINGIDO")
    private boolean restringido;

    @Column(name = "NRO_LICENCIA")
    private int nroLicencia;

    @Column(name = "FECHA_VENCIMIENTO_LICENCIA")
    private LocalDateTime fechaVencimientoLicencia;

    //@JsonBackReference
    @OneToMany(mappedBy = "interesado")
    private List<Prueba> pruebas = new ArrayList<>();

    @OneToOne(mappedBy = "interesado")
    //@JsonBackReference
    private Telefono telefono;

}
