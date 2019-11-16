package ea.sof.ms_user.controllerTest;
import ea.sof.ms_user.controller.UserController;
import ea.sof.ms_user.entity.UserEntity;
import ea.sof.ms_user.interfaces.MsAuth;
import ea.sof.ms_user.serviceImpl.UserServiceImpl;
import ea.sof.shared.models.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserControllerTest {

    @InjectMocks
    UserController userController;
    @Mock
    UserServiceImpl userService;
    @Mock
    private MsAuth msAuth;

    @Test
    public void addUserTest() {

    }

    @Test
    public void editUserTest() {
        UserEntity mockUserEntity = new UserEntity("van", null, "+1641234567");
        User user= new User("van", "", "+1641234567");

        Mockito.when(userService.addUser(mockUserEntity)).thenReturn(mockUserEntity);
        assertEquals((userController.editUser(user).getBody()), mockUserEntity);
    }

    @Test
    public void getUserTest() {
        String username = "van";
        UserEntity mockUserEntity = new UserEntity("vann", "", "+1641234567");

        Mockito.when(userService.getUser(username)).thenReturn(mockUserEntity);
        assertEquals((userController.getUser(username).getBody()), mockUserEntity);
    }

}
