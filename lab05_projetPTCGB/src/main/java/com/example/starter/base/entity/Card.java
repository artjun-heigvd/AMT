package com.example.starter.base.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a Card entity in the database.
 * It is used to store information about a card and link it to inventories and decks.
 * It has the following fields:
 * - id: the id of the card
 * - name: the name of the card
 * - rarity: the rarity of the card
 * - type: the type of the card
 * - health: the health value of the card
 * - stage: the stage of the card
 * - craftingCost: the crafting cost of the card
 * - image: the image URL of the card
 * - extension: the extension of the card
 * - inventories: the list of inventories containing the card
 * - decks: the list of decks containing the card
 *
 * @author Edwin Haeffner
 * @author Arthur Junod
 * @author Eva Ray
 * @author Rachel Tranchida
 */
@Entity(name = "card")
public class Card {

    @Id
    @Column(name = "card_id")
    private Integer id;

    @Column(name = "name")
    @NotNull
    private String name;

    @Column(name = "rarity")
    @NotNull
    private Rarity rarity;

    @Column(name = "type")
    @NotNull
    private Type type;

    @Column(name = "health")
    @NotNull
    @Positive
    private Integer health;

    @Column(name = "stage")
    private Stage stage;

    @Column(name = "crafting_cost")
    private Integer craftingCost;

    @Column(name = "image")
    private String image;

    @Column(name = "extension")
    private String extension;

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    @ManyToMany(mappedBy = "cards")
    private List<Inventory> inventories = new ArrayList<>();

    public Card() {
    }

    public List<Inventory> getInventories() {
        return inventories;
    }

    @ManyToMany(mappedBy = "cards")
    private List<Deck> decks = new ArrayList<>();

    public List<Deck> getDecks() {
        return decks;
    }

    public @NotNull String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    public @NotNull Rarity getRarity() {
        return rarity;
    }

    public void setRarity(@NotNull Rarity rarity) {
        this.rarity = rarity;
    }

    public @NotNull Type getType() {
        return type;
    }

    public void setType(@NotNull Type type) {
        this.type = type;
    }

    public @NotNull @Positive Integer getHealth() {
        return health;
    }

    public void setHealth(@NotNull @Positive Integer health) {
        this.health = health;
    }

    public @NotNull Stage getStage() {
        return stage;
    }

    public void setStage(@NotNull Stage stage) {
        this.stage = stage;
    }

    public Integer getCraftingCost() {
        return craftingCost;
    }

    public void setCraftingCost(Integer craftingCost) {
        this.craftingCost = craftingCost;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setInventories(List<Inventory> inventories) {
        this.inventories = inventories;
    }

    public Card(Integer id, String name, Rarity rarity, Type type, Integer health, Stage stage, Integer craftingCost, String image, String extension, List<Inventory> inventories, List<Deck> decks) {
        this.id = id;
        this.name = name;
        this.rarity = rarity;
        this.type = type;
        this.health = health;
        this.stage = stage;
        this.craftingCost = craftingCost;
        this.image = image;
        this.extension = extension;
        this.inventories = inventories;
        this.decks = decks;
    }

    public void setDecks(List<Deck> decks) {
        this.decks = decks;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
