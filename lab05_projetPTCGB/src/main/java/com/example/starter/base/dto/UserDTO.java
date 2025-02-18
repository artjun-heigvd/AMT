package com.example.starter.base.dto;

import com.example.starter.base.entity.Users;

/**
 * Data Transfer Object for the User entity.
 * @param id the id of the user
 * @param username the username of the user
 * @param email the email of the user
 * @param password the password of the user
 * @param invId the id of the inventory associated with the user
 *
 * @author Edwin Haeffner
 * @author Arthur Junod
 * @author Eva Ray
 * @author Rachel Tranchida
 */
public record UserDTO(Integer id, String username, String email, String password, Integer invId) {
    public static UserDTO convertUserToDTO(Users user) {
        return new UserDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                user.getInv().getId()
        );
    }
}
