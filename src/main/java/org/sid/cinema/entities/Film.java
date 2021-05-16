package org.sid.cinema.entities;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class Film {

    public static final String FK_ID = "id_film";
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String titre;
    private double duree;
    private String realisateur;
    private String description;
    private String photo;
    private LocalDate dateSortie;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "film")
    private List<ProjectionFilm> projectionFilms;

    @ManyToOne
    private Categorie categorie;

}
