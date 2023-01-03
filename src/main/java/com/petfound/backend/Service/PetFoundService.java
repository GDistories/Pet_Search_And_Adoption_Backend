package com.petfound.backend.Service;

import com.petfound.backend.Entity.PetFound.PetFound;
import com.petfound.backend.Entity.PetFound.PetFoundFill;
import com.petfound.backend.Entity.PetFound.PetFoundFilter;
import com.petfound.backend.Mapper.PetFoundMapper;
import com.petfound.backend.Mapper.PetMapper;
import com.petfound.backend.Mapper.UserMapper;
import com.petfound.backend.req.PetFoundListFilterReq;
import com.petfound.backend.resp.CommonResp;
import com.petfound.backend.resp.PetFoundListResp;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class PetFoundService {

    @Resource
    private PetFoundMapper petFoundMapper;
    @Resource
    private PetMapper petMapper;
    @Resource
    private UserMapper userMapper;

    public CommonResp<Integer> addPetFound(PetFound petFound) {
        CommonResp<Integer> resp = new CommonResp<>();
        try {
            int result = petFoundMapper.insertSelective(petFound);
            Integer petFoundId = petFound.getPetFoundId();
            if (result == 1) {
                resp.setMessage("Add Pet Found Success!");
                resp.setContent(petFoundId);
            } else {
                resp.setSuccess(false);
                resp.setMessage("Add Pet Found Failed!");
            }
        } catch (Exception e) {
            resp.setSuccess(false);
            resp.setMessage("Add Pet Found Failed!");
        }
        return resp;
    }

    public CommonResp<String> updatePetFound(PetFound petFound) {
        CommonResp<String> resp = new CommonResp<>();
        if (petFoundMapper.selectAllByPetFoundId(petFound.getPetFoundId()) == null) {
            resp.setSuccess(false);
            resp.setMessage("Pet Found Not Found!");
        } else {
            try {
                int result = petFoundMapper.updateByPetFoundId(petFound, petFound.getPetFoundId());
                if (result == 1) {
                    resp.setMessage("Update Pet Found Success!");
                } else {
                    resp.setSuccess(false);
                    resp.setMessage("Update Pet Found Failed!");
                }
            } catch (Exception e) {
                resp.setSuccess(false);
                resp.setMessage("Update Pet Found Failed!");
            }
        }
        return resp;
    }

    public CommonResp<String> deletePetFound(Integer petFoundId) {
        CommonResp<String> resp = new CommonResp<>();
        if (petFoundMapper.selectAllByPetFoundId(petFoundId) == null) {
            resp.setSuccess(false);
            resp.setMessage("Pet Found Not Found!");
        } else {
            try {
                int result = petFoundMapper.deleteByPetFoundId(petFoundId);
                if (result == 1) {
                    resp.setMessage("Delete Pet Found Success!");
                } else {
                    resp.setSuccess(false);
                    resp.setMessage("Delete Pet Found Failed!");
                }
            } catch (Exception e) {
                resp.setSuccess(false);
                resp.setMessage("Delete Pet Found Failed!");
            }
        }
        return resp;
    }

    public CommonResp<PetFoundFill> getPetFoundByPetFoundId(Integer petFoundId){
        CommonResp<PetFoundFill> resp = new CommonResp<>();
        PetFound petFound = petFoundMapper.selectAllByPetFoundId(petFoundId);
        PetFoundFill petFoundFill = new PetFoundFill();
        if (petFound == null) {
            resp.setSuccess(false);
            resp.setMessage("Pet Found Not Found!");
        } else {
            petFoundFill.setPetFoundId(petFound.getPetFoundId());
            petFoundFill.setPet(petMapper.selectByPetId(petFound.getPetId()));
            petFoundFill.setUser(userMapper.selectByUsername(petFound.getUsername()));
            petFoundFill.setCountyId(petFound.getCountyId());
            petFoundFill.setCreateTime(petFound.getCreateTime());
            petFoundFill.setStatus(petFound.getStatus());
            petFoundFill.setShelterId(petFound.getShelterId());
            resp.setMessage("Get Pet Found Success!");
            resp.setContent(petFoundFill);
        }
        return resp;
    }

    public CommonResp<PetFoundListResp> getFoundPetListByUsername(String username, Integer curPageNum, Integer pageSize) {
        CommonResp<PetFoundListResp> resp = new CommonResp<>();
        Integer startRow = (curPageNum - 1) * pageSize;
        PetFoundListResp petFoundListResp = new PetFoundListResp();
        List<PetFound> petFoundList = petFoundMapper.selectByUsername(username, startRow, pageSize);
        List<PetFoundFill> petFoundFillList = new ArrayList<>();
        for (PetFound petFound : petFoundList) {
            PetFoundFill petFoundFill = new PetFoundFill();
            petFoundFill.setPetFoundId(petFound.getPetFoundId());
            petFoundFill.setPet(petMapper.selectByPetId(petFound.getPetId()));
            petFoundFill.setUser(userMapper.selectByUsername(petFound.getUsername()));
            petFoundFill.setCountyId(petFound.getCountyId());
            petFoundFill.setCreateTime(petFound.getCreateTime());
            petFoundFill.setStatus(petFound.getStatus());
            petFoundFill.setShelterId(petFound.getShelterId());
            petFoundFillList.add(petFoundFill);
        }
        petFoundListResp.setPetList(petFoundFillList);
        petFoundListResp.setCurPageNum(curPageNum);
        petFoundListResp.setTotalPageNum((int) Math.ceil((float) getFoundNumberByUsername(username) / (float) pageSize));
        petFoundListResp.setPageSize(pageSize);
        resp.setContent(petFoundListResp);
        resp.setMessage("Get Pet Found List Success!");

        return resp;
    }

    public CommonResp<PetFoundListResp> getFoundPetListByShelterId(Integer shelterId, Integer curPageNum, Integer pageSize) {
        CommonResp<PetFoundListResp> resp = new CommonResp<>();
        Integer startRow = (curPageNum - 1) * pageSize;
        PetFoundListResp petFoundListResp = new PetFoundListResp();
        List<PetFound> petFoundList = petFoundMapper.selectByShelterId(shelterId, startRow, pageSize);
        List<PetFoundFill> petFoundFillList = new ArrayList<>();
        for (PetFound petFound : petFoundList) {
            PetFoundFill petFoundFill = new PetFoundFill();
            petFoundFill.setPetFoundId(petFound.getPetFoundId());
            petFoundFill.setPet(petMapper.selectByPetId(petFound.getPetId()));
            petFoundFill.setUser(userMapper.selectByUsername(petFound.getUsername()));
            petFoundFill.setCountyId(petFound.getCountyId());
            petFoundFill.setCreateTime(petFound.getCreateTime());
            petFoundFill.setStatus(petFound.getStatus());
            petFoundFill.setShelterId(petFound.getShelterId());
            petFoundFillList.add(petFoundFill);
        }
        petFoundListResp.setPetList(petFoundFillList);
        petFoundListResp.setCurPageNum(curPageNum);
        petFoundListResp.setTotalPageNum((int) Math.ceil((float) getFoundNumberByShelterId(shelterId) / (float) pageSize));
        petFoundListResp.setPageSize(pageSize);
        resp.setContent(petFoundListResp);
        resp.setMessage("Get Pet Found List Success!");
        return resp;
    }




    public CommonResp<PetFoundListResp> getPetListByFilter(PetFoundListFilterReq petFoundListFilterReq) {
        Integer curPageNum = petFoundListFilterReq.getCurPageNum();
        Integer pageSize = petFoundListFilterReq.getPageSize();
        PetFoundFilter petFoundFilter = petFoundListFilterReq.getPetFoundFilter();

        CommonResp<PetFoundListResp> resp = new CommonResp<>();
        Integer startRow = (curPageNum - 1) * pageSize;
        PetFoundListResp petFoundListResp = new PetFoundListResp();
        List<PetFound> petFounds = petFoundMapper.selectByFilter(startRow, pageSize, petFoundFilter);
        List<PetFoundFill> petFoundFills = new ArrayList<>();
        for (PetFound petFound : petFounds) {
            PetFoundFill petFoundFill = new PetFoundFill();
            petFoundFill.setPetFoundId(petFound.getPetFoundId());
            petFoundFill.setPet(petMapper.selectByPetId(petFound.getPetId()));
            petFoundFill.setUser(userMapper.selectByUsername(petFound.getUsername()));
            petFoundFill.setCountyId(petFound.getCountyId());
            petFoundFill.setCreateTime(petFound.getCreateTime());
            petFoundFill.setStatus(petFound.getStatus());
            petFoundFill.setShelterId(petFound.getShelterId());
            petFoundFills.add(petFoundFill);
        }

        petFoundListResp.setPetList(petFoundFills);
        petFoundListResp.setCurPageNum(curPageNum);
        petFoundListResp.setTotalPageNum((int) Math.ceil((float) getFoundNumberByFilter(petFoundFilter) / (float) pageSize));
        petFoundListResp.setPageSize(pageSize);
        resp.setContent(petFoundListResp);
        resp.setMessage("Get Pet Found List Success!");
        return resp;
    }

    public int getFoundNumberByFilter(PetFoundFilter petFoundFilter) {
        return petFoundMapper.countPetFoundIdByFilter(petFoundFilter);
    }

    public int getAllFoundNumber() {
        return petFoundMapper.countPetFoundId();
    }

    public int getFoundNumberByUsername(String username) {
        return petFoundMapper.countPetFoundIdByUsername(username);
    }

    public int getFoundNumberByShelterId(Integer shelterId) {
        return petFoundMapper.countPetFoundIdByShelterId(shelterId);
    }

    public void deletePetFoundByPetId(Integer petId) {
        petFoundMapper.deleteByPetId(petId);
    }

    public void deletePetFoundByUsername(String username) {
        petFoundMapper.deleteByUsername(username);
    }

    public void deletePetFoundByShelterId(Integer shelterId) {
        petFoundMapper.deleteByShelterId(shelterId);
    }

}
