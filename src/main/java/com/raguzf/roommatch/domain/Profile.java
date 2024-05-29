package com.raguzf.roommatch.domain;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class Profile extends User{
    
    @NotEmpty(message = "FIRST NAME CANNOT BE EMPTY")
    private String firstName;
    @NotEmpty(message = "FIRST NAME CANNOT BE EMPTY")
    private String lastName;
    private String bio;
    private List<Tag> tags;
    
}
