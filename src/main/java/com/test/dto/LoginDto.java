package com.test.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginDto {
    @Size(min = 3,max = 50)
    @NotNull
    private String userNameOrEmail;
    @Size(min = 6,message = "most bigger them 6")
    private String password;

}

