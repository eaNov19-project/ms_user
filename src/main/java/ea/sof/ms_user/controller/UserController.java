package ea.sof.ms_user.controller;
import ea.sof.ms_user.entity.UserEntity;
import ea.sof.ms_user.service.AuthService;
import ea.sof.ms_user.serviceImpl.UserServiceImpl;
import ea.sof.shared.models.Auth;
import ea.sof.shared.models.Response;
import ea.sof.shared.models.User;
import org.modelmapper.ModelMapper;
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
    @Autowired
    AuthService authService;

    @Autowired
    private UserServiceImpl userService;

    @Value("${APP_VERSION}")
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
            ResponseEntity<Response> ms_auth =  authService.addAuth(auth);

            if (ms_auth.getBody().getSuccess()) {
                UserEntity userEntity = userService.addUser(entity);
                Response response = new Response();
                response.setSuccess(true);
                response.addObject("user", userEntity);

                return new ResponseEntity(response, HttpStatus.OK);
            }
        } catch (Exception e) {
            Response response = new Response();
            response.setSuccess(false);
            response.addObject("exception", e.getMessage());
            return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
        }
        Response response = new Response();
        response.setSuccess(false);
        response.addObject("exception", "invalid data");
        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/edit")
    public ResponseEntity<Response> editUser(@RequestBody User user,
                                             @RequestHeader("Authorization") String token) {
        try {
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

        } catch (Exception e) {
            Response response = new Response();
            response.setSuccess(false);
            response.addObject("exception", e.getMessage());
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

        } catch (Exception e) {
            Response response = new Response();
            response.setSuccess(false);
            response.addObject("exception", e.getMessage());
            return new ResponseEntity(response, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/question-increment/{email}")
    public void updateNoOfQuestions(@PathVariable("email") String email) throws Exception {
        try {
            userService.saveNoOfQuestions(email, 1);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

    }

    private Response isAuthorized(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return new Response(false, "Invalid token");
        }
        try {
            ResponseEntity<Response> result = authService.validateToken(authHeader);

            if (!result.getBody().getSuccess()) {
                return new Response(false, "Invalid token");
            }
            return result.getBody();

        }catch (Exception e){
            return new Response(false, "exception", e);
        }
    }

}
