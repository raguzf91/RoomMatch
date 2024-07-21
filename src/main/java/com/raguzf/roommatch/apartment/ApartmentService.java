package com.raguzf.roommatch.apartment;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import com.raguzf.roommatch.common.PageResponse;
import com.raguzf.roommatch.user.User;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

/**
 * Service class for managing Apartment entities.
 * @author raguzf
 */
@Service
@RequiredArgsConstructor
public class ApartmentService {

    private final ApartmentMapper apartmentMapper;
    private final ApartmentRepository apartmentRepository;

    /**
     * Saves a new Apartment entity.
     *
     * @param request the ApartmentRequest DTO containing the apartment details
     * @param connectedUser the authentication object of the currently connected user
     * @return the ID of the saved Apartment entity
     */
    @Transactional
    public Integer save(ApartmentRequest request, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Apartment apartment = apartmentMapper.toApartment(request);
        apartment.setUser(user);
        apartmentRepository.save(apartment);
        return apartment.getId();
    }

    /**
     * Finds an Apartment entity by its ID.
     *
     * @param apartmentId the ID of the apartment to find
     * @return the ApartmentResponse DTO corresponding to the found Apartment entity
     * @throws EntityNotFoundException if no Apartment entity is found with the given ID
     */
    public ApartmentResponse findById(Integer apartmentId) {
        return apartmentRepository.findById(apartmentId)
                .map(apartmentMapper::fromApartment)
                .orElseThrow(() -> new EntityNotFoundException("No Apartment found with ID: " + apartmentId));
    }

    /**
     * Finds all displayable Apartment entities with pagination.
     *
     * @param page the page number to retrieve
     * @param size the number of apartments per page
     * @param connectedUser the authentication object of the currently connected user
     * @return a PageResponse containing a list of ApartmentResponse DTOs and pagination information
     */
    public PageResponse<ApartmentResponse> findAllApartments(int page, int size, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<Apartment> apartments = apartmentRepository.findAllDisplayableApartments(user.getId(), pageable);
        List<ApartmentResponse> apartmentResponse = apartments.stream()
                .map(apartmentMapper::fromApartment)
                .collect(Collectors.toList());
        return new PageResponse<>(
                apartmentResponse,
                apartments.getNumber(),
                apartments.getSize(),
                apartments.getTotalElements(),
                apartments.getTotalPages(),
                apartments.isFirst(),
                apartments.isLast()
        );
    }

    /**
     * Finds all Apartment entities owned by the currently connected user with pagination.
     *
     * @param page the page number to retrieve
     * @param size the number of apartments per page
     * @param connectedUser the authentication object of the currently connected user
     * @return a PageResponse containing a list of ApartmentResponse DTOs and pagination information
     */
    public PageResponse<ApartmentResponse> findAllApartmentsByOwner(int page, int size, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<Apartment> apartments = apartmentRepository.findAll(ApartmentSpecification.withOwner(user.getId()), pageable);

        List<ApartmentResponse> apartmentResponse = apartments.stream()
                .map(apartmentMapper::fromApartment)
                .collect(Collectors.toList());
        return new PageResponse<>(
                apartmentResponse,
                apartments.getNumber(),
                apartments.getSize(),
                apartments.getTotalElements(),
                apartments.getTotalPages(),
                apartments.isFirst(),
                apartments.isLast()
        );
    }
}
