package com.raguzf.roommatch.location;
import org.springframework.stereotype.Service;


@Service
public class LocationMapper {
    
    public static Location toLocation(LocationDTO locationDTO) {
        if(locationDTO == null) {
            return null;
        }

        Location location = Location.builder()
                                    .address(locationDTO.address())
                                    .city(locationDTO.city())
                                    .state(locationDTO.state())
                                    .zipCode(locationDTO.zipCode())
                                    .latitude(locationDTO.latitude())
                                    .longitude(locationDTO.longitude())
                                    .build();
        return location;
    }
}
