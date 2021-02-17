package com.service1.service;

import com.service1.dao.CardHitsDAOImpl;
import com.service1.model.CardHits;
import java.util.List;
import java.util.logging.Logger;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Ravindra
 */
@Service
public class TransactionService {
    
    final static String DEFAULT_CARD_CHECK_URL = "https://lookup.binlist.net/";
    
    @Autowired
    CardHitsDAOImpl cardsDao;
    
    Logger logger = Logger.getLogger("myLogger");
    
    @Transactional
    public int addCard(String cardNumber) {
        return cardsDao.addCard(cardNumber);
    }
    
    @Transactional
    public List<CardHits> getHits(int limit, int offset) {
        return cardsDao.getAllByLimit(limit, offset);
    }
    
    @Transactional
    public CardHits getByCardNumber(String cardNumber) {
        return cardsDao.getByCardNumber(cardNumber);
    }
    
    @Transactional
    public void updateCard(CardHits hit){
        cardsDao.updateCard(hit);
    }
    
}
