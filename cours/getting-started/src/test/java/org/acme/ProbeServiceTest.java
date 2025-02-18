package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.acme.entities.Probe;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@QuarkusTest
public class ProbeServiceTest {
    @Inject
    ProbeService probeService;

    @Test
    void probeCreationProbe(){
        var probe = new Probe("http://example.com");
        probeService.createProbeIfNotExists(probe);

        var probes = probeService.listProbes();
        assertEquals(1, probes.size());
    }

    @Test
    void testComputeStatus(){
        var probe = new Probe("http://example.com");
        probeService.createProbeIfNotExists(probe);

        var status = probeService.computeStatus(probe);
        assertTrue(status.isUp());
    }
}
