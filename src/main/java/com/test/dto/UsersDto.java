package com.test.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * A DTO for the {@link com.test.entity.Users} entity
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class UsersDto implements Serializable {
    private Integer id;
    @Size(max = 100)
    @NotNull
    private String fullName;
    @Size(max = 50)
    @NotNull
    private String userName;
    @Size(max = 50)
    @NotNull
    private String email;
    @Size(max = 20)
    @NotNull
    private String phone;
    @NotNull
    private Boolean isDeleted = false;
    private Set<UserPasswordDto> userPasswords = new LinkedHashSet<>();
    private List<AddressDto> addresses = new ArrayList<>();
    private Set<VehicleDto> vehicles = new LinkedHashSet<>();
    private Set<AddDriverDto> drivers = new LinkedHashSet<>();
}
