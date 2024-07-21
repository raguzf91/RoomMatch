package com.raguzf.roommatch.role;
import java.util.Set;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.raguzf.roommatch.common.BaseEntity;
import com.raguzf.roommatch.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "Roles")
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Role extends BaseEntity{

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   public Integer id;

   @NotEmpty(message = "ROLE NAME CANNOT BE EMPTY")
   @Column(name = "name", nullable = false)
   private String name;

   @ManyToMany(mappedBy = "roles")
   @JsonIgnore
   private Set<User> users;

}
