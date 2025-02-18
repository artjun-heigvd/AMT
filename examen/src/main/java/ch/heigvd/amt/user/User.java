package ch.heigvd.amt.user;

import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.Roles;
import io.quarkus.security.jpa.UserDefinition;
import io.quarkus.security.jpa.Username;
import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * An entity representing a user.
 */
@UserDefinition
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Username
    @Column(unique = true)
    private String username;

    @Password
    private String password;

    @Roles
    private String role = "";

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    /**
     * Default constructor.
     */
    public User() {

    }

    /**
     * Returns the username of the user.
     *
     * @return the username of the user
     */
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) { this.username = username; }

    public String getPassword() {   return password; }

    public void setPassword(String password) { this.password = password; }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }
}
