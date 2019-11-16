package ea.sof.ms_user.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;
    @Column(unique = true, name = "email")
    private String email;
    private String phone;

    public UserEntity() {
    }

    public UserEntity(String email, String phone) {
        this.email = email;
        this.phone = phone;
    }

}