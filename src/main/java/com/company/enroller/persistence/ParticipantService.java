package com.company.enroller.persistence;

import com.company.enroller.model.Participant;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component("participantService")
public class ParticipantService {

    DatabaseConnector connector;

    public ParticipantService() {
        connector = DatabaseConnector.getInstance();
    }

    public List getAll() {
        String hql = "FROM Participant";
        Query query = connector.getSession().createQuery(hql);
        return query.list();
    }

    public Participant findByLogin(String login) {
        String hql = "FROM Participant Where login = '" + login + "'";
        Query query = connector.getSession().createQuery(hql);
        Participant participant = (Participant) query.list().get(0);
        return participant;
    }

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

    public void sortDesc() {
        Transaction tx = connector.getSession().beginTransaction();
        String hql = "FROM Participant";
        Query query = connector.getSession().createQuery(hql);
        List list = query.list();
        list.sort(Collections.reverseOrder());
        connector.getSession().update(list);
        tx.commit();
    }

    public void sortAsc() {
        Transaction tx = connector.getSession().beginTransaction();
        String hql = "FROM Participant";
        Query query = connector.getSession().createQuery(hql);
        List list = query.list();
        Collections.sort(list);
        connector.getSession().update(list);
        tx.commit();
    }
}
