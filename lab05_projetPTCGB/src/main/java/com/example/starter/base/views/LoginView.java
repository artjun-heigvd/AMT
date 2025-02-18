package com.example.starter.base.views;

import com.example.starter.base.MenuBar;
import com.example.starter.base.dto.UserDTO;
import com.example.starter.base.service.UserService;
import com.example.starter.base.utilities.Constants;
import com.example.starter.base.utilities.UserHelper;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.dom.Style;
import com.vaadin.flow.router.Route;
import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;

/**
 * View for the login page
 *
 * @author Edwin Haeffner
 * @author Arthur Junod
 * @author Eva Ray
 * @author Rachel Tranchida
 */
@Route(Constants.ROUTE_LOGIN)
public class LoginView extends VerticalLayout {

    /**
     * The user service
     */
    @Inject
    UserService userService;


    /**
     * The username field
     */
    private TextField username;

    /**
     * The password field
     */
    private PasswordField password;

    /**
     * Constructor for the LoginView.
     */
    public LoginView() {
        setSizeFull();
        setPadding(false);
        setSpacing(false);
    }

    /**
     * Initializes the view.
     */
    @PostConstruct
    private void init() {

        // Create menu bar
        MenuBar menuBar = new MenuBar();

        // Create login form
        FormLayout formLayout = new FormLayout();

        username = new TextField("Username");
        username.setPlaceholder("Enter your username");
        username.setId("j_username");
        username.getElement().setAttribute("name", "j_username");

        password = new PasswordField("Password");
        password.setPlaceholder("Enter your password");
        password.setId("j_password");
        password.getElement().setAttribute("name", "j_password");

        Button loginButton = new Button("Login");
        loginButton.addClickListener(event -> {
            submitSecurityCheck(username.getValue(), password.getValue());
        });
        loginButton.getStyle()
                .setBackgroundColor("#4CAF50")
                .setColor( "#fff");
        Div notRegisteredYet = new Div("Not registered yet?");
        notRegisteredYet.getStyle().setTextAlign(Style.TextAlign.CENTER);
        notRegisteredYet.getStyle().setMarginTop("10px");

        Button registerButton = new Button("Register");
        registerButton.addClickListener(buttonClickEvent -> getUI().ifPresent(ui -> ui.navigate("register")));

        formLayout.add(username, password, loginButton, notRegisteredYet, registerButton);
        formLayout.setResponsiveSteps(new FormLayout.ResponsiveStep("0", 1));
        formLayout.setColspan(username, 1);
        formLayout.setWidth("40%");
        formLayout.getStyle().setMargin("0 auto");
        formLayout.setHeight("50%");

        // Center the form layout
        VerticalLayout centeredLayout = new VerticalLayout(formLayout);
        centeredLayout.setDefaultHorizontalComponentAlignment(Alignment.CENTER);
        centeredLayout.setJustifyContentMode(JustifyContentMode.CENTER);

        // Assemble the layout
        add(menuBar, centeredLayout);
        expand(centeredLayout);
    }


    /**
     * Submit the security check
     * @param username the username
     * @param password the password
     */
    private void submitSecurityCheck(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            Notification.show("Please fill in all fields", 3000, Notification.Position.MIDDLE);
            return;
        }

        try {
            UserDTO user = userService.getByUsername(username);

            if (user != null && verifyPassword(password, user.password())) {
                // Login successful
                UserHelper.setCurrentUser(user);

                // Navigate to home page
                UI.getCurrent().navigate(Constants.ROUTE_HOME);

                // Show success notification
                Notification.show("Welcome " + UserHelper.getCurrentUsername() + "!");
            } else { // Login failed
                //Hash the password to prevent timing attacks
                BcryptUtil.matches(password, "dummy_hash");
                Notification.show("Invalid credentials", 3000, Notification.Position.MIDDLE);
            }
        } catch (Exception e) {
            Notification.show("Invalid credentials", 3000, Notification.Position.MIDDLE);
        }
    }

    /**
     * Verify the password
     * @param plainPassword the plain password
     * @param hashedPassword the hashed password
     * @return true if the password is verified, false otherwise
     */
    private boolean verifyPassword(String plainPassword, String hashedPassword) {
        return BcryptUtil.matches(plainPassword, hashedPassword);
    }

}