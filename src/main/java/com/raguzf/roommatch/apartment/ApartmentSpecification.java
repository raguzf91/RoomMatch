package com.raguzf.roommatch.apartment;
import org.springframework.data.jpa.domain.Specification;

/**
 * Utility class for creating specifications for querying Apartment entities.
 */
public class ApartmentSpecification {

    /**
     * Creates a specification for finding apartments owned by a specific user.
     *
     * @param ownerId the ID of the owner
     * @return a Specification for querying apartments by owner ID
     */
    public static Specification<Apartment> withOwner(Integer ownerId) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("user").get("id"), ownerId);
    }
}
