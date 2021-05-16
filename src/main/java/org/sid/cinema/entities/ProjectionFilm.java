package org.sid.cinema.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class ProjectionFilm {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private Date dateProjection;
    private double prix;


    @OneToMany(mappedBy = "projectionFilm")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Ticket> tickets;

    @ManyToOne
    private Seance seance;

    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Salle salle;

    @ManyToOne
    private Film film;


}
