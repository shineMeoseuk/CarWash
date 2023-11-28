package kr.cws.mapper;

import java.util.List;
import kr.cws.model.domain.Review;
import kr.cws.model.dto.response.ReviewRes;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Review Mapper.
 *
 * @since 1.0.0
 */
@Mapper
public interface ReviewMapper {

    List<ReviewRes> selectReviewsByCarWashId(Long carWashId);

    void insertReview(Review review);

    int updateReview(Review review);

    int deleteReview(@Param("userId") Long userId, @Param("reviewId") Long reviewId);
}
