package ch.heigvd.amt.jpa.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

/**
 * This class represents an Address entity in the Sakila database
 * It is used to store the address of a customer or a staff member
 * It has the following fields :
 * - id: the id of the address
 * - address: the first line of the address
 * - address2: the second line of the address
 * - district: the district
 * - city: the city where the address is located
 * - postalCode: the postal code of the address
 * - phone: a phone number linked to the person living at this address
 *
 * @author Junod Arthur
 * @author Tranchida Rachel
 * @author Häffner Edwin
 * @author Ray Eva
 */
@Entity(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Integer id;

    @Column(name = "address", length = 50, nullable = false)
    @NotNull
    private String address;

    @Column(name = "address2", length = 50)
    private String address2;

    @Column(name = "district", length = 20, nullable = false)
    @NotNull
    private String district;

    @ManyToOne
    @JoinColumn(name = "city_id", nullable = false)
    @NotNull
    private City city;

    @Column(name = "postal_code", length = 10)
    private String postalCode;

    @Column(name = "phone", length = 20, nullable = false)
    @NotNull
    private String phone;

    public Address() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String toString() {
        return "Address{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", address2='" + address2 + '\'' +
                ", district='" + district + '\'' +
                ", city=" + city +
                ", postalCode='" + postalCode + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

}