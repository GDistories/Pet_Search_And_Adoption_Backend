package com.petfound.backend.Service;

import com.petfound.backend.Entity.PetSearch.PetSearchFilter;
import com.petfound.backend.Entity.PetSearch.PetSearch;
import com.petfound.backend.Entity.PetSearch.PetSearchFill;
import com.petfound.backend.Mapper.PetMapper;
import com.petfound.backend.Mapper.PetSearchMapper;
import com.petfound.backend.Mapper.UserMapper;
import com.petfound.backend.req.PetSearchListFilterReq;
import com.petfound.backend.resp.CommonResp;
import com.petfound.backend.resp.PetSearchListResp;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class PetSearchService {

    @Resource
    private PetSearchMapper petSearchMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private PetMapper petMapper;

    public CommonResp<Integer> addPetSearch(PetSearch petSearch) {
        CommonResp<Integer> resp = new CommonResp<>();
        try {
            int result = petSearchMapper.insertSelective(petSearch);
            Integer petSearchId = petSearch.getPetSearchId();
            if (result == 1) {
                resp.setMessage("Add Pet Search Success!");
                resp.setContent(petSearchId);
            } else {
                resp.setSuccess(false);
                resp.setMessage("Add Pet Search Failed!");
            }
        } catch (Exception e) {
            resp.setSuccess(false);
            resp.setMessage("Add Pet Search Failed!");
        }
        return resp;
    }

    public CommonResp<String> updatePetSearch(PetSearch petSearch) {
        CommonResp<String> resp = new CommonResp<>();
        if (petSearchMapper.selectAllByPetSearchId(petSearch.getPetSearchId()) == null) {
            resp.setSuccess(false);
            resp.setMessage("Pet Search Not Found!");
        } else {
            try {
                int result = petSearchMapper.updateByPetSearchId(petSearch, petSearch.getPetSearchId());
                if (result == 1) {
                    resp.setMessage("Update Pet Search Success!");
                } else {
                    resp.setSuccess(false);
                    resp.setMessage("Update Pet Search Failed!");
                }
            } catch (Exception e) {
                resp.setSuccess(false);
                resp.setMessage("Update Pet Search Failed!");
            }
        }
        return resp;
    }

    public CommonResp<String> deletePetSearch(Integer petSearchId) {
        CommonResp<String> resp = new CommonResp<>();
        if (petSearchMapper.selectAllByPetSearchId(petSearchId) == null) {
            resp.setSuccess(false);
            resp.setMessage("Pet Search Not Found!");
        } else {
            try {
                int result = petSearchMapper.deleteByPetSearchId(petSearchId);
                if (result == 1) {
                    resp.setMessage("Delete Pet Search Success!");
                } else {
                    resp.setSuccess(false);
                    resp.setMessage("Delete Pet Search Failed!");
                }
            } catch (Exception e) {
                resp.setSuccess(false);
                resp.setMessage("Delete Pet Search Failed!");
            }
        }
        return resp;
    }

    public CommonResp<PetSearchFill> getPetSearchByPetSearchId(Integer petSearchId){
        CommonResp<PetSearchFill> resp = new CommonResp<>();
        PetSearch petSearch = petSearchMapper.selectAllByPetSearchId(petSearchId);
        if (petSearch == null) {
            resp.setSuccess(false);
            resp.setMessage("Pet Search Not Found!");
        } else {
            PetSearchFill petSearchFill = new PetSearchFill();
            petSearchFill.setPetSearchId(petSearch.getPetSearchId());
            petSearchFill.setPet(petMapper.selectByPetId(petSearch.getPetId()));
            petSearchFill.setUser(userMapper.selectByUsername(petSearch.getUsername()));
            petSearchFill.setCountyId(petSearch.getCountyId());
            petSearchFill.setCreateTime(petSearch.getCreateTime());
            resp.setMessage("Get Pet Search Success!");
            resp.setContent(petSearchFill);
        }
        return resp;
    }

    public CommonResp<PetSearchListResp> getPetSearchListByUsername(String username, Integer curPageNum, Integer pageSize) {
        CommonResp<PetSearchListResp> resp = new CommonResp<>();
        Integer startRow = (curPageNum - 1) * pageSize;
        PetSearchListResp petSearchListResp = new PetSearchListResp();
        List<PetSearch> petSearchList = petSearchMapper.selectByUsername(username, startRow, pageSize);
        List<PetSearchFill> petSearchFillList = new ArrayList<>();
        for (PetSearch petSearch : petSearchList) {
            PetSearchFill petSearchFill = new PetSearchFill();
            petSearchFill.setPetSearchId(petSearch.getPetSearchId());
            petSearchFill.setPet(petMapper.selectByPetId(petSearch.getPetId()));
            petSearchFill.setUser(userMapper.selectByUsername(petSearch.getUsername()));
            petSearchFill.setCountyId(petSearch.getCountyId());
            petSearchFill.setCreateTime(petSearch.getCreateTime());
            petSearchFillList.add(petSearchFill);
        }
        petSearchListResp.setPetList(petSearchFillList);
        petSearchListResp.setCurPageNum(curPageNum);
        petSearchListResp.setTotalPageNum((int) Math.ceil((float)getSearchNumberByUsername(username)/(float) pageSize));
        petSearchListResp.setPageSize(pageSize);
        resp.setContent(petSearchListResp);
        resp.setMessage("Get Pet Search List Success!");
        return resp;
    }

    public CommonResp<PetSearchListResp> getPetList(Integer curPageNum, Integer pageSize) {
        CommonResp<PetSearchListResp> resp = new CommonResp<>();
        Integer startRow = (curPageNum - 1) * pageSize;
        PetSearchListResp petSearchListResp = new PetSearchListResp();
        List<PetSearch> petSearches = petSearchMapper.selectByPage(startRow, pageSize);
        List<PetSearchFill> petSearchFills = new ArrayList<>();

        for(PetSearch petSearch: petSearches)
        {
            PetSearchFill petSearchFill = new PetSearchFill();
            petSearchFill.setPetSearchId(petSearch.getPetSearchId());
            petSearchFill.setPet(petMapper.selectByPetId(petSearch.getPetId()));
            petSearchFill.setUser(userMapper.selectByUsername(petSearch.getUsername()));
            petSearchFill.setCountyId(petSearch.getCountyId());
            petSearchFill.setCreateTime(petSearch.getCreateTime());
            petSearchFills.add(petSearchFill);
        }

        petSearchListResp.setPetList(petSearchFills);
        petSearchListResp.setCurPageNum(curPageNum);
        petSearchListResp.setTotalPageNum((int) Math.ceil((float)getAllSearchNumber()/(float) pageSize));
        petSearchListResp.setPageSize(pageSize);
        resp.setContent(petSearchListResp);
        resp.setMessage("Get Pet Search List Success!");
        return resp;
    }

    public CommonResp<PetSearchListResp> getPetListByFilter(PetSearchListFilterReq petSearchListFilterReq) {
        Integer curPageNum = petSearchListFilterReq.getCurPageNum();
        Integer pageSize = petSearchListFilterReq.getPageSize();
        PetSearchFilter petSearchFilter = petSearchListFilterReq.getPetSearchFilter();

        CommonResp<PetSearchListResp> resp = new CommonResp<>();
        Integer startRow = (curPageNum - 1) * pageSize;
        PetSearchListResp petSearchListResp = new PetSearchListResp();
        List<PetSearch> petSearches = petSearchMapper.selectByFilter(startRow, pageSize, petSearchFilter);
        List<PetSearchFill> petSearchFills = new ArrayList<>();
        for(PetSearch petSearch: petSearches)
        {
            PetSearchFill petSearchFill = new PetSearchFill();
            petSearchFill.setPetSearchId(petSearch.getPetSearchId());
            petSearchFill.setPet(petMapper.selectByPetId(petSearch.getPetId()));
            petSearchFill.setUser(userMapper.selectByUsername(petSearch.getUsername()));
            petSearchFill.setCountyId(petSearch.getCountyId());
            petSearchFill.setCreateTime(petSearch.getCreateTime());
            petSearchFills.add(petSearchFill);
        }

        petSearchListResp.setPetList(petSearchFills);
        petSearchListResp.setCurPageNum(curPageNum);
        petSearchListResp.setTotalPageNum((int) Math.ceil((float)getSearchNumberByFilter(petSearchFilter)/(float) pageSize));
        petSearchListResp.setPageSize(pageSize);
        resp.setContent(petSearchListResp);
        resp.setMessage("Get Pet Search List Success!");
        return resp;
    }

    public int getAllSearchNumber() {
        return petSearchMapper.countPetSearchId();
    }

    public int getSearchNumberByFilter(PetSearchFilter petSearchFilter) {
        return petSearchMapper.countPetSearchIdByFilter(petSearchFilter);
    }

    public int getSearchNumberByUsername(String username) {
        return petSearchMapper.countPetSearchIdByUsername(username);
    }

    public void deletePetSearchByPetId(Integer petId) {
        petSearchMapper.deleteByPetId(petId);
    }

    public void deletePetSearchByUsername(String username) {
        petSearchMapper.deleteByUsername(username);
    }


}
