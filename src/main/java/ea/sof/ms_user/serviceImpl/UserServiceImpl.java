package ea.sof.ms_user.serviceImpl;

import ea.sof.ms_user.entity.UserEntity;
import ea.sof.ms_user.repository.UserRepository;
import ea.sof.ms_user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity<?> addUser(UserEntity userEntity) {
        return ResponseEntity.ok(userRepository.save(userEntity));
    }

    @Override
    public ResponseEntity<?> getUser(String username) {
        return ResponseEntity.ok().body(userRepository.findByUsername(username));
    }
}
