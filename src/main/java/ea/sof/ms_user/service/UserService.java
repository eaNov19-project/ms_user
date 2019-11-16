package ea.sof.ms_user.service;
import ea.sof.ms_user.entity.UserEntity;

public interface UserService {
    public UserEntity addUser(UserEntity userEntity);
    public UserEntity editUser(UserEntity userEntity);
    public UserEntity getUser(String username);
}
