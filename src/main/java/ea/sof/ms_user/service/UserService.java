package ea.sof.ms_user.service;
import ea.sof.ms_user.entity.UserEntity;

public interface UserService {
    public UserEntity addUser(UserEntity userEntity) throws Exception;
    public UserEntity editUser(UserEntity userEntity) throws Exception;
    public UserEntity getUser(String username) throws Exception;
    public void saveNoOfQuestions(String email, Integer counter) throws Exception;
}
