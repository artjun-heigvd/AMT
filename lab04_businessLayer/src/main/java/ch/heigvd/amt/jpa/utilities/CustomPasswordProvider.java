package ch.heigvd.amt.jpa.utilities;

import jakarta.xml.bind.DatatypeConverter;

import org.wildfly.security.password.Password;
import org.wildfly.security.password.interfaces.SimpleDigestPassword;

import io.quarkus.security.jpa.PasswordProvider;

/**
 * This class is a custom password provider that provides a password for a given passwordInDatabase.
 * Authors: Rachel Tranchida, Edwin Häffner, Arthur Junod, Eva Ray
 */
public class CustomPasswordProvider implements PasswordProvider {

    /**
     * Get the password for a given passwordInDatabase.
     * @param passwordInDatabase the passwordInDatabase
     * @return the password
     */
    @Override
    public Password getPassword(String passwordInDatabase) {
        byte[] digest = DatatypeConverter.parseHexBinary(passwordInDatabase);

        // Let the security runtime know that this passwordInDatabase is hashed by using the SHA1 hashing algorithm
        return SimpleDigestPassword.createRaw(SimpleDigestPassword.ALGORITHM_SIMPLE_DIGEST_SHA_1, digest);
    }
}