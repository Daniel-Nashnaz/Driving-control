package com.test.dto;

import com.test.dto.UsersDto;
import com.test.entity.UserPassword;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * A DTO for the {@link UserPassword} entity
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class UserPasswordDto implements Serializable {
    private Integer id;
    @NotNull
    private UsersDto userID;
    @Size(min = 6)
    @NotNull
    private String password;
    @NotNull
    private Boolean isActive = false;
}