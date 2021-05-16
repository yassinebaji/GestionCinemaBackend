package org.sid.cinema.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data

@Entity
public class Cinema {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private Double longitude, latitude, altitude;
    private int NombreSalles;

    @OneToMany(mappedBy = "cinema")
    private Collection <Salle> salles;
    @ManyToOne
    private Ville ville;

}