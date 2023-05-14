package com.test.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.Instant;

/**
 * A DTO for the {@link com.test.entity.Message} entity
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class MessageDto implements Serializable {
    private Integer id;

    private Integer userId;
    @Size(max = 100)
    @NotNull
    private String subject;
    @NotNull
    private String body;
    @NotNull
    private Instant sentTime;
}