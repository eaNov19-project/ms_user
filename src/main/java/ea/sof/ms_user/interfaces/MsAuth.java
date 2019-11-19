package ea.sof.ms_user.interfaces;

import ea.sof.shared.showcases.MsAuthShowcase;
import org.springframework.cloud.openfeign.FeignClient;

//@FeignClient(name="ms-auth", url = "http://localhost:8080")
@FeignClient(name="msAuth", url = "${authenticate.service}")
public interface MsAuth extends MsAuthShowcase {
}