package com.raguzf.roommatch.apartment;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.raguzf.roommatch.common.PageResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("apartments")
@RequiredArgsConstructor
@Tag(name = "Apartment")
public class ApartmentController {
    
    private final ApartmentService service;

    @PostMapping("/create")
    public ResponseEntity<Integer> saveApartment(
        @Valid @RequestBody ApartmentRequest request,
        Authentication connectedUser
    ) {
        return ResponseEntity.ok(service.save(request, connectedUser));
    }

    @GetMapping("{apartment-id}")
    public ResponseEntity<ApartmentResponse> findApartmentById (
        @PathVariable("apartment-id") Integer apartmentId
    ) {
        return ResponseEntity.ok(service.findById(apartmentId));
    }

    // PAGING FUNCTIONALITY
    @GetMapping("")
    public ResponseEntity<PageResponse<ApartmentResponse>> findAllApartments(
        @RequestParam(name = "page", defaultValue = "0", required = false) int page, 
        @RequestParam(name = "size", defaultValue = "10", required = false) int size,
        Authentication connectedUser  

    ) {
        return ResponseEntity.ok(service.findAllApartments(page, size, connectedUser));
    }

    @GetMapping("/owner")
    public ResponseEntity<PageResponse<ApartmentResponse>> findAllApartmentsByOwner(
        @RequestParam(name = "page", defaultValue = "0", required = false) int page, 
        @RequestParam(name = "size", defaultValue = "10", required = false) int size,
        Authentication connectedUser

    ) {

        return ResponseEntity.ok(service.findAllApartmentsByOwner(page, size, connectedUser));
    }
}
