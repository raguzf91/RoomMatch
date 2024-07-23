package com.raguzf.roommatch.listing;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("listings")
@RequiredArgsConstructor
@Tag(name = "Listing")
@RestController("listing")
public class ListingController {
    private final ListingService service;

    @PostMapping("/create")
    public ResponseEntity<Integer> saveApartment(
        @Valid @RequestBody ListingRequest request,
        Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.save(request, connectedUser));
    }

    
}
