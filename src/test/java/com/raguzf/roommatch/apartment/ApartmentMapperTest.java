package com.raguzf.roommatch.apartment;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import com.raguzf.roommatch.location.Location;
import com.raguzf.roommatch.location.LocationDTO;
import com.raguzf.roommatch.location.LocationRepository;
import com.raguzf.roommatch.photo.Photo;
import com.raguzf.roommatch.photo.PhotoDTO;
import com.raguzf.roommatch.photo.PhotoRepository;
import com.raguzf.roommatch.tag.Tag;
import com.raguzf.roommatch.tag.TagRepository;
import com.raguzf.roommatch.user.User;

import jakarta.persistence.EntityNotFoundException;

public class ApartmentMapperTest {

    @Mock
    private LocationRepository locationRepository;

    @Mock
    private PhotoRepository photoRepository;

    @Mock
    private TagRepository tagRepository;

    @InjectMocks
    private ApartmentMapper apartmentMapper;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testToApartmentWithNullRequest() {
        assertNull(apartmentMapper.toApartment(null));
    }

    @Test
    void testToApartment() {
        LocationDTO locationDTO = new LocationDTO(
            "address", "city", "state", "zipCode", 1.0, 2.0
        );
        List<PhotoDTO> photoDTOs = Arrays.asList(new PhotoDTO(1, "url1"),
                                                new PhotoDTO(2, "url2"));
        Set<String> tags = new HashSet<>(Arrays.asList("tag1", "tag2"));
        ApartmentRequest request = ApartmentRequest.builder()
                                    .title("Title")
                                    .description("Description")
                                    .price(new BigDecimal("123.45"))
                                    .numberOfRooms(3)
                                    .area(100)
                                    .builtDate(2020)
                                    .location(locationDTO)
                                    .photos(photoDTOs)
                                    .tags(tags)
                                    .build();
        Location location = Location.builder()
                                    .address("address")
                                    .city("city")
                                    .state("state")
                                    .zipCode("zipCode")
                                    .latitude(1.0)
                                    .longitude(2.0)
                                    .build();
        when(locationRepository.save(any(Location.class))).thenReturn(location);
        Tag tag1 = Tag.builder()
                      .name("tag1")
                      .build();
        Tag tag2 = Tag.builder()
                        .name("tag1")
                        .build();      
        
        when(tagRepository.findByName("tag1")).thenReturn(Optional.of(tag1));
        when(tagRepository.findByName("tag2")).thenReturn(Optional.of(tag2));

        Apartment apartment = apartmentMapper.toApartment(request);

        assertNotNull(apartment);
        assertEquals("Title", apartment.getTitle());
        assertEquals("Description", apartment.getDescription());
        assertEquals(new BigDecimal("123.45"), apartment.getPrice());
        assertEquals(3.0, apartment.getNumberOfRooms());
        assertEquals(100.0, apartment.getArea());
        assertEquals(2020, apartment.getBuiltDate());
        assertEquals(2, apartment.getPhotos().size());
        assertEquals(2, apartment.getTags().size());
        verify(locationRepository, times(1)).save(any(Location.class));
        verify(photoRepository, times(2)).save(any(Photo.class));
    }

    @Test
    void testToApartmentThrowsEntityNotFoundException() {
        LocationDTO locationDTO = new LocationDTO(
                "address", "city", "state", "zipCode", 1.0, 2.0);
        List<PhotoDTO> photoDTOs = Arrays.asList(
                new PhotoDTO(1, "url1"),
                new PhotoDTO(2, "url2"));
        Set<String> tags = new HashSet<>(Arrays.asList("tag1", "tag2"));

        ApartmentRequest request = ApartmentRequest.builder()
                .title("Title")
                .description("Description")
                .price(new BigDecimal("123.45"))
                .numberOfRooms(3.0)
                .area(100.0)
                .builtDate(2020)
                .location(locationDTO)
                .photos(photoDTOs)
                .tags(tags)
                .build();

        when(tagRepository.findByName(anyString())).thenReturn(Optional.empty());
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                        () -> {
                            apartmentMapper.toApartment(request);
                        }
        );
        assertEquals("No tag found by name: tag1", exception.getMessage());
    
    
    }

    @Test
    void testFromApartmentWithNullApartment() {
            assertNull(apartmentMapper.fromApartment(null));
        }
    
    
    @Test
    void testFromApartment() {
        Location location = Location.builder().address("address").city("city").state("state")
                .zipCode("zipCode").latitude(1.0).longitude(2.0).build();
        Photo photo1 = Photo.builder().photoUrl("url1").build();
        Photo photo2 = Photo.builder().photoUrl("url2").build();
        Tag tag1 = Tag.builder()
                      .name("tag1")
                      .build();
        Tag tag2 = Tag.builder()
                        .name("tag2")
                        .build();
        User user = User.builder()
                        .firstName("Owner")
                        .lastName("")
                        .build();
        Apartment apartment = Apartment.builder()
                        .id(1)
                        .title("Title")
                        .description("Description")
                        .price(new BigDecimal("123.45"))
                        .availableFrom(LocalDate.of(2020, 1, 1))
                        .numberOfRooms(3.0)
                        .area(100.0)
                        .builtDate(2020)
                        .location(location)
                        .isListed(true)
                        .photos(Arrays.asList(photo1, photo2))
                        .tags(new HashSet<>(Arrays.asList(tag1, tag2)))
                        .user(user)
                        .build();
        ApartmentResponse response = apartmentMapper.fromApartment(apartment);

        assertNotNull(response);
        assertEquals(1, response.getId());
        assertEquals("Title", response.getTitle());
        assertEquals("Description", response.getDescription());
        assertEquals(123.45, response.getPrice());
        assertEquals("2020-01-01", response.getAvailableFrom());
        assertEquals(3.0, response.getNumberOfRooms());
        assertEquals(100.0, response.getArea());
        assertEquals(2020, response.getBuiltDate());
        assertEquals(2, response.getPhotos().size());
        assertEquals(2, response.getTags().size());
        assertEquals("Owner", response.getOwner());
    }                        











   /* 
    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {

    }

    @BeforeAll
    static void beforeAll() {

    }

    @AfterAll
    static void afterAll() {

    }

    @Test
    public void testMethod() {

    }
   */ 
    
}
