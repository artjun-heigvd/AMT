package com.example.starter.base.dto;

import com.example.starter.base.entity.Inventory;
import com.example.starter.base.entity.Users;

/**
 * Data Transfer Object for the Inventory entity.
 * @param id the id of the inventory
 * @param userName the username of the user who owns the inventory
 *
 * @author Edwin Haeffner
 * @author Arthur Junod
 * @author Eva Ray
 * @author Rachel Tranchida
 */
public record InventoryDTO(Integer id, String userName) {
    public static InventoryDTO convertInventoryToDTO(Inventory inventory, Users user){
        return new InventoryDTO(inventory.getId(), user.getUsername());
    }
}
