package ch.heigvd.amt.jpa.entity;

import ch.heigvd.amt.jpa.utilities.CustomPasswordProvider;
import io.quarkus.security.jpa.*;

import jakarta.persistence.*;

import java.util.Set;

//Changement pour passer le test
@Entity(name = "Staff")
@UserDefinition
public class Staff {

    @Username
    public String username;

    @Password(value = PasswordType.CUSTOM, provider = CustomPasswordProvider.class)
    String password;

    @Roles
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "staff_role",
            joinColumns = @JoinColumn(name = "users_staff_id"),
            inverseJoinColumns = @JoinColumn(name = "roles_id")
    )
    public Set<Role> roles;

    @Id
    @Column(name = "staff_id")
    private Integer id;

    @Column(name = "first_name", length = 45)
    private String firstName;

    @Column(name = "last_name", length = 45)
    private String lastName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", referencedColumnName = "address_id")
    private Address address;

    @Column(name = "picture")
    private byte[] picture;

    @Column(name = "email", length = 50)
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "store_id", referencedColumnName = "store_id")
    private Store store;

    @Column(name = "active")
    private boolean active;

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Staff() {

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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Staff{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", picture=" + picture +
                ", email='" + email + '\'' +
                ", active=" + active +
                '}';
    }
}