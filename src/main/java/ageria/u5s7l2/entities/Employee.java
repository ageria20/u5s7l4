package ageria.u5s7l2.entities;


import ageria.u5s7l2.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor

public class Employee implements UserDetails {

    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String avatar;
    @Enumerated(EnumType.STRING)
    private Role role;


    public Employee(String username, String name, String surname, String email, String password, String avatar) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.avatar = avatar;
        this.role = Role.EMPLOYEE;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role.name()));
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}
