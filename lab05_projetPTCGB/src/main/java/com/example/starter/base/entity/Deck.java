package com.example.starter.base.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a Deck entity in the database.
 * It is used to store information about a deck and link it to users, cards, and ratings.
 * It has the following fields:
 * - id: the id of the deck
 * - name: the name of the deck
 * - type: the type of the deck
 * - user: the user who owns the deck
 * - ratings: the list of ratings for the deck
 * - cards: the list of cards in the deck
 *
 * @author Edwin Haeffner
 * @author Arthur Junod
 * @author Eva Ray
 * @author Rachel Tranchida
 */
@Entity(name = "deck")
@NamedEntityGraph(name = "deck_card", attributeNodes = @NamedAttributeNode("cards"))
public class Deck {

    @Id
    @Column(name = "deck_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    @NotNull
    private Type type;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull
    private Users user;

    @OneToMany(mappedBy = "deck", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Rating> ratings;

    @ManyToMany
    @JoinTable(name = "deck_card",
            joinColumns = @JoinColumn(name = "deck_id"),
            inverseJoinColumns = @JoinColumn(name = "card_id")
    )
    @Size(max = 20)
    @NotNull
    private List<Card> cards;

    public Deck(Integer id, String name, Type type, Users user, List<Card> cards, List<Rating> ratings) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.user = user;
        this.cards = cards;
        this.ratings = ratings;
    }

    public Deck() {
        this.type = Type.GRASS; // Set a default type
        this.cards = new ArrayList<>(); // Initialize the cards list
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public @NotNull Type getType() {
        return type;
    }

    public void setType(@NotNull Type type) {
        this.type = type;
    }

    public @NotNull Users getUser() {
        return user;
    }

    public void setUser(@NotNull Users user) {
        this.user = user;
    }

    public @Size(max = 20) @NotNull List<Card> getCards() {
        return cards;
    }

    public void setCards(@Size(max = 20) @NotNull List<Card> cards) {
        this.cards = cards;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    public void addRating(Rating rating) {
        ratings.add(rating);
    }

    public void resetRatings() {
        ratings.clear();
    }

    public void addCard(Card card) {
        cards.add(card);
        card.getDecks().add(this);
    }

    public void removeCard(Card card) {
        cards.remove(card);
        card.getDecks().remove(this);
    }
}
