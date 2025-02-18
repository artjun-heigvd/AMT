package ch.heigvd.amt.jpa.service;

import ch.heigvd.amt.jpa.entity.*;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;

import jakarta.transaction.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * This class is a service that provides operations to rent a film.
 * Authors: Rachel Tranchida, Edwin Häffner, Arthur Junod, Eva Ray
 */
@ApplicationScoped
public class RentalService {

    @Inject
    EntityManager em;

    // The following records must not be changed
    public record RentalDTO(Integer inventory, Integer customer) {
    }

    public record FilmInventoryDTO(String title, String description, Integer inventoryId) {
    }

    public record CustomerDTO(Integer id, String firstName, String lastName) {
    }

    /**
     * Rent a film out of store's inventory for a given customer.
     *
     * @param inventory the inventory to rent
     * @param customer  the customer to which the inventory is rented
     * @param staff     the staff that process the customer's request in the store
     * @return an Optional that is present if rental is successful, if Optional is empty rental failed
     */
    @Transactional
    public Optional<RentalDTO> rentFilm(Inventory inventory, Customer customer, Staff staff) {

        em.createNativeQuery("LOCK TABLE rental IN SHARE MODE").executeUpdate();

        var checkLastRentalDate = """
                SELECT COUNT(r)
                FROM rental r
                WHERE r.inventory_id = ? AND r.return_date IS NULL
                """;

        var result = em.createNativeQuery(checkLastRentalDate)
                .setParameter(1, inventory.getId())
                .getSingleResult();

        if ((Long) result == 0) {
            Rental r = new Rental();
            r.setInventory(inventory);
            r.setCustomer(customer);
            r.setStaff(staff);
            r.setRentalDate(Timestamp.from(Instant.now()));
            em.persist(r);
            return Optional.of(new RentalDTO(inventory.getId(), customer.getId()));
        }

        return Optional.empty();
    }


    /**
     * @param query the searched string
     * @return films matching the query
     */
    public List<FilmInventoryDTO> searchFilmInventory(String query, Store store) {

        var result = em.createQuery("""
                        SELECT f.title, f.description, i.id
                        FROM inventory i
                        JOIN film f ON i.film.id = f.id
                        WHERE i.store.id = :store
                        """, FilmInventoryDTO.class)
                .setParameter("store", store.getId())
                .getResultList();

        result.removeIf(f -> {
            for (String s : query.toLowerCase().split(" ")) {
                if (!f.title().toLowerCase().contains(s) && !f.description().toLowerCase().contains(s) && !f.inventoryId().toString().contains(s)) {
                    return true;
                }
            }
            return false;

        });
        return result;


    }

    /**
     * Search a film by its inventory ID.
     * @param inventoryId the inventory ID
     * @return the film inventory
     */
    public FilmInventoryDTO searchFilmInventory(Integer inventoryId) {

        return em.createQuery("""
                        SELECT f.title, f.description, i.id
                        FROM inventory i
                        JOIN film f ON i.film = f
                        WHERE i.id = :inventoryId
                        """, FilmInventoryDTO.class)
                .setParameter("inventoryId", inventoryId)
                .getSingleResult();
    }

    /**
     * Search a customer by its ID.
     * @param customerId the customer ID
     * @return the customer
     */
    public CustomerDTO searchCustomer(Integer customerId) {

        return em.createQuery("""
                        SELECT c.id, c.firstName, c.lastName
                        FROM customer c
                        WHERE c.id = :customerId
                        """, CustomerDTO.class)
                .setParameter("customerId", customerId)
                .getSingleResult();
    }

    /**
     * Search a customer by its ID.
     * @param query the searched string
     * @param store the store
     * @return the customer
     */
    public List<CustomerDTO> searchCustomer(String query, Store store) {

        var result = em.createQuery("""
                             SELECT c.id, c.firstName, c.lastName
                             FROM customer c
                             WHERE c.store.id = :store
                        """, CustomerDTO.class)
                        .
                setParameter("store", store.getId())
                .getResultList();
        result.removeIf(c -> {
            for (String s : query.toLowerCase().split(" ")) {
                if (!c.firstName().toLowerCase().equals(s) && !c.lastName().toLowerCase().equals(s) && !c.id().toString().equals(s)) {
                    return true;
                }
            }
            return false;
        });
        return result;
    }
}

