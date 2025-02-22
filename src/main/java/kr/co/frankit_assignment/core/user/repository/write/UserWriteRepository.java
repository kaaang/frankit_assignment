package kr.co.frankit_assignment.core.user.repository.write;

import java.util.UUID;
import kr.co.frankit_assignment.core.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserWriteRepository extends JpaRepository<User, UUID> {
    boolean existsByEmail(String email);
}
