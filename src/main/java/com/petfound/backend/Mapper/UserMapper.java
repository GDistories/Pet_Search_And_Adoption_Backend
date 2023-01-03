package com.petfound.backend.Mapper;
import com.petfound.backend.Entity.User.UserFilter;
import org.apache.ibatis.annotations.Param;

import com.petfound.backend.Entity.User.User;

import java.util.List;

public interface UserMapper {
    int insertUser(User record);

    User selectByUsernameAndPassword(@Param("username")String username,@Param("password")String password);

    User selectByUsername(@Param("username")String username);

    User selectByEmail(@Param("email")String email);

    User selectByPhone(@Param("phone")String phone);

    int updateByUsername(@Param("updated")User updated,@Param("username")String username);

    int updatePasswordByUsername(@Param("updatedPassword") String updatedPassword,@Param("username") String username);

    int deleteByUsername(@Param("username")String username);

    List<User> selectUserByFilter(@Param("startRow") Integer startRow, @Param("pageSize") Integer pageSize, @Param("userFilter") UserFilter userFilter);

    Integer countUserByFilter(@Param("userFilter") UserFilter userFilter);
}