package exam.parkReview.repository;

import exam.parkReview.domain.entity.Member;
import exam.parkReview.domain.entity.Park;
import exam.parkReview.domain.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByPark(Park park);
    List<Review> findByMember(Member member);
}
