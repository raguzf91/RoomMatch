package com.raguzf.roommatch.listing;

import com.raguzf.roommatch.apartment.ApartmentRequest;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ListingRequest(
    @NotNull()
    @NotEmpty()
    ApartmentRequest apartment

) {

    
}
