
package com.service1.dao;

import com.service1.model.CardHits;
import java.util.List;

/**
 *
 * @author Ravindra
 */
public interface CardHitsDAO {
    public CardHits addCard(long cardNumber);
    public List<CardHits> getAll();
    public CardHits getByCardNumber(long carNumber);
    public List<CardHits> getAllByLimit(int limit,int offset);
}
