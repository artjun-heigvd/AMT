package ch.heigvd.amt.resources;

import ch.heigvd.amt.services.SynchronizationService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/synchronize")
public class SynchronizationResource {

    @Inject
    SynchronizationService synchronizationService;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        synchronizationService.synchronize();
        return "Done!";
    }

}
