package com.petfound.backend.Service;

import com.petfound.backend.Entity.Pet.Pet;
import com.petfound.backend.Entity.ShelterPets.ShelterPets;
import com.petfound.backend.Entity.ShelterPets.ShelterPetsFill;
import com.petfound.backend.Mapper.PetMapper;
import com.petfound.backend.Mapper.ShelterPetsMapper;
import com.petfound.backend.resp.CommonResp;
import com.petfound.backend.resp.ListResp;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ShelterPetService {
    @Resource
    private ShelterPetsMapper shelterPetsMapper;

    @Resource
    private PetMapper petMapper;

    public CommonResp<Integer> addShelterPet(ShelterPets shelterPets) {
        CommonResp<Integer> resp = new CommonResp<>();
        try {
            int result = shelterPetsMapper.insertSelective(shelterPets);
            Integer shelterPetId = shelterPets.getShelterPetsId();
            if (result == 1) {
                resp.setMessage("Add Shelter Pet Success!");
                resp.setContent(shelterPetId);
            } else {
                resp.setSuccess(false);
                resp.setMessage("Add Shelter Pet Failed!");
            }
        } catch (Exception e) {
            resp.setSuccess(false);
            resp.setMessage("Add Shelter Pet Failed!");
        }
        return resp;
    }

    public CommonResp<String> deleteShelterPet(Integer shelterPetId) {
        CommonResp<String> resp = new CommonResp<>();
        if (shelterPetsMapper.selectByShelterPetsId(shelterPetId) == null) {
            resp.setSuccess(false);
            resp.setMessage("Shelter Pet Not Found!");
        } else {
            try {
                int result = shelterPetsMapper.deleteByShelterPetsId(shelterPetId);
                if (result == 1) {
                    resp.setMessage("Delete Shelter Pet Success!");
                } else {
                    resp.setSuccess(false);
                    resp.setMessage("Delete Shelter Pet Failed!");
                }
            } catch (Exception e) {
                resp.setSuccess(false);
                resp.setMessage("Delete Shelter Pet Failed!");
            }
        }
        return resp;
    }

    public CommonResp<ListResp<ShelterPetsFill>> getShelterAdoptionPetList(Integer shelterId, Integer curPageNum, Integer pageSize) {
        CommonResp<ListResp<ShelterPetsFill>> resp = new CommonResp<>();
        Integer startRow = (curPageNum - 1) * pageSize;
        ListResp<ShelterPetsFill> listResp = new ListResp<>();
        List<ShelterPets> shelterPetsList = shelterPetsMapper.selectByShelterId(shelterId, startRow, pageSize);
        List<ShelterPetsFill> shelterPetsFillList = new ArrayList<>();
        for (ShelterPets shelterPets : shelterPetsList) {
            ShelterPetsFill shelterPetsFill = new ShelterPetsFill();
            shelterPetsFill.setShelterPetsId(shelterPets.getShelterPetsId());
            shelterPetsFill.setShelterId(shelterPets.getShelterId());
            shelterPetsFill.setPet(petMapper.selectByPetId(shelterPets.getPetId()));
            shelterPetsFill.setStatus(shelterPets.getStatus());
            shelterPetsFill.setCreateTime(shelterPets.getCreateTime());
            shelterPetsFillList.add(shelterPetsFill);
        }
        listResp.setList(shelterPetsFillList);
        listResp.setCurPageNum(curPageNum);
        listResp.setPageSize(pageSize);
        listResp.setTotalPageNum((int) Math.ceil((float)countShelterAdoptionPet(shelterId)/(float) pageSize));
        resp.setContent(listResp);
        resp.setMessage("Get Shelter Adoption Pet List Success!");
        return resp;
    }


    public CommonResp<String> changeShelterAdoptionPetStatus(ShelterPets shelterPets) {
        CommonResp<String> resp = new CommonResp<>();
        if (shelterPetsMapper.selectByShelterPetsId(shelterPets.getShelterPetsId()) == null) {
            resp.setMessage("No record found");
            resp.setSuccess(false);
            return resp;
        }
        int result = shelterPetsMapper.updateByShelterPetsId(shelterPets, shelterPets.getShelterPetsId());
        if (result == 0) {
            resp.setMessage("Update failed");
            resp.setSuccess(false);
        }
        else {
            resp.setMessage("Update success");
        }
        return resp;
    }

    public Integer getAllPetAdoptionCount() {
        return shelterPetsMapper.countShelterPetsId();
    }

    public int countShelterAdoptionPet(Integer shelterId) {
        return shelterPetsMapper.countByShelterId(shelterId);
    }
}
