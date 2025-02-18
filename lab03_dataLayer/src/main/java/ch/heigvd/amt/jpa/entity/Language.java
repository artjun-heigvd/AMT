package ch.heigvd.amt.jpa.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

/**
 * This class represents a language in the database.
 * The class contains the following attributes:
 * - id: The unique identifier for the language.
 * - name: The name of the language.
 *
 * @author Arthur Junod
 * @author Edwin Häffner
 * @author Eva Ray
 * @author Rachel Tranchida
 */

@Entity(name = "language")
@Table(name = "language")
@NamedQueries({
        @NamedQuery(name = "Language.findByName", query = "SELECT l FROM language l WHERE TRIM(LOWER(l.name)) LIKE LOWER(:name)")
})
public class Language {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "language_id")
    private Integer id;


    @Column(name = "name", length = 20)
    @NotNull
    private String name;

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

    public Language() {

    }

    @Override
    public String toString() {
        return "Language{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

}
