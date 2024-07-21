package com.raguzf.roommatch.location;
import com.raguzf.roommatch.apartment.Apartment;
import com.raguzf.roommatch.common.BaseEntity;
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
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Locations")
public class Location extends BaseEntity{
    

    @NotEmpty(message = "ADDRESS CANNOT BE EMPTY")
    @Column(name = "address", nullable = false, unique = true)
    private String address;

    @NotEmpty(message = "CITY CANNOT BE EMPTY")
    @Column(name = "city", nullable = false)
    private String city;

    @NotEmpty(message = "STATE CANNOT BE EMPTY")
    @Column(name = "state", nullable = false)
    private String state;

    @NotEmpty(message = "ZIP CODE CANNOT BE EMPTY")
    @Column(name = "zipCode", nullable = false)
    private String zipCode;

    @NotEmpty(message = "LATITUDE CANNOT BE EMPTY")
    @Column(name = "latitude", nullable = false)
    private Double latitude;

    @NotEmpty(message = "LONGITUDE CANNOT BE EMPTY")
    @Column(name = "longitude", nullable = false)
    private Double longitude;

    @OneToOne
    @JoinColumn(name = "id")
    private Apartment apartment;


}
