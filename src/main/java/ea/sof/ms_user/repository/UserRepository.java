package ea.sof.ms_user.repository;

import ea.sof.ms_user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    public ResponseEntity<?> findByUsername(String username);
}
