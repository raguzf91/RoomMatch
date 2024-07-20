package com.raguzf.roommatch.model;

public enum Gender {
   
    MALE,
    FEMALE,
    OTHER;

    public static Gender fromString(String gender) {
        try {
            return Gender.valueOf(gender.toUpperCase());
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid gender: " + gender);
        }
    }
}
