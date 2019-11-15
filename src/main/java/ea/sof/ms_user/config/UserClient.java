package ea.sof.ms_user.config;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "Auth", url = "localhost:8888/api/add-auth")
public interface UserClient {

}
