package com.example.starter.base.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents an Inventory entity in the database.
 * It is used to store information about an inventory and link it to users and cards.
 * It has the following fields:
 * - id: the id of the inventory
 * - cards: the list of cards in the inventory
 * - user: the user who owns the inventory
 *
 * @author Edwin Haeffner
 * @author Arthur Junod
 * @author Eva Ray
 * @author Rachel Tranchida
 */
@Entity(name = "inventory")
public class Inventory {
    @Id
    @Column(name = "inventory_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToMany
    @JoinTable(name = "card_inventory",
            joinColumns = @JoinColumn(name = "inventory_id"),
            inverseJoinColumns = @JoinColumn(name = "card_id")
    )
    private List<Card> cards = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "user_id")
    private Users user;

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Inventory(Integer id, List<Card> cards) {
        this.id = id;
        this.cards = cards;
    }

    public Inventory() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public void addCard(Card card) {
        cards.add(card);
        card.getInventories().add(this);
    }

    public void removeCard(Card card) {
        cards.remove(card);
        card.getInventories().remove(this);
    }
}
