package com.petfound.backend.Mapper;
import com.petfound.backend.Entity.ShelterPets.ShelterPets;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ShelterPetsMapper {

    int insertSelective(ShelterPets shelterPets);

    ShelterPets selectByShelterPetsId(@Param("shelterPetsId")Integer shelterPetsId);

    List<ShelterPets> selectByShelterId(@Param("shelterId")Integer shelterId, @Param("startRow")Integer startRow, @Param("pageSize")Integer pageSize);

    int updateByShelterPetsId(@Param("updated")ShelterPets updated,@Param("shelterPetsId")Integer shelterPetsId);

    int deleteByPetId(@Param("petId")Integer petId);

    int deleteByShelterId(@Param("shelterId")Integer shelterId);

    int deleteByShelterPetsId(@Param("shelterPetsId")Integer shelterPetsId);

    int countByShelterId(@Param("shelterId")Integer shelterId);

    Integer countShelterPetsId();

}