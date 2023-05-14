package com.test.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * A DTO for the {@link com.test.entity.Driver} entity
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class AddDriverDto implements Serializable {
    @NotNull
    private String vehicleNumber;
    @NotNull
    private String userNameOrEmail;

}