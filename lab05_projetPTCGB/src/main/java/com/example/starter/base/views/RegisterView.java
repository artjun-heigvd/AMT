package com.example.starter.base.views;

import com.example.starter.base.MenuBar;
import com.example.starter.base.service.UserService;
import com.example.starter.base.utilities.Constants;
import com.nulabinc.zxcvbn.Strength;
import com.nulabinc.zxcvbn.Zxcvbn;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;

import java.util.Arrays;

/**
 * The RegisterView class represents the registration view for the application.
 * It provides a form for users to register by entering their username, email, and password.
 *
 * @author Edwin Haeffner
 * @author Arthur Junod
 * @author Eva Ray
 * @author Rachel Tranchida
 */
@Route(Constants.ROUTE_REGISTER)
public class RegisterView extends VerticalLayout {

    @Inject
    UserService userService;

    private TextField username;
    private EmailField email;
    private PasswordField password;
    private PasswordField confirmPassword;
    private Strength strength;

    public RegisterView() {
        setSizeFull();
        setPadding(false);
        setSpacing(false);
    }

    /**
     * Enum representing the strength of the password.
     */
    private enum PasswordStrength {
        WEAK("Weak", "#D15252"),
        MEDIUM("Medium", "#D0AC52"),
        STRONG("Strong", "#8BCB38"),
        VERY_STRONG("Very Strong", "#59CC39");

        private final String label;
        private final String color;

        PasswordStrength(String label, String color) {
            this.label = label;
            this.color = color;
        }

        public String getLabel() {
            return label;
        }

        public String getColor() {
            return color;
        }
    }

    /**
     * Initializes the registration form and its components.
     * This method is called after the constructor.
     */
    @PostConstruct
    private void init() {
        MenuBar menuBar = new MenuBar();
        FormLayout formLayout = new FormLayout();

        // Username, email, password, and confirm password fields
        username = new TextField("Username");
        username.setPlaceholder("Enter your username");
        username.setMaxLength(25);
        username.setMinLength(1);

        email = new EmailField("Email");
        email.setPlaceholder("Enter your email");
        email.setErrorMessage("Please enter a valid email address");

        password = new PasswordField("Password");
        password.setPlaceholder("Enter your password");

        Span strengthLabel = new Span("Password strength: ");
        Span strengthValue = new Span();
        Div strengthIndicator = new Div(strengthLabel, strengthValue);

        // Zxcvbn password strength meter
        Zxcvbn zxcvbn = new Zxcvbn();

        password.setValueChangeMode(ValueChangeMode.EAGER); //So it updates on every key press
        password.setMaxLength(100);

        // Update the password strength indicator when the password field changes
        password.addValueChangeListener(event -> {
            String value = password.getValue();
            if (value == null || value.isEmpty()) {
                strengthValue.setText("");
                return;
            }

            var userFields = Arrays.asList(username.getValue(), email.getValue());

            strength = zxcvbn.measure(value, userFields);

            var strengthScore = strength.getScore();

            PasswordStrength passwordStrength = PasswordStrength.values()[(Math.max(strengthScore, 1)) - 1];

            strengthValue.setText(passwordStrength.getLabel());
            strengthValue.getStyle().set("color", passwordStrength.getColor());
        });


        password.setHelperComponent(strengthIndicator);

        confirmPassword = new PasswordField("Confirm Password");
        confirmPassword.setPlaceholder("Confirm your password");

        Button registerButton = new Button("Register");
        registerButton.addClickListener(event -> registerUser());
        registerButton.getStyle()
                .setBackgroundColor("#4CAF50")
                .setColor("#fff");

        formLayout.setColspan(username, 1);
        formLayout.setWidth("40%");
        formLayout.getStyle().setMargin("0 auto");
        formLayout.setHeight("50%");
        formLayout.add(username, email, password, confirmPassword, registerButton);

        var centeredLayout = new VerticalLayout(formLayout);
        centeredLayout.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        centeredLayout.setJustifyContentMode(JustifyContentMode.CENTER);
        centeredLayout.setWidth("100%");
        centeredLayout.setHeight("100%");

        add(menuBar, centeredLayout);
        expand(centeredLayout);
    }

    /**
     * Registers the user by validating the input fields and creating a new user.
     * Displays notifications for any errors or successful registration.
     */
    private void registerUser() {
        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Notification.show("Please fill in all fields", 3000, Notification.Position.MIDDLE);
            return;
        }

        if (!password.getValue().equals(confirmPassword.getValue())) {
            Notification.show("Passwords do not match", 3000, Notification.Position.MIDDLE);
            return;
        }

        if (strength.getScore() < 3) {
            Notification.show("Password is too weak " + strength.getScore(), 3000, Notification.Position.MIDDLE);
            return;
        }

        if (userService.getByUsername(username.getValue()) != null) {
            Notification.show("An error has occurred", 3000, Notification.Position.MIDDLE);
            return;
        }

        try {

            String hashedPassword = hashPassword(password.getValue());

            // Create the user with the hashed password
            userService.createUser(username.getValue(), hashedPassword, email.getValue());

            Notification.show("Registration successful! Please log in.",
                    3000, Notification.Position.MIDDLE);

            // Redirect to login page after successful registration after a delay
            UI.getCurrent().getPage().executeJs(
                    "setTimeout(() => {" +
                            "window.location.href = '/login';" +
                            "}, 500);"
            );

        } catch (Exception e) {
            Notification.show("Registration failed: " + e.getMessage(),
                    3000, Notification.Position.MIDDLE);
        }
    }

    /**
     * Hashes the given password using Bcrypt.
     * @param password the password to hash.
     * @return the hashed password.
     */
    private String hashPassword(String password) {
        return BcryptUtil.bcryptHash(password);
    }
}
