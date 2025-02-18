package ch.heigvd.amt.services;

import io.quarkus.test.junit.QuarkusTestProfile;
import org.apache.groovy.util.Maps;

import java.util.Map;

public class ProbeServiceProfile implements QuarkusTestProfile {

    public ProbeServiceProfile() {
        super();
    }

    @Override
    public Map<String, String> getConfigOverrides() {
        return Maps.of(
                "quarkus.amqp.devservices.enabled","false",
                "quarkus.arc.exclude-types", "ch.heigvd.amt.messaging.*");
    }

}