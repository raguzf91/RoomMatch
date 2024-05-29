package com.raguzf.roommatch.domain;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Tag {

    private Integer id;
    @NotEmpty(message = "NAME CANNOT BE EMPTY")
    private String name;
}