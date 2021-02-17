/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.service1.dao;

import com.service1.model.CardHits;
import java.util.List;
import java.util.logging.Logger;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Ravindra
 */
@Repository
public class CardHitsDAOImpl {

    @Autowired
    private SessionFactory sessionFactory;

    Logger logger = Logger.getLogger("DAOLOgger");

    public void setSessionFactory(SessionFactory sf) {
        this.sessionFactory = sf;
    }

    public List<CardHits> getAll() {
        @SuppressWarnings("unchecked")
        String query = "from CardHits";
        return this.sessionFactory.getCurrentSession().createQuery(query).list();
    }

    public CardHits getByCardNumber(String carNumber) {
        @SuppressWarnings("unchecked")
        Criteria crit = this.sessionFactory.getCurrentSession().createCriteria(CardHits.class);
        crit.add(Restrictions.eq("cardNumber", carNumber));
        return (CardHits) crit.uniqueResult();
    }

    public List<CardHits> getAllByLimit(int limit, int offset) {
        @SuppressWarnings("unchecked")
        Criteria crit = this.sessionFactory.getCurrentSession().createCriteria(CardHits.class);
        crit.setMaxResults(limit);
        crit.setFirstResult(offset);
        return (List<CardHits>) crit.list();
    }

    public Integer addCard(String cardNumber) {
        @SuppressWarnings("unchecked")
        CardHits obj = new CardHits();
        obj.setCardNumber(cardNumber);
        obj.setHits(1);
        return (Integer) this.sessionFactory.getCurrentSession().save(obj);
    }

    public void updateCard(CardHits hit) {
        this.sessionFactory.getCurrentSession().update(hit);
    }

}
