package com.raguzf.roommatch.apartment;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import com.raguzf.roommatch.location.LocationDTO;
import com.raguzf.roommatch.photo.PhotoDTO;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
@Builder
public record ApartmentRequest(
    Integer id,

    @NotNull(message = "100")
    @NotEmpty(message = "100")
    String title,

    @NotNull(message = "101")
    @NotEmpty(message = "101")
    String description,

    @NotNull(message = "102")
    @NotEmpty(message = "102")
    BigDecimal price,

    @NotNull(message = "103")
    @NotEmpty(message = "103")
    double numberOfRooms,

    @NotNull(message = "104")
    @NotEmpty(message = "104")
    double area,

    @NotNull(message = "105")
    @NotEmpty(message = "105")
    int builtDate,

    @NotNull(message = "106")
    @NotEmpty(message = "106")
    LocationDTO location,

    @NotNull(message = "107")
    List<PhotoDTO> photos,

    @NotNull(message = "108")
    Set<String> tags

) {

    
} 