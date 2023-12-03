package kr.cws.aop;

import kr.cws.exception.AccessDeniedException;
import kr.cws.exception.NotFoundException;
import kr.cws.mapper.CarWashMapper;
import kr.cws.model.domain.CarWash;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * 관리자 검증 AOP.
 *
 * @since 1.0.0
 */
@Aspect
@Component
@RequiredArgsConstructor
public class OwnerCheckAspect {

    private final CarWashMapper carWashMapper;

    /**
     * {@link kr.cws.annotation.OwnerCheck}가 붙어있는 메소드에 관리자 검증 AOP 적용.
     *
     * @since 1.0.0
     */
    @Before("@annotation(kr.cws.annotation.OwnerCheck) && args(userId, carWashId, ..)")
    public void ownerCheck(Long userId, Long carWashId) {
        CarWash carWash = carWashMapper.selectCarWashById(carWashId)
            .orElseThrow(() -> new NotFoundException("Select not found car Wash"));

        if (!carWash.getUserId().equals(userId)) {
            throw new AccessDeniedException("The service cannot be accessed.");
        }
    }
}
