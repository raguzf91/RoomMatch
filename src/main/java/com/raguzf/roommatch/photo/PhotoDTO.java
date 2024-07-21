package com.raguzf.roommatch.photo;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record PhotoDTO(
    Integer id,

    @NotNull(message = "PHOTO URL CANNOT BE EMPTY")
    @NotEmpty(message = "PHOTO URL CANNOT BE EMPTY")
    String photoUrl

) {
    
}
