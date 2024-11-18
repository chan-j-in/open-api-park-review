package exam.parkReview.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberSignupDto {

    private String username;
    private String password;
    private String passwordCheck;
}
