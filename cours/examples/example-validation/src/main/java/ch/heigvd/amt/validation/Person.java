package ch.heigvd.amt.validation;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class Person {

    @NotBlank(message = "First name cannot be blank")
    public String firstName;

    @NotBlank(message = "Last name cannot be blank")
    public String lastName;

    @Min(value = 1900, message = "Year of birth must be greater than 1900")
    @Max(value = 2021, message = "Year of birth must be less than 2021")
    public int yearOfBirth;

    @NotBlank(message = "Email cannot be blank")
    @Pattern(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$", message = "Email is not valid")
    public String email;

}
