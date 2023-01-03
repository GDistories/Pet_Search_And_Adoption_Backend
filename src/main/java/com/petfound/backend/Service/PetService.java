package com.petfound.backend.Service;

import com.petfound.backend.Entity.Pet.Pet;
import com.petfound.backend.Entity.Pet.PetFilter;
import com.petfound.backend.Mapper.PetMapper;
import com.petfound.backend.Mapper.ShelterPetsMapper;
import com.petfound.backend.req.ListFilterReq;
import com.petfound.backend.resp.CommonResp;
import com.petfound.backend.resp.ListResp;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PetService {
    @Resource
    private PetMapper petMapper;
    @Resource
    private ShelterPetsMapper shelterPetsMapper;

    @Resource
    private PetFoundService petFoundService;

    @Resource
    private PetSearchService petSearchService;

    public CommonResp<Pet> getPetByPetId(Integer petId) {
        CommonResp<Pet> resp = new CommonResp<Pet>();
        Pet pet = petMapper.selectByPetId(petId);
        if (pet == null) {
            resp.setSuccess(false);
            resp.setMessage("Pet Not Found!");
        } else {
            resp.setMessage("Get Pet Success!");
            resp.setContent(pet);
        }
        return resp;
    }

    public CommonResp<Integer> insertPet(Pet pet) {
        CommonResp<Integer> resp = new CommonResp<>();
        try {
            int result = petMapper.insertSelective(pet);
            Integer petId = pet.getPetId();
            if (result == 1) {
                resp.setMessage("Insert Pet Success!");
                resp.setContent(petId);
            } else {
                resp.setSuccess(false);
                resp.setMessage("Insert Pet Failed!");
            }
        } catch (Exception e) {
            resp.setSuccess(false);
            resp.setMessage("Insert Pet Failed!");
        }
        return resp;
    }

    public CommonResp<String> updatePet(Pet pet) {
        CommonResp<String> resp = new CommonResp<String>();
        if (petMapper.selectByPetId(pet.getPetId()) == null) {
            resp.setSuccess(false);
            resp.setMessage("Pet Not Found!");
        } else {
            try {
                int result = petMapper.updateByPetId(pet, pet.getPetId());
                if (result == 1) {
                    resp.setMessage("Update Pet Success!");
                } else {
                    resp.setSuccess(false);
                    resp.setMessage("Update Pet Failed!");
                }
            } catch (Exception e) {
                resp.setSuccess(false);
                resp.setMessage("Update Pet Failed!");
            }
        }
        return resp;
    }

    public CommonResp<String> deletePet(Integer petId) {
        CommonResp<String> resp = new CommonResp<String>();
        if (petMapper.selectByPetId(petId) == null) {
            resp.setSuccess(false);
            resp.setMessage("Pet Not Found!");
        } else {
            try {
                shelterPetsMapper.deleteByPetId(petId);
                petFoundService.deletePetFoundByPetId(petId);
                petSearchService.deletePetSearchByPetId(petId);

                int result = petMapper.deleteByPetId(petId);
                if (result == 1) {
                    resp.setMessage("Delete Pet Success!");
                } else {
                    resp.setSuccess(false);
                    resp.setMessage("Delete Pet Failed!");
                }
            } catch (Exception e) {
                resp.setSuccess(false);
                resp.setMessage("Delete Pet Failed!");
            }
        }
        return resp;
    }

    public CommonResp<ListResp<Pet>> getPetListByFilter(ListFilterReq<PetFilter> req) {
        Integer curPageNum = req.getCurPageNum();
        Integer pageSize = req.getPageSize();
        PetFilter filter = req.getFilter();

        CommonResp<ListResp<Pet>> resp = new CommonResp<>();
        Integer startRow = (curPageNum - 1) * pageSize;
        ListResp<Pet> listResp = new ListResp<>();
        List<Pet> petList = petMapper.selectPetByFilter(startRow, pageSize, filter);
        listResp.setCurPageNum(curPageNum);
        listResp.setPageSize(pageSize);
        listResp.setTotalPageNum((int) Math.ceil((double) getPetCountByFilter(filter) / (double) pageSize));
        listResp.setList(petList);
        resp.setContent(listResp);
        resp.setMessage("Get Pet List Success!");
        return resp;
    }

    public int getPetCountByFilter(PetFilter filter) {
        return petMapper.countPetByFilter(filter);
    }

}
