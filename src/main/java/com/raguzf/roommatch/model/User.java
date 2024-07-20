package com.raguzf.roommatch.model;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.raguzf.roommatch.model.common.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Builder.Default;
import lombok.experimental.SuperBuilder;
import jakarta.persistence.*;
import java.security.Principal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
@Entity
@Table(name = "Users")
public class User extends BaseEntity implements UserDetails, Principal {


    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "firstName", nullable = false)
    private String firstName;

    @Column(name = "lastName", nullable = false)
    private String lastName;

    @Column(name = "bio")
    private String bio;

    @Column(name = "phoneNumber")
    private String phoneNumber;


    @Enumerated(EnumType.STRING)
    @Column(name = "gender", nullable = false)
    private Gender gender;

    @Column(name = "enabled")
    @Default
    private boolean enabled = false;

    @Default
    private boolean accountLocked = false;

    @Column(name = "address")
    private String address;

    @Column(name = "dateOfBirth", nullable = false)
    private LocalDate dateOfBirth;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "UserRoles",
        joinColumns = @JoinColumn(name = "userId"),
        inverseJoinColumns = @JoinColumn(name = "roleId"))
    private Set<Role> roles;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.LAZY)
    private Set<Listing> listings;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.LAZY)
    private Set<Photo> photos;

    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinTable(
        name = "UserTags",
        joinColumns = @JoinColumn(name = "userId"),
        inverseJoinColumns = @JoinColumn(name = "tagId"))
    private Set<Tag> tags;


    @Override
    public String getName() {
        return this.email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles
            .stream()
            .map(r -> new SimpleGrantedAuthority(r.getName()))
            .collect(Collectors.toList());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !accountLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public String getFullName () {
        return this.firstName + this.lastName;
    }


}


