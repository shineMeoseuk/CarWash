package kr.cws.service;

import java.util.List;
import kr.cws.exception.NotFoundException;
import kr.cws.mapper.ReviewMapper;
import kr.cws.model.domain.Review;
import kr.cws.model.dto.request.ReviewReq;
import kr.cws.model.dto.response.ReviewRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Review Service
 *
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewMapper reviewMapper;

    /**
     * 리뷰 목록 조회하기.
     *
     * @since 1.0.0
     */
    public List<ReviewRes> getReviews(Long carWashId) {
        return reviewMapper.selectReviewsByCarWashId(carWashId);
    }

    /**
     * 리뷰 생성하기.
     *
     * @since 1.0.0
     */
    public void createReview(ReviewReq reviewReq, Long userId, Long carWashId) {
        Review review = Review.builder()
            .userId(userId)
            .carWashId(carWashId)
            .description(reviewReq.getDescription())
            .score(reviewReq.getScore())
            .build();

        reviewMapper.insertReview(review);
    }

    /**
     * 리뷰 업데이트하기
     *
     * @since 1.0.0
     */
    public void updateReview(ReviewReq reviewReq, Long userId, Long reviewId) {
        Review review = Review.builder()
            .id(reviewId)
            .userId(userId)
            .description(reviewReq.getDescription())
            .score(reviewReq.getScore())
            .build();

        int updateCount = reviewMapper.updateReview(review);
        if (updateCount == 0) {
            throw new NotFoundException("Select not found review");
        }
    }

    /**
     * 리뷰 삭제하기.
     *
     * @since 1.0.0
     */
    public void deleteReview(Long userId, Long reviewId) {
        int deleteCount = reviewMapper.deleteReview(userId, reviewId);
        if (deleteCount == 0) {
            throw new NotFoundException("Select not found review");
        }
    }
}
