package com.raguzf.roommatch.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@Data
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Apartment extends Location {

    @NotEmpty(message = "TITLE CANNOT BE EMPTY")
    private String title;
    @NotEmpty(message = "DESCRIPTION CANNOT BE EMPTY")
    private String description;
    @NotEmpty(message = "PRICE CANNOT BE EMPTY")
    private Double price;
    @NotEmpty(message = "AVAILABLE DATE CANNOT BE EMPTY")
    private LocalDate availableFrom;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * 
     * CREATE TABLE Apartments (
	id INT AUTO_INCREMENT PRIMARY KEY,
    location_id INT NOT NULL,
    owner_id INT NOT NULL,
    title VARCHAR(100) NOT NULL,
    description TEXT NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    available_from DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (owner_id) REFERENCES Users(id) ON DELETE CASCADE,
    FOREIGN KEY (location_id) REFERENCES Locations(id) ON DELETE CASCADE
);

     */
    
}
