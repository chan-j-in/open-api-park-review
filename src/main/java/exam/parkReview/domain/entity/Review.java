package exam.parkReview.domain.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;
    private int rating;

    @ManyToOne
    @JoinColumn(name = "park_id")
    private Park park;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Review(String content, int rating, Park park, Member member) {
        this.content = content;
        this.rating = rating;
        this.park = park;
        this.member = member;
    }

    public void update(String content, Integer rating) {
        if (content != null) {
            this.content = content;
        }
        if (rating != null) {
            this.rating = rating;
        }
    }
}