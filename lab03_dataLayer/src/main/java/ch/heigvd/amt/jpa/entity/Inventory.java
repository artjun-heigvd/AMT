package ch.heigvd.amt.jpa.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

/**
 * This class represents an inventory item in the database.
 * The class contains the following attributes:
 * - id: The unique identifier for the inventory item.
 * - film: The film that is in the inventory.
 * - store: The store that has the inventory item.
 *
 * @author Arthur Junod
 * @author Edwin Häffner
 * @author Eva Ray
 * @author Rachel Tranchida
 */
@Entity(name = "inventory")
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventory_id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "film_id", nullable = false)
    @NotNull
    private Film film;

    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    @NotNull
    private Store store;

    public Inventory() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Film getFilm() {
        return film;
    }

    public void setFilm(Film film) {
        this.film = film;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "id=" + id +
                ", film=" + film.getId() + '\'' +
                ", store=" + store.getId() + '\'' +
                '}';
    }


}
