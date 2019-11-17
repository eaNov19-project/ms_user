package ea.sof.ms_user.servicetest;

import ea.sof.ms_user.entity.UserEntity;
import ea.sof.ms_user.repository.UserRepository;
import ea.sof.ms_user.serviceImpl.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceTest {
    @InjectMocks
    UserServiceImpl userService;
    @Mock
    UserRepository userRepository;

    @Test
    public void addUserTest() throws Exception {
        UserEntity mockUserEntity = new UserEntity("van@gmail.com", "+1641234567");
        Mockito.when(userRepository.save(mockUserEntity)).thenReturn(mockUserEntity);

        assertEquals((userService.addUser(mockUserEntity)), mockUserEntity);
    }

    @Test
    public void editUserTest() throws Exception {
        UserEntity mockUserEntity = new UserEntity("van@gmail.com", "+1641234567");
        mockUserEntity.setUserId(1);
        Mockito.when(userRepository.findByEmail(mockUserEntity.getEmail())).thenReturn(mockUserEntity);
        Mockito.when(userRepository.save(mockUserEntity)).thenReturn(mockUserEntity);

        assertEquals((userService.editUser(mockUserEntity)), mockUserEntity);
    }

    @Test
    public void getUserTest() throws Exception {
        String username = "van@gmail.com";
        UserEntity mockUserEntity = new UserEntity("van@gmail.com", "+1641234567");

        Mockito.when(userRepository.findByEmail(username)).thenReturn(mockUserEntity);
        assertEquals((userService.getUser(username)), mockUserEntity);
    }
}
