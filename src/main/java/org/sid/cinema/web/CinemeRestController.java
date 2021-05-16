package org.sid.cinema.web;

import lombok.Data;
import org.sid.cinema.dao.FilmRepository;
import org.sid.cinema.dao.TicketRepository;
import org.sid.cinema.entities.Film;
import org.sid.cinema.entities.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@RestController
public class CinemeRestController {
    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @GetMapping(path="/imagefilm/{id}",produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] image(@PathVariable(name = "id") Long id) throws Exception{
        Film f = filmRepository.findById(id).get();
        File file = new File(System.getProperty("user.home")+"/Fimimages/"+f.getPhoto());
        Path path = Paths.get(file.toURI());
        return Files.readAllBytes(path);
    }

    @GetMapping("/listefilms")
    public List<Film> listFilms(){
        return filmRepository.findAll();
    }
    @Transactional
    @PostMapping("/payerTickets")
    public List<Ticket> payerTickets(@RequestBody TicketForm ticketForm){
        List<Ticket> listTickets = new ArrayList<>();
        ticketForm.getTickets().forEach(idTicket ->{
            Ticket ticket =ticketRepository.findById(idTicket).get();
            ticket.setNomClient(ticketForm.getNomClient());
            ticket.setCodePaiement(ticketForm.getCodepayment());
            ticket.setReserve(true);
            ticketRepository.save(ticket);
            listTickets.add(ticket);
        });
        return listTickets;
    }
}
@Data
class TicketForm{
    private String nomClient;
    private List<Long> tickets = new ArrayList<>();
    private Long codepayment;
}
