package com.test.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class DriversDto {


    @NotNull
    private String fullName;
    @NotNull
    private String userName;
    @NotNull
    private String email;
    @NotNull
    private String phone;


}
