package exam.parkReview.dto.review;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateReviewRequestDto {

    @NotBlank(message = "내용을 입력해주세요.")
    @Size(max = 100, message = "리뷰 내용은 최대 100자까지 입력 가능합니다.")
    private String content;

    @NotNull(message = "평점을 입력해주세요.")
    @Min(value = 1, message = "평점은 최소 1점이어야 합니다.")
    @Max(value = 5, message = "평점은 최대 5점이어야 합니다.")
    private int rating;
}
