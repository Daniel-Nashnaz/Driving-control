package com.test.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class UsersOfAdminDto implements Serializable {
    @NotNull
    private Integer id;
    @NotNull
    private Integer tripId;
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

    @Size(max = 20)
    @NotNull
    private Instant timeStart;

}
