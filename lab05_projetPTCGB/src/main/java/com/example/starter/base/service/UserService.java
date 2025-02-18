package com.example.starter.base.service;

import com.example.starter.base.dto.CardDTO;
import com.example.starter.base.dto.UserDTO;
import com.example.starter.base.entity.Users;
import com.example.starter.base.repository.InventoryRepository;
import com.example.starter.base.repository.UserRepository;
import com.example.starter.base.entity.Inventory;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.List;

/**
 * Service class for managing user operations.
 *
 * @author Edwin Haeffner
 * @author Arthur Junod
 * @author Eva Ray
 * @author Rachel Tranchida
 */
@ApplicationScoped
public class UserService {

    @Inject
    UserRepository userRepository;
    @Inject
    InventoryRepository inventoryRepository;

    /**
     * Retrieves a user by their ID and converts it to a UserDTO.
     *
     * @param id the ID of the user
     * @return the UserDTO representation of the user
     */
    @Transactional
    public UserDTO getById(int id) {
        return UserDTO.convertUserToDTO(userRepository.getById(id));
    }

    /**
     * Retrieves a user by their username and converts it to a UserDTO.
     *
     * @param username the username of the user
     * @return the UserDTO representation of the user
     */
    @Transactional
    public UserDTO getByUsername(String username) {
        Users user = userRepository.getByUsername(username);
        if (user == null) {
            return null;
        }
        return UserDTO.convertUserToDTO(user);
    }

    /**
     * Retrieves all users and converts them to a list of UserDTOs.
     *
     * @return a list of UserDTOs representing all users
     */
    @Transactional
    public List<UserDTO> getAllUsers() {
        var users = userRepository.getAll();
        return users.stream().map(UserDTO::convertUserToDTO).toList();
    }

    /**
     * Creates a new user with the specified username and password.
     *
     * @param username the username of the new user
     * @param password the password of the new user
     * @return a UserDTO representing the created user
     */
    @Transactional
    public UserDTO createUser(String username, String password, String email) {
        return UserDTO.convertUserToDTO(userRepository.createUser(username, password, email));
    }

    /**
     * Updates the username of an existing user.
     *
     * @param id       the ID of the user to update
     * @param username the new username of the user
     * @return a UserDTO representing the updated user
     */
    @Transactional
    public UserDTO updateUser(int id, String username) {
        return UserDTO.convertUserToDTO(userRepository.updateUser(id, username));
    }

    /**
     * Deletes a user by their ID.
     *
     * @param id the ID of the user to delete
     */
    @Transactional
    public void deleteUser(int id) {
        userRepository.deleteUser(id);
    }

    /**
     * Add a card to the user inventory
     *
     * @param userId the id of the user
     * @param cardId the id of the card
     */
    @Transactional
    public void addCard(int userId, int cardId) {
        Inventory inv = inventoryRepository.getByUserId(userId);
        inventoryRepository.addCard(inv.getId(), cardId);
    }

    /**
     * remove a card to the user inventory
     *
     * @param userId the id of the user
     * @param cardId the id of the card
     */
    @Transactional
    public void removeCard(int userId, int cardId) {
        Inventory inv = inventoryRepository.getByUserId(userId);
        inventoryRepository.removeCard(inv.getId(), cardId);
    }

    /**
     * get all the cards of a user
     *
     * @param userId the id of the user
     * @return the list of cards of the user
     */
    @Transactional
    public List<CardDTO> getCards(int userId) {
        Users user = userRepository.getById(userId);
        return inventoryRepository.getAllCards(user.getInv().getId()).stream().map(CardDTO::convertCardToDTO).toList();
    }
}
