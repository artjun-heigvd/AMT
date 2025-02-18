package ch.heigvd.amt.user;

import io.quarkus.vertx.http.runtime.security.FormAuthenticationEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.ObservesAsync;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;


@ApplicationScoped
public class LastLoginRecorder {

    @Inject
    UserService userService;

    /**
     * Method observing the authentication event based of Quarkus Security Forms Authentication
     * https://quarkus.io/guides/security-customization#observe-security-events
     * @param event
     */
    void observeAuthenticationSuccess(@ObservesAsync FormAuthenticationEvent event) {
        userService.updateLastLogin(event.getSecurityIdentity().getPrincipal().getName());
    }
}
