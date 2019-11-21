package ea.sof.ms_user.service;
import ea.sof.ms_user.entity.UserEntity;
import ea.sof.ms_user.exception.UserAlreadyExistException;
import ea.sof.ms_user.exception.UserNotFoundException;

public interface UserService {
    public UserEntity addUser(UserEntity userEntity) throws UserAlreadyExistException;
    public UserEntity editUser(UserEntity userEntity) throws UserNotFoundException;
    public UserEntity getUser(String email) throws UserNotFoundException;
    public void saveNoOfQuestions(String email, Integer counter) throws UserNotFoundException;
    public void deleteUser(String email) throws UserNotFoundException;
}
