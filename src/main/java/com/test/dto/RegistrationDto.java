package com.test.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class RegistrationDto implements Serializable {

    @Size(min = 2, max = 100, message = "fullName must be greater than 2 and less than 100")
    @NotNull
    private final String fullName;
    @Size(min = 3, max = 50, message = "userName must be greater than 3 and less than 50")
    @NotNull
    private final String userName;
    @Size(min = 6, max = 50, message = "email must be greater than 6 and less than 50")
    @NotEmpty(message = "Please enter valid email.")
    @Email
    private final String email;

    @Size(min = 5, max = 20, message = "phone must be greater than 5 and less than 20")
    @NotNull
    private final String phone;
    @Size(min = 2, max = 100, message = "address must be greater than 2 and less than 100")
    @NotNull
    private final String address;
    @Size(min = 1, max = 10, message = "apartment number must be greater than 1 and less than 10")
    @NotNull
    private final String apartmentNumber;
    @Size(min = 2, max = 50, message = "city must be greater than 2 and less than 50")
    @NotNull
    private final String city;
    @Size(min = 2, max = 50, message = "country number must be greater than 2 and less than 50")
    @NotNull
    private final String country;

    @Size(min = 6, message = "password must be greater than 6 and less than 50")
    @NotEmpty(message = "Please enter valid password.")
    private final String password;


}
