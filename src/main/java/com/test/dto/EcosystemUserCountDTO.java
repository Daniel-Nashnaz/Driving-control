package com.test.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class EcosystemUserCountDTO {

        private String ecologicalType;
        private int count;
        private int userId;
        private String fullName;


}
