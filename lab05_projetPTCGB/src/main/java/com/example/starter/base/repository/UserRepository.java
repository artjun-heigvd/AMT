package com.example.starter.base.repository;

import com.example.starter.base.entity.Role;
import com.example.starter.base.entity.Users;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.transaction.Transactional;

import java.util.List;


/**
 * Repository for users
 *
 * @author Edwin Haeffner
 * @author Arthur Junod
 * @author Eva Ray
 * @author Rachel Tranchida
 */
@ApplicationScoped
public class UserRepository {

    @Inject
    EntityManager em;

    /**
     * Get a user by id
     * @param id the id of the user
     * @return the user
     */
    @Transactional
    public Users getById(int id){
        return em.find(Users.class, id);
    }

    /**
     * Get a user by username
     * @param username the username of the user
     * @return the user
     */
    @Transactional
    public Users getByUsername(String username) {
        try {
            return em.createQuery("SELECT u FROM Users u WHERE u.username = :username", Users.class)
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    /**
     * Get all users
     * @return a list of all users
     */
    @Transactional
    public List<Users> getAll(){
        String query = """
                SELECT u
                FROM Users u
        """;
        return em.createQuery(query, Users.class)
                .getResultList();
    }

    /**
     * Create a user
     * @param username the username of the user
     * @param password the password of the user
     * @return the created user
     */
    @Transactional
    public Users createUser(String username, String password, String email){
        Users user = new Users();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        Role role = new Role("user");
        em.persist(role);
        user.getRoles().add(role);
        em.persist(user);
        em.flush();
        return user;
    }

    /**
     * Update a user's username
     * @param id the id of the user
     * @param username the new username
     * @return the updated user
     */
    @Transactional
    public Users updateUser(int id, String username){
        Users user = em.find(Users.class, id);
        if (user == null){
            throw new IllegalArgumentException("User not found");
        }
        user.setUsername(username);
        return user;
    }

    /**
     * Delete a user
     * @param id the id of the user
     * @return the deleted user
     */
    @Transactional
    public Users deleteUser(int id){
        Users user = em.find(Users.class, id);
        if (user == null){
            throw new IllegalArgumentException("User not found");
        }
        em.remove(user);
        return user;
    }

}
