package exam.parkReview.repository;

import exam.parkReview.domain.entity.Member;
import exam.parkReview.domain.entity.Park;
import exam.parkReview.domain.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    Optional<Review> findByParkAndMember(Park park, Member member);
    List<Review> findByPark(Park park);
    List<Review> findByMember(Member member);
    boolean existsByParkAndMember(Park park, Member member);
}
