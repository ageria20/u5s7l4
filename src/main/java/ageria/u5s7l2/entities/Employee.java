package ageria.u5s7l2.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor

public class Employee {

    @Id
    @GeneratedValue
    private Long id;
    private String username;
    private String name;
    private String surname;
    private String email;
    private String password;
    private String avatar;


    public Employee(String username, String name, String surname, String email, String password, String avatar) {
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.avatar = avatar;
    }


}
