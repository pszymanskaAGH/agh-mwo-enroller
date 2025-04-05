package com.company.enroller.persistence;

import com.company.enroller.model.Participant;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.*;

@Component("participantService")
public class ParticipantService {

    DatabaseConnector connector;

    public ParticipantService() {
        connector = DatabaseConnector.getInstance();
    }

    //ok
    public Collection<Participant> getAll() {
        String hql = "FROM Participant";
        Query query = connector.getSession().createQuery(hql);
        return query.list();
    }

    //ok
    public Participant findByLogin(String login) {
        String hql = "FROM Participant WHERE login = :login";
        Query<Participant> query = connector.getSession()
                .createQuery(hql, Participant.class)
                .setParameter("login", login);

        List<Participant> results = query.list();
        if (results.isEmpty()) {
            return null;
        }
        return results.get(0);
    }

    //ok
    public void addUser(Participant participant) {
        Transaction tx = connector.getSession().beginTransaction();
        connector.getSession().save(participant);
        tx.commit();
    }

    public void deleteUser(Participant participant) {
        Transaction tx = connector.getSession().beginTransaction();
        connector.getSession().delete(participant);
        tx.commit();
    }

    public void updateUser(Participant participant) {
        Transaction tx = connector.getSession().beginTransaction();
        connector.getSession().update(participant);
        tx.commit();
    }

    public void sortDesc(List<Participant> participants) {
        Transaction tx = connector.getSession().beginTransaction();
        List<Participant> newList = new ArrayList<>(participants);
        newList.sort(Comparator.comparing(Participant::getLogin).reversed());

        for (Participant p : newList) {
            connector.getSession().update(p);
        }

        tx.commit();
    }


    public void sortAsc(List<Participant> participants) {
        Transaction tx = connector.getSession().beginTransaction();
        List<Participant> newList = new ArrayList<>(participants);
        newList.sort(Comparator.comparing(Participant::getLogin));

        for (Participant p : newList) {
            connector.getSession().update(p);
        }

        tx.commit();
    }
}
