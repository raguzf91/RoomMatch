package com.raguzf.roommatch.listing;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import com.raguzf.roommatch.apartment.Apartment;
import com.raguzf.roommatch.apartment.ApartmentMapper;
import com.raguzf.roommatch.apartment.ApartmentRepository;
import com.raguzf.roommatch.user.User;
import com.raguzf.roommatch.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ListingService {

    private final ListingRepository listingRepository;
    private final ApartmentMapper apartmentMapper;
    private final ApartmentRepository apartmentRepository;
    private final UserRepository userRepository;

    /**
     * Saves a new listing.
     *
     * @param request the listing request containing apartment details
     * @param connectedUser the authentication object of the currently connected user
     * @return the ID of the saved listing
     */
    @Transactional
    public Integer save(ListingRequest request, Authentication connectedUser) {
        User user = ((User) connectedUser.getPrincipal());
        Apartment apartment = apartmentMapper.toApartment(request.apartment());
        Listing listing = Listing.builder()
                                .user(user)
                                .apartment(apartment)
                                .build();
        apartment.setListing(listing);
        apartment.setListed(true);
        apartmentRepository.save(apartment);

        user.addListing(listing);
        userRepository.save(user);

        listingRepository.save(listing);
        return listing.getId();
    }

    /**
     * Deletes a listing by its ID.
     *
     * @param listingId the ID of the listing to delete
     * @param connectedUser the authentication object of the currently connected user
     */
    @Transactional
    public void delete(Integer listingId, Authentication connectedUser) {
        User user = (User) connectedUser.getPrincipal();
        Listing listing = listingRepository.findById(listingId)
                .orElseThrow(() -> new EntityNotFoundException("No Listing found with ID: " + listingId));

        if (!listing.getUser().getId().equals(user.getId())) {
            throw new SecurityException("User not authorized to delete this listing");
        }

        Apartment apartment = listing.getApartment();
        apartment.setListing(null);
        apartment.setListed(false);
        apartmentRepository.save(apartment);

        user.removeListing(listing);
        userRepository.save(user);

        listingRepository.delete(listing);
    }
}
