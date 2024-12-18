package exam.parkReview.controller;

import exam.parkReview.dto.park.ParkDetailsDto;
import exam.parkReview.dto.park.ParkSummaryDto;
import exam.parkReview.service.ParkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/parks")
@Slf4j
public class ParkController {

    private final ParkService parkService;

    @GetMapping
    public ResponseEntity<List<ParkSummaryDto>> getParks(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {

        List<ParkSummaryDto> parkSummaries = parkService.getParks(page, size);
        return ResponseEntity.ok(parkSummaries);
    }

    @GetMapping("/{parkNum}")
    public ResponseEntity<ParkDetailsDto> getParkDetails(@PathVariable("parkNum") long parkNum) {

        ParkDetailsDto parkDetails = parkService.getParkDetails(parkNum);
        return ResponseEntity.ok(parkDetails);
    }

}
