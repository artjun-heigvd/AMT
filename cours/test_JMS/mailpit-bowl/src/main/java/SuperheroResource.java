import io.quarkus.mailer.Mail;
import io.quarkus.mailer.Mailer;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

import java.util.List;

@Path("/superheroes")
@ApplicationScoped
public class SuperheroResource {
    @Inject
    Mailer mailer;

    @GET
    public String villainAlert() {
        Mail m = new Mail();
        m.setFrom("admin@hallofjustice.net");
        m.setTo(List.of("superheroes@quarkus.io"));
        m.setSubject("WARNING: Super Villain Alert");
        m.setText("Lex Luthor has been seen in Gotham City!");
        mailer.send(m);

        return "Superheroes alerted!";
    }
}