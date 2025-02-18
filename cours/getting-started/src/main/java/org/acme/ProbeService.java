package org.acme;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.acme.entities.Probe;
import org.acme.entities.Status;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

@ApplicationScoped
public class ProbeService {

    @Inject
    EntityManager em;

    @Transactional
    public Probe createProbeIfNotExists(Probe probe) {
        em.persist(probe);
        return probe;
    }

    @Transactional
    public List<Probe> listProbes() {
        return em.createQuery("select p from Probe p", Probe.class).getResultList();
    }

    public List<Status> getStatusList(String url, int count) {return null;}

    public Status getLastStatus(String url) {return null;}

    public Status executeProbe(Probe probe) {return null;}


    public Status computeStatus(Probe probe) {
        var status = new Status();
        status.setProbe(probe);
        var start = Instant.now();
        status.setTimestamp(start);

        try {

            HttpClient httpClient = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(probe.getUrl()))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            status.setUp(response.statusCode() == 200);

        }catch(Exception e) {
            e.printStackTrace();
        }

        status.setUp(false);

        var stop = Instant.now();
        var duration = Duration.between(start, stop);
        status.setDuration(duration);
        return status;
    }
}
