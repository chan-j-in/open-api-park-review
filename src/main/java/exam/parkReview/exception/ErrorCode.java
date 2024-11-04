package exam.parkReview.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    PARK_NOT_FOUND(HttpStatus.NOT_FOUND, "Park not found"),
    DATA_LOAD_FAILED(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to load data");

    private HttpStatus httpStatus;
    private String message;
}
