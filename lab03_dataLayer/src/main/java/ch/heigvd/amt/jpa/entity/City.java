package ch.heigvd.amt.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.validation.constraints.NotNull;

/**
 * This class represents a City entity in the Sakila database
 * It is used to store the city of an address and link it to a country
 * It has the following fields :
 * - id : the id of the city
 * - cityName : the name of the city
 * - country : the country where the city is located
 *
 * @author Junod Arthur
 * @author Tranchida Rachel
 * @author Häffner Edwin
 * @author Ray Eva
 */
@Entity(name = "city")
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "city_id")
    @NotNull
    private Integer id;

    @Column(name = "city", length = 50, nullable = false)
    @NotNull
    private String cityName;

    @ManyToOne
    @JoinColumn(name = "country_id", nullable = false)
    @NotNull
    private Country country;

    public City() {
    }

    public Integer getId() {
        return id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", city='" + cityName + '\'' +
                ", country_id='" + country + '\'' +
                '}';
    }

}
