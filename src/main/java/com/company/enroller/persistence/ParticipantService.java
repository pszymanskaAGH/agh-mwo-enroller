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

    public List<Participant> getAllSortedByLoginDESC() {
        Transaction tx = connector.getSession().beginTransaction();

        String hql = "FROM Participant ORDER BY login DESC";
        List<Participant> participants = connector.getSession()
                .createQuery(hql, Participant.class)
                .getResultList();

        tx.commit();
        return participants;
    }


    public List<Participant> getAllSortedByLoginAsc() {
        Transaction tx = connector.getSession().beginTransaction();

        String hql = "FROM Participant ORDER BY login ASC";
        List<Participant> participants = connector.getSession()
                .createQuery(hql, Participant.class)
                .getResultList();

        tx.commit();
        return participants;
    }

    public List<Participant> getLoginWhereKeyUser() {
        String hql = "FROM Participant WHERE login LIKE '%user%'";
        Query<Participant> query = connector.getSession()
                .createQuery(hql, Participant.class);

        List<Participant> results = query.list();
        if (results.isEmpty()) {
            return null;
        }
        return results;
    }

    public List<Participant> getLoginWhereKeyOg() {
        String hql = "FROM Participant WHERE login LIKE '%og%'";
        Query<Participant> query = connector.getSession()
                .createQuery(hql, Participant.class);

        List<Participant> results = query.list();
        if (results.isEmpty()) {
            return null;
        }
        return results;
    }
}
