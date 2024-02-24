package king.ide.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import king.ide.controller.request.SignupRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEMBER_ID")
    private Long id;

    private String name;
    private String loginId;
    private String password;
    private String mobileNumber;

    @Enumerated(EnumType.STRING)
    private Authority authority;
    
    public Member(String loginId, Authority authority) {
        this.loginId = loginId;
        this.authority = authority;
    }

    public void createMember(SignupRequest request, PasswordEncoder passwordEncoder) {
        this.name = request.getName();
        this.loginId = request.getLoginId();
        this.password = encodePassword(passwordEncoder, request.getPassword());
        this.mobileNumber = request.getMobileNumber();
        this.authority = Authority.ROLE_USER;
    }

    private String encodePassword(PasswordEncoder passwordEncoder, String password) {
        return passwordEncoder.encode(password);
    }

//    public static boolean matchPassword(PasswordEncoder passwordEncoder, String rawPassword, String encodedPassword) {
//        return passwordEncoder.matches(rawPassword, encodedPassword);
//    }

    public void withdrawal() {
        this.authority = Authority.ROLE_WITHDRAWAL;
    }


    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", loginId='" + loginId + '\'' +
                ", password='" + password + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", authority=" + authority +
                '}';
    }
}
