package ea.sof.ms_user.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import ea.sof.ms_user.interfaces.MsAuth;
import ea.sof.shared.models.Auth;
import ea.sof.shared.models.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    MsAuth msAuth;

    @HystrixCommand(fallbackMethod = "addAuthFallback")
    public ResponseEntity<Response> addAuth(Auth auth) {
        LOGGER.info("calling from ms_user(addAuth) to ms_auth");
        return msAuth.addAuth(auth);
    }

    @HystrixCommand(fallbackMethod = "validateTokenFalback")
    public ResponseEntity<Response> validateToken(String auth) {
        LOGGER.info("calling from ms_user(validateToken) to ms_auth");
        return msAuth.validateToken(auth);
    }

    public ResponseEntity<Response> addAuthFallback(Auth auth) {
        LOGGER.error("Authentication service is not reachable");
        return ResponseEntity.ok(new Response());
    }

    public ResponseEntity<Response> validateTokenFalback(String auth) {
        LOGGER.error("Authentication service is not reachable");
        return ResponseEntity.ok(new Response());
    }
}
