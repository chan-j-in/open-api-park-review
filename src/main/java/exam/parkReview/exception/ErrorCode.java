package exam.parkReview.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    PARK_NOT_FOUND(HttpStatus.NOT_FOUND, "Park not found"),
    DATA_LOAD_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to load data"),
    DUPLICATE_MEMBER(HttpStatus.CONFLICT, "Duplicate member found"),
    PASSWORD_DO_NOT_MATCH(HttpStatus.BAD_REQUEST, "Passwords do nat match"),
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "Invalid username  or password"),
    MEMBER_NOT_AUTHENTICATED(HttpStatus.UNAUTHORIZED, "Member not authenticated"),
    MEMBER_NOT_FOUND(HttpStatus.NOT_FOUND, "Member not found"),
    REVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "Review not found"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "Unauthorized");

    private HttpStatus httpStatus;
    private String message;
}
