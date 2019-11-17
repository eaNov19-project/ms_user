package ea.sof.ms_user.serviceImpl;

import ea.sof.ms_user.entity.UserEntity;
import ea.sof.ms_user.repository.UserRepository;
import ea.sof.ms_user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserEntity addUser(UserEntity userEntity) throws Exception {
        try {
            userEntity.setCreatedDate(LocalDate.now());
            userEntity.setLastUpdated(LocalDate.now());
            userEntity.setNoOfQuestions(0);
            return userRepository.save(userEntity);
        } catch (Exception e) {
            throw new Exception("User not added" + e.getMessage());
        }
    }

    @Override
    public UserEntity editUser(UserEntity userEntity) throws Exception {
        try {
                UserEntity user = userRepository.findByEmail(userEntity.getEmail());
                user.setLastUpdated(LocalDate.now());
                user.setPhone(userEntity.getPhone());
                return userRepository.save(user);
        } catch (Exception e) {
            throw new Exception("User not edited" + e.getMessage());
        }
    }

    @Override
    public void saveNoOfQuestions(String email, Integer counter) throws Exception {
        try {
            UserEntity user = userRepository.findByEmail(email);
            if(user.getUserId() != null) {
                counter += user.getNoOfQuestions();
                user.setNoOfQuestions(counter);
                userRepository.save(user);
            }
        } catch (Exception e) {
            throw new Exception("No of Question not increased" + e.getMessage());
        }
    }


    @Override
    public UserEntity getUser(String username) throws Exception {
        try {
            return userRepository.findByEmail(username);
        } catch (Exception e) {
            throw new Exception("User not found" + e.getMessage());
        }
    }
}
