package ch.heigvd.amt.jpa.entity;

import io.quarkus.security.jpa.RolesValue;
import jakarta.persistence.*;

/**
 * This class is an entity representing a role in the database.
 * Authors: Rachel Tranchida, Edwin Häffner, Arthur Junod, Eva Ray
 */
@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    @RolesValue
    private String role;

    // Getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
