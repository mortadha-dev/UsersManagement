package mortadha.formation.UsersManagement.model;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id ;
    private String userId;
    private String firstName ;
    private String lastName;
    private String username;
    private String password;
    private String email ;
    private String profileImageUrl ;
    private Date lastLoginDate ;
    private Date lastLoginDateDisplay;
    private Date joinDate ;
    private String [] roles;
    private String[] authorities ;
    private boolean isActive ;
    private boolean isNotLocked;
}