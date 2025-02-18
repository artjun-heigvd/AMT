package com.example.starter.base.repository;

import com.example.starter.base.entity.Card;
import com.example.starter.base.entity.Rarity;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.transaction.Transactional;
import java.util.List;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

/**
 * Repository for cards
 *
 * @author Edwin Haeffner
 * @author Arthur Junod
 * @author Eva Ray
 * @author Rachel Tranchida
 */
@ApplicationScoped
public class CardRepository {
    @Inject
    EntityManager em;

    /**
     * Get a card by id (exact match)
     * @param id    id of the card we get
     * @return      a single card or none
     */
    @Transactional
    public Card getById(int id){
        return em.find(Card.class, id);
    }

    /**
     * Get a list of cards with a rarity above or below a certain threshold, ordered by a column
     * @param rarity The rarity threshold
     * @param aboveEq True if we want rarity above or equal to the threshold, false otherwise
     * @param column The column we order by
     * @param asc True if we want ascending order, false otherwise
     * @return A list of cards
     */
    @Transactional
    public List<Card> getThresholdRarityOrdered(Rarity rarity, boolean aboveEq ,String column, boolean asc){
        StringBuilder query = new StringBuilder("""
                SELECT c
                FROM card c
                WHERE rarity
                """)
                .append(aboveEq ? " >= " : " <= ")
                .append(rarity.toString())
                .append("\n ORDER BY ")
                .append(column)
                .append(asc ? "ASC" : "DESC");

        return em.createQuery(query.toString(), Card.class).getResultList();
    }

    /**
     * Get a list of all cards sorted by the column given in ascending or descending
     * @param column    Column we order by
     * @param asc       True if ascending order, descending otherwise
     * @return          An ordered list of all cards
     */
    @Transactional
    public List<Card> getAllOrdered(String column, boolean asc){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Card> criteria = cb.createQuery(Card.class);
        Root<Card> root = criteria.from(Card.class);
        if (asc){
            return em.createQuery(criteria.select(root).orderBy(cb.asc(root.get(column)))).getResultList();
        }else{
            return em.createQuery(criteria.select(root).orderBy(cb.desc(root.get(column)))).getResultList();
        }
    }
}
