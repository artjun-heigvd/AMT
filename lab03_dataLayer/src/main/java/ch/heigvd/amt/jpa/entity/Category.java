package ch.heigvd.amt.jpa.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.Set;

/**
 * This class represents an Category entity in the Sakila database
 * It is used to store the category of a movie
 * It has the following fields :
 * - id: the id of the category
 * - name: the name of the category
 * - films: the films that are in this category
 *
 * @author Junod Arthur
 * @author Tranchida Rachel
 * @author Häffner Edwin
 * @author Ray Eva
 */
@Entity(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Integer id;

    @Column(name = "name", length = 25, nullable = false)
    @NotNull
    private String name;

    @ManyToMany(mappedBy = "categories")
    private Set<Film> films;

    public Category() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
