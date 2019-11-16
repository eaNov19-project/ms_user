package ea.sof.ms_user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

public class HomeController {
    @GetMapping("/")
    public ResponseEntity<?> home(){
        return ResponseEntity.ok().build();
    }
}
