package com.petfound.backend.Mapper;
import com.petfound.backend.Entity.Pet.PetFilter;
import org.apache.ibatis.annotations.Param;
import com.petfound.backend.Entity.Pet.Pet;

import java.util.List;

public interface PetMapper {
    Pet selectByPetId(@Param("petId")Integer petId);

    int insertSelective(Pet pet);

    int updateByPetId(@Param("updated")Pet updated,@Param("petId")Integer petId);

    int deleteByPetId(@Param("petId")Integer petId);

    List<Pet> selectPetByFilter(@Param("startRow") Integer startRow, @Param("pageSize") Integer pageSize, @Param("petFilter") PetFilter petFilter);

    int countPetByFilter(@Param("petFilter") PetFilter petFilter);
}