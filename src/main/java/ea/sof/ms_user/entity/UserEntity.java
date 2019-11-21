package ea.sof.ms_user.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    @Column(unique = true, name = "email")
    private String email;
    private String phone;
    private String name;
    private Integer noOfQuestions;
    private LocalDateTime lastUpdated;
    private LocalDateTime createdDate;

    public UserEntity() {
    }

    public UserEntity(String email, String phone) {
        this.email = email;
        this.phone = phone;
    }

}
