package ch.heigvd.amt.jpa.repository;

import ch.heigvd.amt.jpa.entity.*;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

/**
 * A repository that allow to get staff entities.
 * Authors: Rachel Tranchida, Edwin Häffner, Arthur Junod, Eva Ray
 */
@ApplicationScoped
public class StaffRepository {
    @Inject
    EntityManager em;

    /**
     * Search a staff by its username (we consider it's unique)
     * @param username Username of the staff researched
     * @return A staff entity
     */
    @Transactional
    public Staff searchStaff(String username) {
        return em.createQuery("""
                        SELECT s
                        FROM Staff s
                        WHERE s.username = :username
                        """, Staff.class)
                .setParameter("username", username)
                .getSingleResult();
    }
}
