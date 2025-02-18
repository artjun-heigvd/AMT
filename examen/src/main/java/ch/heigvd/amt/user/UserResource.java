package ch.heigvd.amt.user;


import io.quarkus.qute.Location;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.quarkus.security.Authenticated;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.time.Instant;
import java.util.Date;

@Path(UserResource.path)
public class UserResource {
    static final String path = "/user";

    @Location("user/login")
    Template login;

    @Location("user/error")
    Template error;

    @Location("user/user")
    Template user;

    @Location("user/register")
    Template register;

    @Location("user/register_success")
    Template registerSuccess;

    @Location("user/register_failed")
    Template registerFailed;

    @ConfigProperty(name = "quarkus.http.auth.form.cookie-name")
    String cookieName;

    @Inject
    UserService userService;

    @GET
    @Path("logout")
    public Response logout() {
        final NewCookie removeCookie = new NewCookie.Builder(cookieName)
                .maxAge(0)
                .expiry(Date.from(Instant.EPOCH))
                .path("/")
                .build();
        return Response
                .seeOther(UriBuilder.fromUri("/wiki").build())
                .cookie(removeCookie)
                .build();
    }

    @GET
    @Authenticated
    public TemplateInstance user(@Context SecurityContext sc) {
        return user.data("username", sc.getUserPrincipal().getName());
    }

    @GET
    @Path("error")
    public TemplateInstance error() {
        return error.instance();
    }

    @GET
    @Path("login")
    public TemplateInstance login() {
        return login.instance();
    }

    /**
     * GET sur user/register
     * @return l'instance register
     */
    @GET
    @Path("/register")
    public TemplateInstance getregisterUser() {
        return register.instance();
    }

    /**
     * POST sur user/register
     * @param username username du form
     * @param password password du form
     * @return La bonne page suivant l'exécution
     */
    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response registerUser(@FormParam("username") String username,
                                 @FormParam("password") String password) {
        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            return Response.seeOther(UriBuilder.fromPath("/user/register_failed")
                    .queryParam("error", "All fields are required.").build()).build();
        }

        try {
            userService.registerUser(username, password);
            return Response.seeOther(UriBuilder.fromPath("/user/register_success").build()).build();
        } catch (Exception e) {
            return Response.seeOther(UriBuilder.fromPath("/user/register_failed")
                    .queryParam("error", "Registration failed: " + e.getMessage()).build()).build();
        }
    }

    @GET
    @Path("/register_success")
    public TemplateInstance registerSuccess() {
        return registerSuccess.instance();
    }

    @GET
    @Path("/register_failed")
    public TemplateInstance registerFailed(@QueryParam("error") String error) {
        return registerFailed.data("error", error);
    }

}
