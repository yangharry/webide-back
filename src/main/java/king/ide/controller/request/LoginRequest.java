package king.ide.controller.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class LoginRequest {
    @NotEmpty(message = "아이디를 입력해 주세요.")
    private String loginId;

    @NotEmpty(message = "비밀번호를 입력해 주세요.")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,16}$", message = "비밀번호는 8자 이상, 16자 이하의 영문, 숫자 및 특수문자를 조합하여 사용해야 합니다.")
    private String password;
}
