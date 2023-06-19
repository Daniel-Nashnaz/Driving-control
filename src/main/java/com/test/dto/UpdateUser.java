package com.test.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class UpdateUser implements Serializable {

    @Size(min = 2, max = 100, message = "fullName must be greater than 2 and less than 100")
    private final String fullName;

    @Size(min = 3, max = 50, message = "userName must be greater than 3 and less than 50")
    private final String userName;

    @Size(min = 6, max = 50, message = "email must be greater than 6 and less than 50")
    @Email
    private final String email;

    @Size(min = 5, max = 20, message = "phone must be greater than 5 and less than 20")
    private final String phone;

    //@Size(min = 6, message = "password must be greater than 6 and less than 50")
    private final String password;


}