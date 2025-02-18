package ch.heigvd.amt.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;

@Entity(name = "status")
public class Status {
    @Id
    int id;

    Probe probe;

    boolean isUp ;

    
}
