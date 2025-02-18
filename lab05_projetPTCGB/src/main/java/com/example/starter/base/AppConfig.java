package com.example.starter.base;

import com.example.starter.base.dto.CardDTO;
import com.example.starter.base.service.CardService;
import com.example.starter.base.service.DeckService;
import com.example.starter.base.service.InventoryService;
import com.example.starter.base.service.UserService;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;

import java.util.ArrayList;

/**
 * Class to initialize the application with some data, such as users, cards, inventories, and decks.
 *
 * @author Edwin Haeffner
 * @author Arthur Junod
 * @author Eva Ray
 * @author Rachel Tranchida
 */
@ApplicationScoped
public class AppConfig {

    @Inject
    UserService userService;

    @Inject
    InventoryService inventoryService;

    @Inject
    CardService cardService;

    @Inject
    DeckService deckService;



    void onStart(@Observes StartupEvent ev) {

        // Create Users
        var user1 = userService.createUser("Edwin", BcryptUtil.bcryptHash("1234"), "edwin.haffner@heig-vd.ch");
        var user2 = userService.createUser("Eva", BcryptUtil.bcryptHash("1234"),"eva.ray@heig-vd.ch");
        var user3 = userService.createUser("Rachel", BcryptUtil.bcryptHash("1234"),"rachel.tranchida@heig-vd.ch");
        var user4 = userService.createUser("Arthur", BcryptUtil.bcryptHash("1234"), "arthur.junod@heig-vd.ch");

        // User 1's inventory
        for (int i = 1; i <= 10; i++) {
            // Add 3 copies of cards 1 and 2
            if (i <= 2) {
                inventoryService.addCard(i, user1.invId());
                inventoryService.addCard(i, user1.invId());
                inventoryService.addCard(i, user1.invId());
            }
            // Add 2 copies of cards 3-10
            else {
                inventoryService.addCard(i, user1.invId());
                inventoryService.addCard(i, user1.invId());
            }
        }

        // User 2's inventory
        for (int i = 11; i <= 20; i++) {
            // Add 3 copies of cards 11 and 12
            if (i <= 12) {
                inventoryService.addCard(i, user2.invId());
                inventoryService.addCard(i, user2.invId());
                inventoryService.addCard(i, user2.invId());
            }
            // Add 2 copies of cards 13-20
            else {
                inventoryService.addCard(i, user2.invId());
                inventoryService.addCard(i, user2.invId());
            }
        }

        // User 3's inventory
        for (int i = 21; i <= 30; i++) {
            // Add 3 copies of cards 21 and 22
            if (i <= 22) {
                inventoryService.addCard(i, user3.invId());
                inventoryService.addCard(i, user3.invId());
                inventoryService.addCard(i, user3.invId());
            }
            // Add 2 copies of cards 23-30
            else {
                inventoryService.addCard(i, user3.invId());
                inventoryService.addCard(i, user3.invId());
            }
        }

        // Create decks for each user
        deckService.newDeck("My Grass deck", user1.id());
        deckService.newDeck("Fire Deck", user2.id());
        deckService.newDeck("Water Deck", user3.id());

        // Add cards to User 1's deck
        ArrayList<CardDTO> cards = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            cards.add(cardService.getCardById(i));
        }
        deckService.updateInfos(1, "Grass", cards);

        // Add cards to User 2's deck
        cards.clear();
        for (int i = 11; i <= 20; i++) {
            cards.add(cardService.getCardById(i));
        }
        deckService.updateInfos(2, "Fire", cards);

        // Add cards to User 3's deck

        cards.clear();
        for (int i = 21; i <= 30; i++) {
            cards.add(cardService.getCardById(i));
        }
        deckService.updateInfos(3, "Water", cards);


    }
}
