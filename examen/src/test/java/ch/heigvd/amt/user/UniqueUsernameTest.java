package ch.heigvd.amt.user;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import jakarta.transaction.UserTransaction;
import org.hibernate.Session;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class UniqueUsernameTest {
    @Inject
    EntityManager em;

    @Inject
    UserTransaction transaction;

    @Test
    public void testUniqueUsername() throws Exception {
        transaction.begin();
        insertUser("amt", "amt123");
        transaction.commit();

        try {
            transaction.begin();
            insertUser("amt", "amt123");
            transaction.commit();
        } catch (PersistenceException e) {
            transaction.rollback();
        }

        transaction.begin();
        Assertions.assertEquals(1L, nbUsersWithSameUsername("amt"));
        transaction.commit();
    }

    private Long nbUsersWithSameUsername(String username) {
        return em.createQuery("SELECT count(u) FROM User u WHERE u.username = :username", Long.class)
                .setParameter("username", username)
                .getSingleResult();
    }

    private void insertUser(String username, String password) {
        var user = new User();
        user.setUsername(username);
        user.setPassword(password);
        em.persist(user);
    }
}
