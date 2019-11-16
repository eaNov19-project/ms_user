package ea.sof.ms_user.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    private String username;
    private String email;
    private String phone;

    public UserEntity() {
    }

    public UserEntity(String username, String email, String phone) {
        this.username = username;
        this.email = email;
        this.phone = phone;
    }

}
