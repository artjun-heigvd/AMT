package ch.heigvd.amt.transaction;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class PostgresEntity {

    @Id
    private int id;

    public PostgresEntity() {
        // Required by JPA
    }

    public PostgresEntity(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
