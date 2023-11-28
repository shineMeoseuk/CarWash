package kr.cws.aop;

import kr.cws.exception.AccessDeniedException;
import kr.cws.mapper.CarWashMapper;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * 블랙리스트 체크 AOP.
 *
 * @since 1.0.0
 */
@Aspect
@Component
@RequiredArgsConstructor
public class BlackCheckAspect {

    private final CarWashMapper carWashMapper;

    /**
     * {@link kr.cws.annotation.BlackCheck} 가 붙어 있는 메소드에 블랙리스트 체크 AOP 적용.
     *
     * @since 1.0.0
     */
    @Before("@annotation(kr.cws.annotation.BlackCheck) && args(userId, carWashId, ..)")
    public void blackCheck(Long userId, Long carWashId) {
        if (carWashMapper.isBlockUser(userId, carWashId)) {
            throw new AccessDeniedException("The service cannot be accessed.");
        }
    }
}
