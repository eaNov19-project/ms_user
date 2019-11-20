package ea.sof.ms_user.serviceImpl;

import ea.sof.ms_user.entity.UserEntity;
import ea.sof.ms_user.exception.UserAlreadyExistException;
import ea.sof.ms_user.exception.UserNotFoundException;
import ea.sof.ms_user.repository.UserRepository;
import ea.sof.ms_user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.Clock;
import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserEntity addUser(UserEntity userEntity) throws UserAlreadyExistException {
            UserEntity user = userRepository.findByEmail(userEntity.getEmail());
            if(user != null) {
                throw new UserAlreadyExistException("User already exists in our system");
            } else {
                userEntity.setCreatedDate(LocalDateTime.now(Clock.systemUTC()));
                userEntity.setLastUpdated(LocalDateTime.now(Clock.systemUTC()));
                userEntity.setNoOfQuestions(0);
                return userRepository.save(userEntity);
            }
    }

    @Override
    public UserEntity editUser(UserEntity userEntity) throws UserNotFoundException {
                UserEntity user = userRepository.findByEmail(userEntity.getEmail());
                if(user == null) {
                    throw new UserNotFoundException("User does not exist in our system");
                } else {
                    user.setLastUpdated(LocalDateTime.now(Clock.systemUTC()));
                    user.setPhone(userEntity.getPhone());
                    user.setName(userEntity.getName());
                    return userRepository.save(user);
                }
    }

    @Override
    public void saveNoOfQuestions(String email, Integer counter) throws UserNotFoundException {
            UserEntity user = userRepository.findByEmail(email);
            if(user == null) {
                throw new UserNotFoundException("User does not exist in our system");
            } else {
                counter += user.getNoOfQuestions();
                user.setNoOfQuestions(counter);
                userRepository.save(user);
            }
    }


    @Override
    public UserEntity getUser(String email) throws UserNotFoundException {
            UserEntity user = userRepository.findByEmail(email);
            if(user == null) {
                throw new UserNotFoundException("User does not exist in our system");
            } else {
                return user;
            }
    }
}
