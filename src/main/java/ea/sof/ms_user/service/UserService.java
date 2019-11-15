package ea.sof.ms_user.service;

import ea.sof.ms_user.entity.UserEntity;
import org.springframework.http.ResponseEntity;

public interface UserService {
    public ResponseEntity<?> addUser(UserEntity userEntity);
    public ResponseEntity<?> getUser(String username);
}
