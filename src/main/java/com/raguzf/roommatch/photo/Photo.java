package com.raguzf.roommatch.photo;
import com.raguzf.roommatch.apartment.Apartment;
import com.raguzf.roommatch.common.BaseEntity;
import com.raguzf.roommatch.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Photos")
public class Photo extends BaseEntity{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "PHOTO URL CANNOT BE EMPTY")
    @Column(name = "photoUrl", nullable = false)
    private String photoUrl;

    @OneToOne
    @JoinColumn(name = "userId", unique = true)
    private User user;

    @ManyToOne
    @JoinColumn(name = "apartmentId", unique = true)
    private Apartment apartment;
}
