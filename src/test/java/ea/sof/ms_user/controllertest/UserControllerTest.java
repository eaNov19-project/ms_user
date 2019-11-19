//package ea.sof.ms_user.controllertest;
//import ea.sof.ms_user.controller.UserController;
//import ea.sof.ms_user.entity.UserEntity;
//import ea.sof.ms_user.interfaces.MsAuth;
//import ea.sof.ms_user.serviceImpl.UserServiceImpl;
//import ea.sof.shared.models.Auth;
//import ea.sof.shared.models.Response;
//import ea.sof.shared.models.User;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//public class UserControllerTest {
//
//    @InjectMocks
//    UserController userController;
//    @Mock
//    UserServiceImpl userService;
//    @Mock
//    private MsAuth msAuth;
//
//    @Test
//    public void addUserTest() throws Exception {
//        UserEntity mockUserEntity = new UserEntity("van@gmail.com",  "+1641234567");
//        User user= new User("van@gmail.com", null, "+1641234567");
//
//        Auth auth =  new Auth("van@gmail.com", "chinedu");
//        Response response =  new Response();
//        response.setSuccess(true);
//
//        Response response2 =  new Response();
//        response.setSuccess(true);
//        response.addObject("user", mockUserEntity);
//
//        Mockito.when(msAuth.addAuth(auth)).thenReturn(new ResponseEntity<>(response, HttpStatus.OK));
//        Mockito.when(userService.addUser(mockUserEntity)).thenReturn(mockUserEntity);
//
//        assertEquals((userController.addUser(user).getBody()), response2);
//    }
//
//    @Test
//    public void editUserTest() throws Exception {
//        UserEntity mockUserEntity = new UserEntity("van@gmail.com", "+1641234567");
//        User user= new User("van@gmail.com", "", "+1641234567");
//
//        String token = "Bearer @@@@@#$ssshhienntt*%fnnnnnn";
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", token);
//        String tokenTest = headers.get("Authorization").get(0);
//
//        Response response =  new Response();
//        response.setSuccess(true);
//        response.addObject("user", mockUserEntity);
//
//        Response response2 = new Response();
//        response.setSuccess(true);
//
//        Mockito.when(msAuth.validateToken(tokenTest)).thenReturn(new ResponseEntity<>(response2, HttpStatus.OK));
//
//        Mockito.when(userService.editUser(mockUserEntity)).thenReturn(mockUserEntity);
//        assertEquals((userController.editUser(user, tokenTest).getBody()), response);
//    }
//
//    @Test
//    public void getUserTest() throws Exception {
//        String email = "van@gmail.com";
//        UserEntity mockUserEntity = new UserEntity("van@gmail.com", "+1641234567");
//
//        String token = "Bearer @@@@@#$ssshhienntt*%fnnnnnn";
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("Authorization", token);
//        String tokenTest = headers.get("Authorization").get(0);
//
//        Response response =  new Response();
//        response.setSuccess(true);
//        response.addObject("user", mockUserEntity);
//
//        Response response2 = new Response();
//        response.setSuccess(true);
//
//
//        Mockito.when(msAuth.validateToken(tokenTest)).thenReturn(new ResponseEntity<>(response2, HttpStatus.OK));
//
//        Mockito.when(userService.getUser(email)).thenReturn(mockUserEntity);
//        assertEquals((userController.getUser(email, tokenTest).getBody()), response);
//    }
//
////
////    @Test
////    public void noOfQuestionTest() throws Exception {
////        String email = "van@gmail.com";
////
////        Mockito.doThrow(new Exception()).when(userService.saveNoOfQuestions(email, 1));
////        assert ((userController.updateNoOfQuestions(email)), void);
////    }
//
//}
