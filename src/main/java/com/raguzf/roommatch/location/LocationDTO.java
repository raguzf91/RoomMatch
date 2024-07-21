package com.raguzf.roommatch.location;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record LocationDTO(
    @NotNull(message = "ADDRESS CANNOT BE EMPTY")
    @NotEmpty(message = "ADDRESS CANNOT BE EMPTY")
    String address,

    @NotNull(message = "CITY CANNOT BE EMPTY")
    @NotEmpty(message = "CITY CANNOT BE EMPTY")
    String city,

    @NotNull(message = "STATE CANNOT BE EMPTY")
    @NotEmpty(message = "STATE CANNOT BE EMPTY")
    String state,

    @NotNull(message = "ZIP CODE CANNOT BE EMPTY")
    @NotEmpty(message = "ZIP CODE CANNOT BE EMPTY")
    String zipCode,

    @NotNull(message = "LATITUDE CANNOT BE EMPTY")
    Double latitude,

    @NotNull(message = "LONGITUDE CANNOT BE EMPTY")
    Double longitude

) {
    
}
