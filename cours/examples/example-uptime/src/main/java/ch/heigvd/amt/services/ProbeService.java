package ch.heigvd.amt.services;

import ch.heigvd.amt.entities.Probe;
import ch.heigvd.amt.entities.Status;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.ws.rs.core.HttpHeaders;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ApplicationScoped
public class ProbeService {

    @Inject
    EntityManager entityManager;

    @Transactional
    public List<Probe> listProbes() {
        return entityManager.createQuery("SELECT p FROM Probe p", Probe.class).getResultList();
    }

    @Transactional
    public Probe createProbeIfNotExists(@Valid Probe probe) {
        var existingProbe = entityManager.find(Probe.class, probe.getUrl());
        if (existingProbe == null) {
            entityManager.persist(probe);
            return probe;
        } else {
            return existingProbe;
        }
    }

    @Transactional
    public Status getLastStatus(String url) {
        try {
            return entityManager.createQuery("SELECT s FROM Status s WHERE s.probe.url = :url ORDER BY s.timestamp DESC", Status.class)
                    .setParameter("url", url)
                    .setMaxResults(1)
                    .getSingleResult();
        } catch (Exception e) {
            return new Status(null, new Probe(url), Instant.EPOCH, false, -1);
        }
    }

    @Transactional
    public List<Status> getStatusList(String url, int count) {
        var status = new ArrayList<>(entityManager.createQuery("SELECT s FROM Status s WHERE s.probe.url = :url ORDER BY s.timestamp DESC", Status.class)
                .setParameter("url", url)
                .setMaxResults(count)
                .getResultList());
        var n = count - status.size();
        for (int i = 0; i < n; i++) {
            status.add(new Status(null, new Probe(url), Instant.EPOCH, false, -1));
        }
        Collections.reverse(status);
        return status;
    }

    @Transactional
    public Status executeProbe(Probe probe) {
        var status = computeStatus(probe);
        entityManager.persist(status);
        return status;
    }

    public Status computeStatus(Probe probe) {
        var status = new Status();
        status.setProbe(probe);
        var start = Instant.now();
        status.setTimestamp(start);
        try(HttpClient client = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .connectTimeout(Duration.ofSeconds(1)).build()) {
            var request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(probe.getUrl()))
                    .header(HttpHeaders.CACHE_CONTROL, "no-cache, no-store, must-revalidate")
                    .header(HttpHeaders.EXPIRES, "0")
                    .timeout(Duration.ofSeconds(5))
                    .build();

            var responseCode = client.send(request, HttpResponse.BodyHandlers.discarding()).statusCode();
            status.setUp(responseCode >= 200 && responseCode < 300);
        } catch (IOException|InterruptedException e) {
            status.setUp(false);
        } finally {
            status.setDuration((int)(Duration.between(start, Instant.now()).toMillis()));
        }
        return status;
    }
}
