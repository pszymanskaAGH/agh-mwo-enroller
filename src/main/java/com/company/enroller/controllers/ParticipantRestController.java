package com.company.enroller.controllers;

import com.company.enroller.model.Participant;
import com.company.enroller.persistence.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/participants")
public class ParticipantRestController {

    @Autowired
    ParticipantService participantService;


    //ok
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> getParticipants() {
        Collection<Participant> participants = participantService.getAll();
        return new ResponseEntity<Collection<Participant>>(participants, HttpStatus.OK);
    }

    //ok
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getParticipant(@PathVariable("id") String login) {
        Participant participant = participantService.findByLogin(login);
        if (participant == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(participant, HttpStatus.OK);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> registerParticipant(@RequestBody Participant participant) {
        if (participant.getLogin() == null || participant.getPassword() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Participant p = participantService.findByLogin(participant.getLogin());
        if (p != null) {
            return new ResponseEntity<>("Unable to create. A participant with login " +
                    participant.getLogin() + " already exist.", HttpStatus.CONFLICT);
        }

        participantService.addUser(participant);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //ok
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUser(@PathVariable("id") String login) {
        Participant participant = participantService.findByLogin(login);
        if (participant == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        participantService.deleteUser(participant);
        return new ResponseEntity<>(participant, HttpStatus.OK);
    }

    //ok
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> updateUserPass(@RequestBody Participant participant) {
        Participant copy = participantService.findByLogin(participant.getLogin());
        if (copy == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        copy.setPassword(participant.getPassword());
        participantService.updateUser(copy);
        return new ResponseEntity<>(copy, HttpStatus.OK);
    }

    @RequestMapping(value = "/sortDESC", method = RequestMethod.PUT)
    public ResponseEntity<?> sortDesc() {
        List<Participant> sorted = participantService.getAllSortedByLoginDESC();
        return new ResponseEntity<>(sorted, HttpStatus.OK);
    }

    @RequestMapping(value = "/sortASC", method = RequestMethod.PUT)
    public ResponseEntity<?> sortASC() {
        List<Participant> sorted = participantService.getAllSortedByLoginAsc();
        return new ResponseEntity<>(sorted, HttpStatus.OK);
    }
}
