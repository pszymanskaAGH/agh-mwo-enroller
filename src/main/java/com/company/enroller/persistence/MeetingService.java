package com.company.enroller.persistence;

import java.util.Collection;

import com.company.enroller.model.Participant;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import com.company.enroller.model.Meeting;

@Component("meetingService")
public class MeetingService {

    DatabaseConnector connector;

    public MeetingService() {
        connector = DatabaseConnector.getInstance();
    }

    //ok
    public Collection<Meeting> getAll() {
        String hql = "FROM Meeting";
        Query query = connector.getSession().createQuery(hql);
        return query.list();
    }

    //ok
    public Meeting findById(long id) {
        Collection<Meeting> meetings = this.getAll();
        for (Meeting meeting : meetings) {
            if (meeting.getId() == id) {
                return meeting;
            }
        }
        return null;
    }

    //ok
    public void addMeeting(Meeting meeting) {
        Transaction tx = connector.getSession().beginTransaction();
        connector.getSession().save(meeting);
        tx.commit();
    }

    public void deleteMeeting(Meeting meeting) {
        Transaction tx = connector.getSession().beginTransaction();
        connector.getSession().delete(meeting);
        tx.commit();
    }

    public void editMeetingViaDate(Meeting meeting, String date) {
        meeting.setDate(date);
        Transaction tx = connector.getSession().beginTransaction();
        connector.getSession().update(meeting);
        tx.commit();
    }

    //ok
    public void addParticipantToMeeting(Participant participant, Meeting meeting) {
        meeting.getParticipants().add(participant);
        Transaction tx = connector.getSession().beginTransaction();
        connector.getSession().save(meeting);
        tx.commit();
    }

    //ok
    public void removeParticipantFromMeeting(Participant participant, Meeting meeting) {
        Collection<Participant> participants = meeting.getParticipants();
        participants.remove(participant);
        Transaction tx = connector.getSession().beginTransaction();
        connector.getSession().update(meeting);
        tx.commit();
    }

    //ok
    public void getParticipantsFromMeeting(Participant participant, Meeting meeting) {
//        Collection<Participant> participants = meeting.getParticipants();
//        participants.remove(participant);
//        Transaction tx = connector.getSession().beginTransaction();
//        connector.getSession().update(meeting);
//        tx.commit();
    }
}
