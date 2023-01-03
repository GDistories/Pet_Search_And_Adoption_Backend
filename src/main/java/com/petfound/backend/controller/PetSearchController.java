package com.petfound.backend.controller;

import com.petfound.backend.Entity.PetSearch.PetSearch;
import com.petfound.backend.Entity.PetSearch.PetSearchFill;
import com.petfound.backend.Service.PetSearchService;
import com.petfound.backend.req.PageAndUsernameReq;
import com.petfound.backend.req.PetSearchListFilterReq;
import com.petfound.backend.resp.CommonResp;
import com.petfound.backend.resp.PetSearchListResp;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class PetSearchController {

    @Resource
    private PetSearchService petSearchService;

    @PostMapping("/api/addPetSearch")
    public CommonResp<Integer> addPetSearch(@RequestBody PetSearch petSearch) {
        return petSearchService.addPetSearch(petSearch);
    }

    @PostMapping("/api/updatePetSearch")
    public CommonResp<String> updatePetSearch(@RequestBody PetSearch petSearch) {
        return petSearchService.updatePetSearch(petSearch);
    }

    @PostMapping("/api/deletePetSearch")
    public CommonResp<String> deletePetSearch(@RequestBody PetSearch petSearch) {
        Integer petSearchId = petSearch.getPetSearchId();
        return petSearchService.deletePetSearch(petSearchId);
    }

    @PostMapping("/api/getPetSearchByPetSearchId")
    public CommonResp<PetSearchFill> getPetSearchByPetSearchId(@RequestBody PetSearch petSearch) {
        Integer petSearchId = petSearch.getPetSearchId();
        return petSearchService.getPetSearchByPetSearchId(petSearchId);
    }

    @PostMapping("/api/getPetSearchListByUsername")
    public CommonResp<PetSearchListResp> getPetSearchListByUsername(@RequestBody PageAndUsernameReq req) {
        String username = req.getUsername();
        Integer curPageNum = req.getCurPageNum();
        Integer pageSize = req.getPageSize();
        return petSearchService.getPetSearchListByUsername(username, curPageNum, pageSize);
    }

    @PostMapping("/api/getSearchPetListByFilter")
    public CommonResp<PetSearchListResp> getPetListByFilter(@RequestBody PetSearchListFilterReq petSearchListFilterReq) {
        return petSearchService.getPetListByFilter(petSearchListFilterReq);
    }
}
