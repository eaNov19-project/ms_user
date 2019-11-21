package ea.sof.ms_user.controller;
import ea.sof.ms_user.entity.UserEntity;
import ea.sof.ms_user.exception.UserAlreadyExistException;
import ea.sof.ms_user.exception.UserNotFoundException;
import ea.sof.ms_user.service.AuthService;
import ea.sof.ms_user.serviceImpl.UserServiceImpl;
import ea.sof.shared.models.Auth;
import ea.sof.shared.models.Response;
import ea.sof.shared.models.User;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.InetAddress;
import java.net.UnknownHostException;


@RestController
@CrossOrigin
@RequestMapping("/users")
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @Autowired
    AuthService authService;

    @Autowired
    private UserServiceImpl userService;

    @Value("${app.version}")
    private String appVersion;

    @GetMapping("/health")
    public ResponseEntity<?> index() {
        String host = "Unknown host";
        try {
            host = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("User service (" + appVersion + "). Host: " + host, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Response> addUser(@RequestBody User user) {
        ModelMapper modelMapper = new ModelMapper();
        UserEntity entity = new UserEntity();
        modelMapper.map(user, entity);
        try {
            Auth auth = new Auth();
            auth.setEmail(user.getEmail());
            auth.setPassword(user.getPassword());

            //first add the user
            UserEntity userEntity = userService.addUser(entity);
            if(userEntity != null) {
                //save user in authentication
                auth.setUserId(userEntity.getUserId());
                ResponseEntity<Response> ms_auth =  authService.addAuth(auth);
                if(ms_auth.getBody().getSuccess()) {
                    Response response = new Response();
                    response.setSuccess(true);
                    response.addObject("user", userEntity);
                    return new ResponseEntity(response, HttpStatus.OK);
                } else {
                    userService.deleteUser(userEntity.getEmail());
                    Response response = new Response();
                    response.setSuccess(false);
                    response.setMessage("Authentication Service network has some temporary issues");
                    LOGGER.warn("Add users :: Exception. " + "Authentication network has some temporary issues");
                    return new ResponseEntity(response, HttpStatus.BAD_GATEWAY);
                }
            } else {
                Response response = new Response();
                response.setSuccess(false);
                response.setMessage("User Service network has some temporary issues");
                LOGGER.warn("Add users :: Exception. " + "Authentication network has some temporary issues");
                return new ResponseEntity(response, HttpStatus.BAD_GATEWAY);
            }
        } catch (UserAlreadyExistException e) {
            Response response = new Response();
            response.setSuccess(false);
            response.setMessage(e.getMessage());
            LOGGER.warn("Add users :: Exception. " + e.getMessage());
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            Response response = new Response();
            response.setSuccess(false);
            response.setMessage("User was not added due to network issues");
            LOGGER.warn("Add users :: Exception. " + e.getMessage());
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/edit")
    public ResponseEntity<Response> editUser(@RequestBody User user,
                                             @RequestHeader("Authorization") String token) {
        try {
            //check if user is authenticated before editing
            Response authCheckResp = isAuthorized(token);
            if (authCheckResp.getSuccess()) {
                ModelMapper modelMapper = new ModelMapper();
                UserEntity entity = new UserEntity();
                modelMapper.map(user, entity);

                UserEntity userEntity = userService.editUser(entity);
                Response response = new Response();
                response.setSuccess(true);
                response.addObject("user", userEntity);

                return new ResponseEntity(response, HttpStatus.OK);
            } else {
                return ResponseEntity.
                        status(HttpStatus.UNAUTHORIZED).body(new Response(false, "Invalid Token"));
            }

        } catch (UserNotFoundException e) {
            Response response = new Response();
            response.setSuccess(false);
            response.setMessage(e.getMessage());
            LOGGER.warn("Edit users :: Exception. " + e.getMessage());
            return new ResponseEntity(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            Response response = new Response();
            response.setSuccess(false);
            response.setMessage("User was not edited due to network issues");
            LOGGER.warn("Edit users :: Exception. " + e.getMessage());
            return new ResponseEntity(response, HttpStatus.NOT_MODIFIED);
        }
    }

    @GetMapping("/get/{email}")
    public ResponseEntity<Response> getUser(@PathVariable("email") String email,
                                            @RequestHeader("Authorization") String token ) {
        try {
            //Check if request is authorized
            Response authCheckResp = isAuthorized(token);
            if (authCheckResp.getSuccess()) {
                UserEntity userEntity = userService.getUser(email);
                Response response = new Response();
                response.setSuccess(true);
                response.addObject("user", userEntity);

                return new ResponseEntity(response, HttpStatus.OK);
            } else {
                return ResponseEntity.
                        status(HttpStatus.UNAUTHORIZED).body(new Response(false, "Invalid Token"));
            }

        } catch (UserNotFoundException e) {
            Response response = new Response();
            response.setSuccess(false);
            response.setMessage(e.getMessage());
            LOGGER.warn("Get users :: Exception. " + e.getMessage());
            return new ResponseEntity(response, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            Response response = new Response();
            response.setSuccess(false);
            response.setMessage("User was not found due to network issues");
            LOGGER.warn("Get users :: Exception. " + e.getMessage());
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/question-increment/{email}")
    public void updateNoOfQuestions(@PathVariable("email") String email) throws Exception {
        try {
            userService.saveNoOfQuestions(email, 1);
        }  catch (UserNotFoundException e) {
            LOGGER.warn("Update no of Questions :: Exception. " + e.getMessage());
            throw new UserNotFoundException(e.getMessage());
        } catch (Exception e) {
            LOGGER.warn("Update no of Questions :: Exception. " + e.getMessage());
            throw new Exception(e.getMessage());
        }

    }

    private Response isAuthorized(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            LOGGER.warn("isAuthorized :: Exception. " + "Invalid token");
            return new Response(false, "Invalid token");
        }
        try {
            ResponseEntity<Response> result = authService.validateToken(authHeader);

            if (!result.getBody().getSuccess()) {
                LOGGER.warn("isAuthorized :: Exception. " + "Invalid token");
                return new Response(false, "Invalid token");
            }
            return result.getBody();

        }catch (Exception e){
            LOGGER.warn("isAuthorized :: Exception. " + e.getMessage());
            return new Response(false, "exception", e);
        }
    }

}
