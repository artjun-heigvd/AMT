package ch.heigvd.amt.user;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import java.time.LocalDateTime;

@ApplicationScoped
public class UserService {

    @Inject
    EntityManager em;

    /**
     * Crée un nouvel user et le persiste
     * @param username username du nouvel user
     * @param password password du nouvel user
     */
    @Transactional
    public void registerUser(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        em.persist(user);
    }

    /**
     *
     * @param userName username du last login
     */
    @Transactional
    public void updateLastLogin(String userName) {
        User user = em.find(User.class, userName);
        user.setLastLogin(LocalDateTime.now());
    }
}
