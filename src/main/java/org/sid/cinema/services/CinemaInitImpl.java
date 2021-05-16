package org.sid.cinema.services;

import org.sid.cinema.dao.*;
import org.sid.cinema.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;
@Transactional
@Service
public class CinemaInitImpl implements ICinemaInitService{
    @Autowired
    private VilleRepository villeRepository;

    @Autowired
    private CinemaRepository cinemaRepository;

    @Autowired
    private SalleRepository salleRepository;

    @Autowired
    private PlaceRepository placeRepository;

    @Autowired
    private SeanceRepository seanceRepository;

    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private ProjectionFilmRepository projectionFilmRepository;

    @Autowired
    private CategorieRepository categorieRepository;

    @Autowired
    private TicketRepository ticketRepository;


    @Override
    public void initVilles() {
        Stream.of("CASA","MARAKECH","AGADIR","TANGER").forEach(name ->{
            Ville ville = new Ville();
            ville.setNom(name);
            villeRepository.save(ville);
        });
    }

    @Override
    public void initCinemas() {
        villeRepository.findAll().forEach(v->{
            Stream.of("Megarama","IMAX","Netflix","IClub","Dalli").forEach(cimemaName ->{
                Cinema cinema = new Cinema();
                cinema.setNom(cimemaName);
                cinema.setNombreSalles(3+(int)(Math.random()*7));
                cinema.setVille(v);
                cinemaRepository.save(cinema);
            });
        });

    }

    @Override
    public void initSalles() {
        cinemaRepository.findAll().forEach(cinema -> {
            for (int i = 0; i < cinema.getNombreSalles(); i++) {
                Salle salle = new Salle();
                salle.setNom("Salle"+(i+1));
                salle.setCinema(cinema);
                salle.setNombrePlaces(15+(int)(Math.random()*20));
                salleRepository.save(salle);
            }
        });
    }

    @Override
    public void initPlaces() {
        salleRepository.findAll().forEach(salle->{
            for (int i = 0; i < salle.getNombrePlaces(); i++) {
                Place place = new Place();
                place.setNumero(i+1);
                place.setSalle(salle);
                placeRepository.save(place);
            }
        });

    }

    @Override
    public void initSeances() {
        DateFormat dateFormat=new SimpleDateFormat("HH:mm");
        Stream.of("17:00","15:00","18:00","21:00","23:00").forEach(s->{
            Seance seance = new Seance();
            try {
                seance.setHeureDebut(dateFormat.parse(s));
                seanceRepository.save(seance);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    public void initCategories() {
            Stream.of("Histoire","actions","fictions","Drama").forEach(c->{
                Categorie categorie = new Categorie();
                categorie.setNom(c);
                categorieRepository.save(categorie);
            });
    }

    @Override
    public void initFilms() {
            List<Categorie> categorieList = categorieRepository.findAll();
            Double[] durees = {Double.valueOf(1),1.5, Double.valueOf(2),2.5,3.0};
            Stream.of("Little man","Breaking Bad","Batman","Superman","Catwomen").forEach(f->{
                Film film = new Film();
                film.setTitre(f);
                film.setDuree(durees[new Random().nextInt(durees.length)]);
                film.setPhoto(f+".jpg");
                film.setCategorie(categorieList.get(new Random().nextInt(categorieList.size())));
                filmRepository.save(film);
            });
    }

    @Override
    public void initProjections() {
        double prices[] = {30.0,65.0,70.0,65.0,80.0,90.0};
        villeRepository.findAll().forEach(v->{
            v.getCinema().forEach(cinema -> {
                cinema.getSalles().forEach(salle -> {
                    filmRepository.findAll().forEach(film -> {
                        seanceRepository.findAll().forEach(seance -> {
                            ProjectionFilm projectionFilm = new ProjectionFilm();
                            projectionFilm.setDateProjection(new Date());
                            projectionFilm.setFilm(film);
                            projectionFilm.setPrix(prices[new Random().nextInt(prices.length)]);
                            projectionFilm.setSalle(salle);
                            projectionFilm.setSeance(seance);
                            projectionFilmRepository.save(projectionFilm);
                        });
                    });
                });
            });
        });
    }

    @Override
    public void initTickets() {
        projectionFilmRepository.findAll().forEach(projectionFilm -> {
            projectionFilm.getSalle().getPlaces().forEach(place -> {
                Ticket ticket = new Ticket();
                ticket.setPlace(place);
                ticket.setPrix(projectionFilm.getPrix());
                ticket.setProjectionFilm(projectionFilm);
                ticket.setReserve(false);
                ticketRepository.save(ticket);
            });
        });
    }
}
