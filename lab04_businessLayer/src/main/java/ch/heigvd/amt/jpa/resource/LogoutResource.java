package ch.heigvd.amt.jpa.resource;

import io.quarkus.security.Authenticated;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.NewCookie;
import jakarta.ws.rs.core.Response;
import java.net.URI;

import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 * This class is a JAX-RS resource that provides operations to log out a user.
 * Authors: Rachel Tranchida, Edwin Häffner, Arthur Junod, Eva Ray
 */
@Path("/logout")
@Authenticated
public class LogoutResource {

    @ConfigProperty(name = "quarkus.http.auth.form.cookie-name")
    String cookieName;
    @ConfigProperty(name = "quarkus.http.auth.form.login-page")
    String loginPage;

    /**
     * Logs out the user by deleting the cookie.
     * @return a response that redirects to the login page
     */
    @GET
    public Response logout() {
        var cookie = new NewCookie.Builder(cookieName)
                .maxAge(0)
                .path("/")
                .httpOnly(true)
                .secure(true)
                .build();
        return Response.seeOther(URI.create(loginPage))
                .cookie(cookie)
                .build();
    }
}