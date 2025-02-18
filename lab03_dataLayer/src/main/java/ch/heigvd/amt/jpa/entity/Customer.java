package ch.heigvd.amt.jpa.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;

/**
 * This class represents a Customer entity in the Sakila database
 * It is used to store the information of a customer
 * It has the following fields :
 * - id: the id of the customer
 * - firstName: their first name
 * - lastName: their last name
 * - email: their email address
 * - activeBool: a boolean value to know if the customer is active or not
 * - active: an integer value to know if the customer is active or not
 * - createDate: the date when the customer was created
 * - store: the store where the customer is registered
 * - address: the address of the customer
 *
 * @author Junod Arthur
 * @author Tranchida Rachel
 * @author Häffner Edwin
 * @author Ray Eva
 */
@Entity(name = "customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Integer id;

    @NotNull
    @Column(name = "first_name", nullable = false, length = 45)
    private String firstName;

    @NotNull
    @Column(name = "last_name", nullable = false, length = 45)
    private String lastName;

    @Column(name = "email", length = 50)
    private String email;

    @NotNull
    @ColumnDefault("true")
    @Column(name = "activebool", nullable = false)
    private Boolean activeBool = true;

    @Column(name = "active")
    private Integer active;

    @NotNull
    @Column(name = "create_date", nullable = false)
    private LocalDate createDate = LocalDate.now();

    @NotNull
    @ManyToOne
    @JoinColumn(name = "store_id", nullable = false)
    private Store store;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    public Customer() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getActiveBool() {
        return activeBool;
    }

    public void setActiveBool(Boolean activeBool) {
        this.activeBool = activeBool;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public LocalDate getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDate createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", store='" + store.getId() + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", address='" + address.getId() + '\'' +
                ", email='" + email + '\'' +
                ", activeBool='" + activeBool + '\'' +
                ", createDate='" + createDate + '\'' +
                ", active='" + active + '\'' +
                '}';
    }
}
