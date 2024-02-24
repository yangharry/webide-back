package king.ide.controller.response;

import king.ide.dto.MemberDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MemberResponse {
    private int httpCode;
    private MemberDto member;
}
