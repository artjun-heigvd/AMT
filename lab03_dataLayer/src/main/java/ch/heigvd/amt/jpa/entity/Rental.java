package ch.heigvd.amt.jpa.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.sql.Timestamp;

/**
 * This class represents a rental in the database.
 * The class contains the following attributes:
 * - id: The unique identifier for the rental.
 * - rentalDate: The date and time the rental was initiated.
 * - returnDate: The date and time the rental was returned (can be null if not yet returned).
 * - staff: The staff member who processed the rental.
 * - inventory: The inventory item that was rented.
 * - customer: The customer who rented the item.
 *
 * @author Arthur Junod
 * @author Edwin Häffner
 * @author Eva Ray
 * @author Rachel Tranchida
 */

@Entity(name = "rental")
public class Rental {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rental_id")
    private Integer id;

    @Column(name = "rental_date")
    @NotNull
    private Timestamp rentalDate;

    @Column(name = "return_date")
    private Timestamp returnDate;

    @ManyToOne
    @JoinColumn(name = "staff_id", nullable = false)
    @NotNull
    private Staff staff;

    @ManyToOne
    @JoinColumn(name = "inventory_id", nullable = false)
    @NotNull
    private Inventory inventory;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    @NotNull
    private Customer customer;

    public Rental() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Timestamp getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Timestamp returnDate) {
        this.returnDate = returnDate;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "Rental{" +
                "id=" + id +
                ", rentalDate=" + rentalDate +
                ", returnDate=" + returnDate +
                ", staff=" + staff.getId() +
                ", inventory=" + inventory.getId() +
                ", customer=" + customer.getId() +
                '}';
    }
}
