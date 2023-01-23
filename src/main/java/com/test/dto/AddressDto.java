package com.test.dto;

import com.test.dto.UsersDto;
import com.test.entity.Address;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * A DTO for the {@link Address} entity
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class AddressDto implements Serializable {
    private Integer id;
    private UsersDto userID;
    @Size(max = 100)
    @NotNull
    private String address;
    @Size(max = 10)
    @NotNull
    private String apartmentNumber;
    @Size(max = 50)
    @NotNull
    private String city;
    @Size(max = 50)
    @NotNull
    private String country;
}