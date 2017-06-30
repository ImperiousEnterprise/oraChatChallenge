package ora.chat.application.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ora.chat.application.globalerrors.SamePasswords;
import ora.chat.application.globalerrors.StrongPassword;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.util.Collection;
import java.util.List;


@Entity
@Table(name="users")
@Getter
@Setter
@SamePasswords(groups = {Users.ValidationStepTwo.class})
public class Users implements UserDetails {

    public interface ValidationStepOne {
        // validation group marker interface
    }

    public interface ValidationStepTwo {
        // validation group marker interface
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name",unique = true)
    @NotBlank(groups = {ValidationStepOne.class}, message = "error.name.notblank")
    private String name;

    @Column(name = "email",unique = true)
    @NotBlank(groups = {ValidationStepOne.class},message = "error.email.notblank")
    @Email(groups = {ValidationStepOne.class,ValidationStepTwo.class}, message = "error.email.notformed.well")
    private String email;

    @Column(name = "password")
    @NotBlank(groups = {ValidationStepOne.class},message = "error.password.notblank")
    @StrongPassword(groups = {ValidationStepTwo.class})
    private String password;

    @Column(name = "password_confirmation")
    @NotBlank(groups = {ValidationStepOne.class},message = "error.password_confirmation.notblank")
    private String password_confirmation;

    @JsonIgnore
    @Null
    private String username;



    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "users_authority",
            joinColumns = @JoinColumn(name = "users_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id"))
    private List<Authority> authorities;


    @Override
    public String getUsername() {
        return email;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }
}