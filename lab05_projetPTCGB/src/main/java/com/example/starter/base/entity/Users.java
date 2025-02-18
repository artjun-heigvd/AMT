package com.example.starter.base.entity;

import io.quarkus.security.jpa.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a User entity in the database.
 * It is used to store information about a user and link it to inventories, decks, and ratings.
 * It has the following fields:
 * - id: the id of the user
 * - username: the username of the user
 * - password: the password of the user
 * - inv: the inventory associated with the user
 * - decks: the list of decks owned by the user
 * - ratings: the list of ratings given by the user
 *
 * @author Edwin Haeffner
 * @author Arthur Junod
 * @author Eva Ray
 * @author Rachel Tranchida
 */
@Entity(name = "Users")
public class Users {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "username")
    @NotNull
    @NotEmpty
    @NotBlank
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    @NotNull
    @NotEmpty
    @NotBlank
    private String password;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Inventory inv;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Deck> decks;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Rating> ratings;

    @Roles
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "users_role",
            joinColumns = @JoinColumn(name = "users_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles = new ArrayList<>();

    public Users(Integer id, String username, String email, String password, Inventory inv, List<Deck> decks, List<Rating> ratings) {

        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.inv = new Inventory();
        this.decks = decks;
        this.ratings = ratings;
        this.inv.setUser(this);
        this.roles = new ArrayList<>();

    }

    public Users() {
        this.inv = new Inventory();
        this.inv.setUser(this);
        this.roles = new ArrayList<>();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public @NotNull @NotEmpty @NotBlank String getPassword() {
        return password;
    }

    public void setPassword(@NotNull @NotEmpty @NotBlank String password) {
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public @NotNull @NotEmpty @NotBlank String getUsername() {
        return username;
    }

    public void setUsername(@NotNull @NotEmpty @NotBlank String username) {
        this.username = username;
    }

    public Inventory getInv() {
        return inv;
    }

    public void setInv(Inventory inv) {
        this.inv = inv;
    }

    public List<Deck> getDecks() {
        return decks;
    }

    public void setDecks(List<Deck> decks) {
        this.decks = decks;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    public void setRole(String roleName) {
        Role role = new Role(roleName);
        if (this.roles == null) {
            this.roles = new ArrayList<>();
        }
        this.roles.add(role);
    }

    public List<Role> getRoles() {
        return roles;
    }
}

