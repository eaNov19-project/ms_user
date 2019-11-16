package ea.sof.ms_user.serviceImpl;

import ea.sof.ms_user.entity.UserEntity;
import ea.sof.ms_user.repository.UserRepository;
import ea.sof.ms_user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserEntity addUser(UserEntity userEntity) {
        return userRepository.save(userEntity);
    }

    @Override
    public UserEntity editUser(UserEntity userEntity) {
        UserEntity user = userRepository.findByEmail(userEntity.getEmail());
        if(user.getUserId() != null) {
            user.setPhone(userEntity.getPhone());
        }
        return userRepository.save(user);
    }


    @Override
    public UserEntity getUser(String username) {
        return userRepository.findByEmail(username);
    }
}
