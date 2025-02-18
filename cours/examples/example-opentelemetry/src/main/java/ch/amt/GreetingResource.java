package ch.amt;

import io.opentelemetry.api.metrics.LongCounter;
import io.opentelemetry.api.metrics.Meter;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.jboss.logging.Logger;
import org.jboss.logmanager.MDC;
import org.jboss.resteasy.reactive.RestPath;

@Path("/hello")
public class GreetingResource {

    private static final Logger LOG = Logger.getLogger(GreetingResource.class);

    @Inject
    PersonService personService;

    private final LongCounter counter;
    public GreetingResource(Meter meter) {
        counter = meter.counterBuilder("amt-count")
                .setDescription("amt-count")
                .setUnit("invocations")
                .build();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        counter.add(2);
        MDC.put("amt","hello");
        LOG.info("hello");
        return "Hello from Quarkus REST -- AMT OBS";
    }

    @GET
    @Path("person/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getPerson(@RestPath Long id) {
        return personService.getPerson(id);
    }

    @POST
    @Path("person")
    @Produces(MediaType.TEXT_PLAIN)
    public String createRandom() {
        var p = personService.createRandom();
        return p.toString();
    }
}
