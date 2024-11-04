package exam.parkReview.service;

import exam.parkReview.domain.entity.Park;
import exam.parkReview.dto.ParkDetailsDto;
import exam.parkReview.dto.ParkSummaryDto;
import exam.parkReview.repository.ParkRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ParkService {

    private final ParkRepository parkRepository;

    public ResponseEntity<List<ParkSummaryDto>> getParks(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Park> parks = parkRepository.findAll(pageable);

        List<ParkSummaryDto> parkSummaries = parks.getContent().stream()
                .map(ParkSummaryDto::new)
                .toList();

        return ResponseEntity.ok().body(parkSummaries);
    }


    public ResponseEntity<ParkDetailsDto> getParkDetails(long parkNum) {

        Park park = parkRepository.findByParkNum(parkNum)
                .orElseThrow(() -> new IllegalArgumentException("공원을 찾을 수 없습니다."));
        ParkDetailsDto parkDetailsDto = new ParkDetailsDto(park);

        return ResponseEntity.ok().body(parkDetailsDto);
    }
}
