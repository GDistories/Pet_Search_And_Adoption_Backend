package com.petfound.backend.Mapper;
import com.petfound.backend.Entity.PetSearch.PetSearch;
import com.petfound.backend.Entity.PetSearch.PetSearchFilter;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PetSearchMapper {

    int insertSelective(PetSearch petSearch);

    int updateByPetSearchId(@Param("updated")PetSearch updated,@Param("petSearchId")Integer petSearchId);

    int deleteByPetSearchId(@Param("petSearchId")Integer petSearchId);

    PetSearch selectAllByPetSearchId(@Param("petSearchId")Integer petSearchId);

    List<PetSearch> selectByUsername(@Param("username")String username, @Param("startRow") Integer startRow, @Param("pageSize") Integer pageSize);

    Integer countPetSearchId();

    int deleteByPetId(@Param("petId")Integer petId);

    int deleteByUsername(@Param("username")String username);

    Integer countPetSearchIdByUsername(@Param("username")String username);

    Integer countPetSearchIdByFilter(@Param("petSearchFilter") PetSearchFilter petSearchFilter);

    List<PetSearch> selectByPage(@Param("startRow") Integer startRow, @Param("pageSize") Integer pageSize);

    List<PetSearch> selectByFilter(@Param("startRow") Integer startRow, @Param("pageSize") Integer pageSize, @Param("petSearchFilter") PetSearchFilter petSearchFilter);


}