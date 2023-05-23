package com.test.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@AllArgsConstructor
@Data
@Accessors(chain = true)
public class UpdatePasswordDto implements Serializable {
    @Size(min = 6, message = "password must be greater than 6 and less than 50")
    @NotEmpty(message = "Please enter valid password.")
    private final String currentPassword;
    @Size(min = 6, message = "password must be greater than 6 and less than 50")
    @NotEmpty(message = "Please enter valid password.")
    private final String newPassword;

}
