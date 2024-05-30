package com.raguzf.roommatch.model;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Tags")
public class Tag {

    private Integer id;
    @NotEmpty(message = "NAME CANNOT BE EMPTY")
    private String name;

    @ManyToMany(mappedBy = "tags")
    private Set<User> profiles;
}