package com.petfound.backend.Mapper;

import com.petfound.backend.Entity.Shelter.Shelter;
import com.petfound.backend.Entity.Shelter.ShelterFilter;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ShelterMapper {
    Shelter selectByUsernameAndPassword(@Param("username")String username,@Param("password")String password);

    int countShelterBySearch(@Param("search") String search);

    List<Shelter> selectShelterBySearch(@Param("startRow") Integer startRow, @Param("pageSize") Integer pageSize, @Param("search") String search);

    Shelter selectByUsername(@Param("username")String username);

    Shelter selectByEmail(@Param("email")String email);

    Shelter selectByPhone(@Param("phone")String phone);

    Shelter selectByShelterId(@Param("shelterId")Integer shelterId);

    int updateByUsername(@Param("updated")Shelter updated,@Param("username")String username);

    int updatePasswordByUsername(@Param("updatedPassword")String updatedPassword,@Param("username")String username);

    Integer selectShelterIdByUsername(@Param("username")String username);

    int deleteByUsername(@Param("username")String username);

    int insertSelective(Shelter shelter);

    List<Shelter> selectShelterByFilter(@Param("startRow") Integer startRow, @Param("pageSize") Integer pageSize, @Param("shelterFilter") ShelterFilter shelterFilter);

    Integer countShelterByFilter(@Param("shelterFilter") ShelterFilter shelterFilter);
}