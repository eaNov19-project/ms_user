package ea.sof.ms_user.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class HomeController {
    @GetMapping("/")
    public ResponseEntity<?> index(){
        return ResponseEntity.ok().build();
    }
}
