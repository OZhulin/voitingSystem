package ru.zhulin.oleg.restsystem.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.EnumSet;
import java.util.Set;

@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(name = "user_name_idx", columnNames = "name")})
@Getter
@Setter
@ToString
@NoArgsConstructor
public class User extends NamedParentEntity{
    @NotBlank
    @Length(min = 5)
    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "role")
    private Set<Role> roleSet;

    public User(Long id, String name, String password, Set<Role> roleSet) {
        super(id, name);
        this.password = password;
        this.roleSet = roleSet;
    }

    public User(Long id, String name, String password, Role role, Role... roles) {
        this(id, name, password, EnumSet.of(role, roles));
    }
}
