package exam.parkReview.repository;

import exam.parkReview.domain.entity.Park;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ParkRepository extends JpaRepository<Park, Long> {
    Optional<Park> findByParkNum(long parkNum);
}
