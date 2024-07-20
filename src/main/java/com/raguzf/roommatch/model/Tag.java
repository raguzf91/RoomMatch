package com.raguzf.roommatch.model;

import java.util.Set;

import com.raguzf.roommatch.model.common.BaseEntity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Tags")
public class Tag extends BaseEntity{


    @NotEmpty(message = "NAME CANNOT BE EMPTY")
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany(mappedBy = "tags")
    private Set<User> users;

    @ManyToMany(mappedBy = "tags")
    private Set<Apartment> apartments;
    
}