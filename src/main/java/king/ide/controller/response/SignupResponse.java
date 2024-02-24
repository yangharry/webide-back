package king.ide.controller.response;

import lombok.Data;

@Data
public class SignupResponse {
    private long id;
    private int httpCode;
    private String message;

    public SignupResponse(long id, int httpCode, String message) {
        this.id = id;
        this.httpCode = httpCode;
        this.message = message;
    }
}
