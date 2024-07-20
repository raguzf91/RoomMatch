package com.raguzf.roommatch.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder.Default;
import lombok.experimental.SuperBuilder;


@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "Apartments")
public class Apartment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "availableFrom", nullable = false)
    private LocalDate availableFrom;

    @Column(name = "createdAt", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updatedAt")
    private LocalDateTime updatedAt;


    //TODO Make it a double 
    @Column(name = "numberOfRooms", nullable = false)
    private int numberOfRooms;

    @Column(name = "area", nullable = false)
    @Min(value = 0)
    private double area;

    @Column(name = "builtDate", nullable = false)
    private int builtDate;

    @OneToOne(mappedBy = "apartment", cascade = CascadeType.ALL)
    private Location location;

    @Default
    private boolean isListed = false;

    @OneToOne(mappedBy = "apartment", cascade = CascadeType.ALL)
    private Listing listing;

    @OneToMany(mappedBy = "apartment", cascade = CascadeType.ALL)
    private Set<Photo> photos;

    @ManyToMany
    @JoinTable(
        name = "ApartmentTags",
        joinColumns = @JoinColumn(name = "apartmentId"),
        inverseJoinColumns = @JoinColumn(name = "tagId")
    )
    private Set<Tag> tags;

}
