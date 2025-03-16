package com.company.enroller.controllers;

import com.company.enroller.model.Participant;
import com.company.enroller.persistence.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/participants")
public class ParticipantRestController {

    @Autowired
    ParticipantService participantService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> getParticipants() {
        Collection<Participant> participants = participantService.getAll();
        return new ResponseEntity<Collection<Participant>>(participants, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getParticipant(@PathVariable("id") String login) {
        Participant participant = participantService.findByLogin(login);
        if (participant == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(participant, HttpStatus.OK);
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> registerParticipant(@RequestBody Participant participant) {
        if (participant.getLogin() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (participant.getPassword() == null) {
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

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUser(@PathVariable("id") String login) {
        Participant participant = participantService.findByLogin(login);
        if (participant == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        participantService.deleteUser(participant);
        return new ResponseEntity<>(participant, HttpStatus.OK);
    }

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
        Collection<Participant> participants = participantService.getAll();
        participantService.sortDesc(participants);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/sortASC", method = RequestMethod.PUT)
    public ResponseEntity<?> sortASC() {
        Collection<Participant> participants = participantService.getAll();
        participantService.sortAsc(participants);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
