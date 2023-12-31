package kr.cws.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 로그인시 UserId를 받아올 어노테이션. {@link kr.cws.utils.CurrentUserIdResolver} 를 통해 파라미터로 받아서 사용.
 *
 * @since 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface CurrentUserId {

}
