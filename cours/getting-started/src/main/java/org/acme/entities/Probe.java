package org.acme.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Probe {

    @Id
    String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Probe() {}

    public Probe(String url) {
        this.url = url;
    }
}
