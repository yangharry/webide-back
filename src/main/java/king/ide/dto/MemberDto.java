package king.ide.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MemberDto {
    private Long id;
    private String name;
    private String loginId;
    private String mobileNumber;
}