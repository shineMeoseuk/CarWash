package kr.cws.mapper;

import java.util.Optional;
import kr.cws.model.domain.User;
import kr.cws.model.dto.response.UserInfoRes;
import org.apache.ibatis.annotations.Mapper;

/**
 * User Mapper
 *
 * @since 1.0.0
 */
@Mapper
public interface UserMapper {

    void insertUser(User user);

    boolean isExistsEmail(String email);

    Optional<User> selectUserByEmail(String email);

    void deleteUser(Long id);

    Optional<UserInfoRes> selectUserById(Long userId);

    void updateUserById(User user);
}
