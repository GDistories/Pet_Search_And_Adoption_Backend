package com.petfound.backend.Mapper;

import com.petfound.backend.Entity.PetFound.PetFound;
import com.petfound.backend.Entity.PetFound.PetFoundFilter;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PetFoundMapper {
    int insertSelective(PetFound petFound);

    int updateByPetFoundId(@Param("updated")PetFound updated,@Param("petFoundId")Integer petFoundId);

    int deleteByPetFoundId(@Param("petFoundId")Integer petFoundId);

    List<PetFound> selectByUsername(@Param("username")String username, @Param("startRow") Integer startRow, @Param("pageSize") Integer pageSize);

    List<PetFound> selectByShelterId(@Param("shelterId")Integer shelterId, @Param("startRow") Integer startRow, @Param("pageSize") Integer pageSize);

    PetFound selectAllByPetFoundId(@Param("petFoundId")Integer petFoundId);

    Integer countPetFoundIdByFilter(@Param("petFoundFilter") PetFoundFilter petFoundFilter);

    Integer countPetFoundIdByUsername(@Param("username")String username);

    Integer countPetFoundIdByShelterId(@Param("shelterId")Integer shelterId);

    Integer countPetFoundId();

    int deleteByPetId(@Param("petId")Integer petId);

    int deleteByUsername(@Param("username")String username);

    int deleteByShelterId(@Param("shelterId")Integer shelterId);

    List<PetFound> selectByFilter(@Param("startRow") Integer startRow, @Param("pageSize") Integer pageSize, @Param("petFoundFilter") PetFoundFilter petFoundFilter);

}