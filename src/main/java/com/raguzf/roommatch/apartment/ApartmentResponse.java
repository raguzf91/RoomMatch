package com.raguzf.roommatch.apartment;
import java.util.List;
import java.util.Set;
import com.raguzf.roommatch.location.Location;
import com.raguzf.roommatch.photo.Photo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApartmentResponse {

    private Integer id;
    private String title;
    private String description;
    private Double price;
    private String availableFrom;
    private Double numberOfRooms;
    private Double area;
    private Integer builtDate;
    private Location location;
    private boolean isListed;
    private List<Photo> photos;
    private Set<String> tags;
    private String owner;

}
