package ch.heigvd.amt.jpa.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;


import java.math.BigDecimal;
import java.time.Instant;

/**
 * This class represents a payment in the database.
 * The class contains the following attributes:
 * - id: The unique identifier for the payment.
 * - customer: The customer who made the payment.
 * - staff: The staff member who processed the payment.
 * - rental: The rental for which the payment was made.
 * - amount: The amount of the payment.
 * - paymentDate: The date and time the payment was made.
 *
 * @author Arthur Junod
 * @author Edwin Häffner
 * @author Eva Ray
 * @author Rachel Tranchida
 */
@Entity(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_id")
    private Integer id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "staff_id", nullable = false)
    private Staff staff;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "rental_id", nullable = false)
    private Rental rental;

    @NotNull
    @Column(name = "amount", nullable = false, precision = 5, scale = 2)
    private BigDecimal amount;

    @NotNull
    @Column(name = "payment_date", nullable = false)
    private Instant paymentDate;

    public Payment() {

    }

    public Integer getId() {
        return id;
    }

    public Instant getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Instant paymentDate) {
        this.paymentDate = paymentDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Rental getRental() {
        return rental;
    }

    public void setRental(Rental rental) {
        this.rental = rental;
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String toString() {
        return "Payment{" +
                "id=" + id +
                ", customer='" + customer.getId() + '\'' +
                ", staff='" + staff.getId() + '\'' +
                ", rental='" + rental.getId() + '\'' +
                ", amount='" + amount + '\'' +
                ", paymentDate='" + paymentDate + '\'' +
                '}';
    }
}
