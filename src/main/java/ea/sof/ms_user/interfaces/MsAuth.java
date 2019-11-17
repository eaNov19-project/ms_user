package ea.sof.ms_user.interfaces;

import ea.sof.shared.showcases.MsAuthShowcase;
import org.springframework.cloud.openfeign.FeignClient;

//@FeignClient(name="auth-ms", url = "${AUTHENTICATE_SERVICE}")
@FeignClient(name="ms-auth", url = "http://localhost:8080")
public interface MsAuth extends MsAuthShowcase {
}
