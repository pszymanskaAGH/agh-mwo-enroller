package com.company.enroller.controllers;

import com.company.enroller.model.Meeting;
import com.company.enroller.model.Participant;
import com.company.enroller.persistence.MeetingService;
import com.company.enroller.persistence.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/meetings")
public class MeetingRestController {

    @Autowired
    MeetingService meetingService;

    @Autowired
    ParticipantService participantService;

    //ok
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<?> getMeetings() {
        Collection<Meeting> meetings = meetingService.getAll();
        return new ResponseEntity<>(meetings, HttpStatus.OK);
    }

    //ok
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getMeeting(@PathVariable("id") long id) {
        Meeting meeting = meetingService.findById(id);
        if (meeting == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(meeting, HttpStatus.OK);
    }

    //ok
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<?> addMeeting(@RequestBody Meeting meeting) {
        if (meeting.getId() == 0 || meeting.getDate() == null || meeting.getDescription() == null || meeting.getTitle() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Meeting p = meetingService.findById(meeting.getId());
        if (p != null) {
            return new ResponseEntity<>("Unable to create. A meeting with login " +
                    meeting.getId() + " already exist.", HttpStatus.CONFLICT);
        }

        meetingService.addMeeting(meeting);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //ok
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteMeeting(@PathVariable("id") long id) {
        Meeting meeting = meetingService.findById(id);
        if (meeting == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        meetingService.deleteMeeting(meeting);
        return new ResponseEntity<>(meeting, HttpStatus.OK);
    }

    //ok
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> editMeetingViaDate(@PathVariable("id") long id, @RequestBody String date) {
        Meeting meeting = meetingService.findById(id);
        if (meeting == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        meetingService.editMeetingViaDate(meeting, date);
        return new ResponseEntity<>(meeting, HttpStatus.OK);
    }

    //ok
    @RequestMapping(value = "/addParticipant", method = RequestMethod.POST)
    public ResponseEntity<?> addParticipantToMeeting(@RequestBody Meeting meeting) {
        Collection<Participant> participants = meeting.getParticipants();
        if (meeting.getId() == 0 || meeting.getDate() == null || meeting.getDescription() == null || meeting.getTitle() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Meeting m = meetingService.findById(meeting.getId());

        for (Participant participant : participants) {
            if (participant.getLogin() == null || participant.getLogin().isEmpty()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            Participant p = participantService.findByLogin(participant.getLogin());
            if (p == null) {
                return new ResponseEntity<>("Unable to add participant to meeting, because participant is null", HttpStatus.NOT_FOUND);
            }
            if (m == null) {
                return new ResponseEntity<>("Unable to add participant to meeting, because meeting is null", HttpStatus.NOT_FOUND);
            }

            meetingService.addParticipantToMeeting(participant, meeting);

        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //ok
    @RequestMapping(value = "/removeParticipant", method = RequestMethod.PUT)
    public ResponseEntity<?> removeParticipantFromMeeting(@RequestBody Meeting meeting) {
        Collection<Participant> participants = meeting.getParticipants();
        if (meeting.getId() == 0 || meeting.getDate() == null || meeting.getDescription() == null || meeting.getTitle() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Meeting m = meetingService.findById(meeting.getId());

        for (Participant participant : participants) {
            if (participant.getLogin() == null || participant.getLogin().isEmpty()) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            Participant p = participantService.findByLogin(participant.getLogin());
            if (p == null) {
                return new ResponseEntity<>("Unable to add participant to meeting, because participant is null", HttpStatus.NOT_FOUND);
            }
            if (m == null) {
                return new ResponseEntity<>("Unable to add participant to meeting, because meeting is null", HttpStatus.NOT_FOUND);
            }

            meetingService.removeParticipantFromMeeting(participant, meeting);

        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //3 podpunktcik z dalsze kroki
    @RequestMapping(value = "/getParticipantsFromMeeting", method = RequestMethod.GET)
    public ResponseEntity<?> getParticipantsFromMeeting(@RequestBody Meeting meeting) {
        Collection<Participant> participants = meeting.getParticipants();
        if (meeting.getId() == 0 || meeting.getDate() == null || meeting.getDescription() == null || meeting.getTitle() == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Meeting m = meetingService.findById(meeting.getId());
//        m.

//        for (Participant participant : participants) {
//            if (participant.getLogin() == null || participant.getLogin().isEmpty()) {
//                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//            }
//
//            if (p == null) {
//                return new ResponseEntity<>("Unable to add participant to meeting, because participant is null", HttpStatus.NOT_FOUND);
//            }
//            if (m == null) {
//                return new ResponseEntity<>("Unable to add participant to meeting, because meeting is null", HttpStatus.NOT_FOUND);
//            }
//
//            meetingService.removeParticipantFromMeeting(participant, meeting);
//
//        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
//#POST http://localhost:8090/meetings/
//        #Content-Type: application/json
//#
//        #
//        #  {
//        #    "id": 3,
//        #    "title": "some title",
//        #    "description": "some description",
//        #    "date": "some date"
//        #  }
//
//
//        #DELETE http://localhost:8090/meetings/3
//
//        #GET http://localhost:8090/participants/
//
//        #PUT http://localhost:8090/participants/sortDESC
//        #PUT http://localhost:8090/participants/keyUser
//
//        #PUT http://localhost:8090/meetings/2
//        #Content-Type: application/json
//#
//        #"date"
//
//
//PUT http://localhost:8090/meetings/removeParticipant
//Content-Type: application/json
//
//{
//    "id": 3,
//        "title": "some title",
//        "description": "some description",
//        "date": "some date",
//        "participants": [
//    {
//        "login": "user2",
//            "password": "some password"
//    }
//  ]
//}
//
//
//
//
