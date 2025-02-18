package com.example.starter.base.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

/**
 * This class represents a Rating entity in the database.
 * It is used to store information about a rating and link it to users and decks.
 * It has the following fields:
 * - id: the id of the rating
 * - value: the value of the rating
 * - user: the user who gave the rating
 * - deck: the deck that was rated
 *
 * @author Edwin Haeffner
 * @author Arthur Junod
 * @author Eva Ray
 * @author Rachel Tranchida
 */
@Entity(name = "rating")
public class Rating {

    @Id
    @Column(name = "rating_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "value")
    @NotNull
    private Integer value;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull
    private Users user;

    @ManyToOne
    @JoinColumn(name = "deck_id")
    @NotNull
    private Deck deck;

    public Rating(Integer id, Integer value, Users user, Deck deck) {
        this.id = id;
        this.value = value;
        this.user = user;
        this.deck = deck;
    }

    public Rating() {

    }

    public @NotNull Integer getValue() {
        return value;
    }

    public void setValue(@NotNull Integer value) {
        this.value = value;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public @NotNull Users getUser() {
        return user;
    }

    public void setUser(@NotNull Users user) {
        this.user = user;
    }

    public @NotNull Deck getDeck() {
        return deck;
    }

    public void setDeck(@NotNull Deck deck) {
        this.deck = deck;
    }
}
