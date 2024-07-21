package com.raguzf.roommatch.apartment;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.raguzf.roommatch.location.Location;
import com.raguzf.roommatch.location.LocationMapper;
import com.raguzf.roommatch.location.LocationRepository;
import com.raguzf.roommatch.photo.Photo;
import com.raguzf.roommatch.photo.PhotoMapper;
import com.raguzf.roommatch.photo.PhotoRepository;
import com.raguzf.roommatch.tag.Tag;
import com.raguzf.roommatch.tag.TagRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

/**
 * Service class for mapping between Apartment entities and their DTO representations.
 */
@Service
@RequiredArgsConstructor
public class ApartmentMapper {

    private final LocationRepository locationRepository;
    private final PhotoRepository photoRepository;
    private final TagRepository tagRepository;

    /**
     * Converts an ApartmentRequest DTO to an Apartment entity.
     *
     * @param request the ApartmentRequest DTO containing the apartment details
     * @return the Apartment entity corresponding to the given DTO, or null if the request is null
     */
    public Apartment toApartment(ApartmentRequest request) {
        if (request == null) {
            return null;
        }

        Location location = LocationMapper.toLocation(request.location());
        List<Photo> photos = request.photos().stream()
                .map(PhotoMapper::toPhoto)
                .collect(Collectors.toList());

        Set<Tag> tags = request.tags().stream()
                .map(tagName -> tagRepository.findByName(tagName)
                        .orElseThrow(() -> new EntityNotFoundException("No tag found by name: " + tagName)))
                .collect(Collectors.toSet());

        Apartment apartment = Apartment.builder()
                .title(request.title())
                .description(request.description())
                .price(request.price())
                .numberOfRooms(request.numberOfRooms())
                .area(request.area())
                .builtDate(request.builtDate())
                .location(location)
                .photos(new ArrayList<>(photos))
                .tags(tags)
                .build();

        photos.forEach(photo -> photo.setApartment(apartment));
        locationRepository.save(location);
        photos.forEach(photoRepository::save);

        return apartment;
    }

    /**
     * Converts an Apartment entity to an ApartmentResponse DTO.
     *
     * @param apartment the Apartment entity to be converted
     * @return the ApartmentResponse DTO corresponding to the given entity, or null if the apartment is null
     */
    public ApartmentResponse fromApartment(Apartment apartment) {
        if (apartment == null) {
            return null;
        }

        return ApartmentResponse.builder()
                .id(apartment.getId())
                .title(apartment.getTitle())
                .description(apartment.getDescription())
                .price(apartment.getPrice().setScale(2, RoundingMode.HALF_UP).doubleValue())
                .availableFrom(apartment.getAvailableFrom().toString())
                .numberOfRooms(apartment.getNumberOfRooms())
                .area(apartment.getArea())
                .builtDate(apartment.getBuiltDate())
                .location(apartment.getLocation())
                .isListed(apartment.isListed())
                .photos(apartment.getPhotos())
                .tags(tagsToNames(apartment.getTags()))
                .owner(apartment.getUser().getFullName())
                .build();
    }

    /**
     * Converts a set of Tag entities to a set of tag names.
     *
     * @param tags the set of Tag entities to be converted
     * @return a set of tag names corresponding to the given Tag entities
     */
    private Set<String> tagsToNames(Set<Tag> tags) {
        return tags.stream()
                .map(Tag::getName)
                .collect(Collectors.toSet());
    }
}
