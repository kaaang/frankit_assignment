package kr.co.frankit_assignment.core.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import kr.co.frankit_assignment.core.kernel.domain.BaseEntityAggregateRoot;
import kr.co.frankit_assignment.core.user.vo.UserRoleType;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PACKAGE)
@Getter
@Entity
@Table(name = "user_bcs_users")
public class User extends BaseEntityAggregateRoot<User> implements UserDetails {
    @Id private UUID id;

    @Column @NotNull private String email;

    @Column @NotNull @ToString.Exclude private String password;

    @Column
    @NotNull
    @Enumerated(EnumType.STRING)
    private UserRoleType roleType;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> roleType.name());
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}
