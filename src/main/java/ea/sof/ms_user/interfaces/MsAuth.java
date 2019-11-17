package ea.sof.ms_user.interfaces;

import ea.sof.shared.showcases.MsAuthShowcase;
import org.springframework.cloud.openfeign.FeignClient;

//@FeignClient(name="ms-auth", url = "http://localhost:8080/auth")
//@FeignClient(name="AUTH_MS", url = "${AUTH_MS_URL}")
@FeignClient(name = "${feign.name}", url = "${feign.url}")
public interface MsAuth extends MsAuthShowcase {
}
