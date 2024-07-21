package com.raguzf.roommatch.user;
import java.time.LocalDateTime;
import com.raguzf.roommatch.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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
@Table(name = "Tokens")
public class Token extends BaseEntity {
    
    @Column(name = "token", nullable = false)
    private String token;
    @Column(name = "validatedAt")
    private LocalDateTime validatedAt;
    @Column(name = "expiresAt")
    private LocalDateTime expiresAt;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;
}
