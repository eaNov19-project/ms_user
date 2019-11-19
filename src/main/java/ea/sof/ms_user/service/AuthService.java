package ea.sof.ms_user.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import ea.sof.ms_user.interfaces.MsAuth;
import ea.sof.shared.models.Auth;
import ea.sof.shared.models.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    MsAuth msAuth;

    @HystrixCommand(fallbackMethod = "fallback")
    public ResponseEntity<Response> addAuth(Auth auth) {
        return msAuth.addAuth(auth);
    }

    @HystrixCommand(fallbackMethod = "fallback")
    public ResponseEntity<Response> validateToken(String auth) {
        return msAuth.validateToken(auth);
    }

    public ResponseEntity<Response> fallback(Auth auth) {
        System.out.println("falling back");
        return ResponseEntity.ok(new Response());
    }
}
