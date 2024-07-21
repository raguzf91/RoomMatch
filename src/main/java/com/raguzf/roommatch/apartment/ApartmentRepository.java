package com.raguzf.roommatch.apartment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ApartmentRepository extends JpaRepository<Apartment, Integer>, JpaSpecificationExecutor<Apartment> {

    @Query("""
            SELECT apartment
            FROM Apartment apartment
            WHERE apartment.isListed = true
            AND apartment.user.id != :userId  
            """)
    Page<Apartment> findAllDisplayableApartments(@Param("userId") Integer userId, Pageable pageable);

    
} 
