package ea.sof.ms_user.repository;

import ea.sof.ms_user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    public UserEntity findByEmail(String username);
}
