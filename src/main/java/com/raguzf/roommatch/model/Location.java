package com.raguzf.roommatch.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Location {
    
    private Integer id;
    @NotEmpty(message = "ADDRESS CANNOT BE EMPTY")
    private String address;
    @NotEmpty(message = "CITY CANNOT BE EMPTY")
    private String city;
    @NotEmpty(message = "STATE CANNOT BE EMPTY")
    private String state;
    @NotEmpty(message = "ZIP CODE CANNOT BE EMPTY")
    private String zip_code;
    @NotEmpty(message = "LATIDUTE CANNOT BE EMPTY")
    private Double latidute;
    @NotEmpty(message = "LONGITUDE CANNOT BE EMPTY")
    private Double longitude;

    /*
     * CREATE TABLE Locations (
	id INT AUTO_INCREMENT PRIMARY KEY,
    address VARCHAR(255) NOT NULL,
    city VARCHAR(100) NOT NULL,
    state VARCHAR(100) NOT NULL,
    zip_code VARCHAR(10) NOT NULL,
    latitude DECIMAL(10, 8) NOT NULL,
    longitude DECIMAL(11, 8) NOT NULL
);
     * 
     */

}
