package ch.heigvd.amt.jpa.repository;

import ch.heigvd.amt.jpa.entity.Inventory;
import ch.heigvd.amt.jpa.entity.Store;
import ch.heigvd.amt.jpa.entity.Film;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

/**
 * Repository class for managing Inventory entities in the database.
 * Provides methods for creating, reading, updating, and deleting Inventory records.
 * This class uses an {@link EntityManager} to interact with the persistence context.
 *
 * @author Eva Ray
 * @author Rachel Tranchida
 * @author Arthur Junod
 * @author Edwin Häffner
 */
@ApplicationScoped
public class InventoryRepository {

    @Inject
    private EntityManager em;

    /**
     * Converts an {@link Inventory} entity to an {@link InventoryDTO}.
     *
     * @param inventory the Inventory entity to convert
     * @return an InventoryDTO representing the inventory, or null if the inventory is null
     */
    private InventoryRepository.InventoryDTO fromEntityToDTO(Inventory inventory) {
        if (inventory == null) {
            return null;
        }
        return new InventoryRepository.InventoryDTO(inventory.getId(), inventory.getFilm().getId(), inventory.getStore().getId());
    }

    /**
     * Data Transfer Object (DTO) for the Inventory entity.
     * Encapsulates an Inventory's ID, associated Film ID, and Store ID.
     */
    public record InventoryDTO(Integer id, Integer filmdId, Integer storeId) {

        /**
         * Static factory method to create an InventoryDTO without specifying an ID.
         * Useful for creating new Inventory entries.
         *
         * @param filmdId the ID of the Film associated with this inventory
         * @param storeId the ID of the Store associated with this inventory
         * @return an InventoryDTO with a null ID
         */
        public static InventoryDTO create(Integer filmdId, Integer storeId) {
            return new InventoryDTO(null, filmdId, storeId);
        }
    }

    /**
     * Retrieves an inventory from the database by its ID.
     *
     * @param id the ID of the inventory to retrieve
     * @return an {@link InventoryDTO} representing the retrieved inventory, may be null if no inventory with the given ID exists
     */
    public InventoryDTO read(Integer id) {
        Inventory inventory = em.find(Inventory.class, id);
        return this.fromEntityToDTO(inventory);
    }

    /**
     * Creates a new inventory in the database.
     * Validates that the specified Film and Store exist before creating the record.
     *
     * @param inventory the {@link InventoryDTO} representing the inventory to create
     * @return an {@link InventoryDTO} representing the newly created inventory
     * @throws IllegalArgumentException if the specified Film or Store does not exist
     */
    @Transactional
    public InventoryDTO create(InventoryDTO inventory) {
        Inventory inventoryEntity = new Inventory();

        Film film = em.find(Film.class, inventory.filmdId);
        if (film == null) {
            throw new IllegalArgumentException("Film with id " + inventory.filmdId + " does not exist");
        }
        inventoryEntity.setFilm(film);

        Store store = em.find(Store.class, inventory.storeId);
        if (store == null) {
            throw new IllegalArgumentException("Store with id " + inventory.storeId + " does not exist");
        }
        inventoryEntity.setStore(store);

        em.persist(inventoryEntity);
        return fromEntityToDTO(inventoryEntity);
    }

    /**
     * Updates an existing inventory in the database.
     * Ensures that the specified Inventory exists and updates its associated Film and Store.
     *
     * @param inventory the {@link InventoryDTO} containing the updated inventory information
     * @throws IllegalArgumentException if no inventory with the specified ID exists
     */
    @Transactional
    public void update(InventoryDTO inventory) {
        Inventory inventoryEntity = em.find(Inventory.class, inventory.id);

        if (inventoryEntity == null) {
            throw new IllegalArgumentException("Inventory with id " + inventory.id + " does not exist");
        }

        inventoryEntity.setFilm(em.find(Film.class, inventory.filmdId));
        inventoryEntity.setStore(em.find(Store.class, inventory.storeId));
    }

    /**
     * Deletes an inventory from the database by its ID.
     *
     * @param id the ID of the inventory to delete
     * @throws IllegalArgumentException if no inventory with the given ID exists
     */
    @Transactional
    public void delete(Integer id) {
        Inventory inventory = em.find(Inventory.class, id);

        if (inventory == null) {
            throw new IllegalArgumentException("Inventory with id " + id + " does not exist");
        }
        em.remove(inventory);
    }
}
