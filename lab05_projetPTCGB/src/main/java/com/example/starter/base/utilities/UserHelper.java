package com.example.starter.base.utilities;

import com.example.starter.base.dto.UserDTO;
import com.vaadin.flow.server.VaadinSession;

/**
 * UserHelper is a utility class that provides methods to manage the current user.
 *
 * @author Edwin Haeffner
 * @author Arthur Junod
 * @author Eva Ray
 * @author Rachel Tranchida
 */
public class UserHelper {

    public static void setCurrentId(int id) {
        VaadinSession.getCurrent().setAttribute("userId", id);
    }

    public static void setCurrentUsername(String username) {
        VaadinSession.getCurrent().setAttribute("user", username);
    }

    /**
     * Method to get the current user id.
     *
     * @return Integer object.
     */
    public static int getCurrentId() {

        Integer userId = (Integer) VaadinSession.getCurrent().getAttribute("userId");

        if (userId == null) {
            return -1;
        }

        return userId;
    }

    public static String getCurrentUsername() {
        return (String) VaadinSession.getCurrent().getAttribute("user");
    }

    public static void setCurrentUser(UserDTO user) {
        setCurrentId(user.id());
        setCurrentUsername(user.username());
        VaadinSession.getCurrent().setAttribute("userDTO", user);
    }

    public static UserDTO getCurrentUser() {
        return (UserDTO) VaadinSession.getCurrent().getAttribute("userDTO");
    }

    public static boolean isLoggedIn() {
        return getCurrentId() != -1;
    }

    public static void logout(){
        VaadinSession.getCurrent().setAttribute("userId", -1);
        VaadinSession.getCurrent().setAttribute("user", null);
        VaadinSession.getCurrent().setAttribute("userDTO", null);
    }
}
