package kr.co.frankit_assignment.core.user.repository.read;

import java.util.Optional;
import java.util.UUID;
import kr.co.frankit_assignment.core.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserReadRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
}
