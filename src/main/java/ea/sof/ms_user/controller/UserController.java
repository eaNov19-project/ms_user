package ea.sof.ms_user.controller;
import ea.sof.ms_user.entity.UserEntity;
import ea.sof.ms_user.interfaces.MsAuth;
import ea.sof.ms_user.serviceImpl.UserServiceImpl;
import ea.sof.shared.models.Auth;
import ea.sof.shared.models.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.parser.Entity;

@RestController
@CrossOrigin
@RequestMapping("api/users")
public class UserController {
    @Autowired
    private MsAuth msAuth;
    @Autowired
    private UserServiceImpl userService;

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody User user) throws Exception {
        ModelMapper modelMapper = new ModelMapper();
        UserEntity entity = new UserEntity();
        modelMapper.map(user, entity);

        try {
            Auth auth = new Auth();
            auth.setUsername(user.getUsername());
            auth.setPassword(user.getPassword());
            ResponseEntity<Auth> ms_auth = (ResponseEntity<Auth>) msAuth.addAuth(auth);

            if (ms_auth.getBody().getUsername() != null) {
                return ResponseEntity.ok().body(userService.addUser(entity));
            }
        } catch (Exception e) {
            System.out.println("addUser():  " +e.getMessage());
            return ResponseEntity.badRequest().body("adding of user failed: " + e.getMessage());
        }
        return ResponseEntity.badRequest().body("invalid data");
    }

    @PostMapping("/edit")
    public ResponseEntity<UserEntity> editUser(@RequestBody User user) {
        ModelMapper modelMapper = new ModelMapper();
        UserEntity entity = new UserEntity();
        modelMapper.map(user, entity);

        try {
            return new ResponseEntity(userService.addUser(entity), HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("editUser():  " +e.getMessage());
            return new ResponseEntity("edit of user failed: " + e.getMessage(), HttpStatus.NOT_MODIFIED);
        }
    }

    @GetMapping("/get")
    public ResponseEntity<UserEntity> getUser(@PathVariable String username) {
        try {
            return new ResponseEntity(userService.getUser(username), HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("getUser():  " +e.getMessage());
            return new ResponseEntity("User Retrieval failed: " + e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

}
