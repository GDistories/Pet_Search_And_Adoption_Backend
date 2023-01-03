package com.petfound.backend.controller;

import com.petfound.backend.Entity.PetFound.PetFound;
import com.petfound.backend.Entity.PetFound.PetFoundFill;
import com.petfound.backend.Service.PetFoundService;
import com.petfound.backend.req.PageAndShelterIdReq;
import com.petfound.backend.req.PageAndUsernameReq;
import com.petfound.backend.req.PetFoundListFilterReq;
import com.petfound.backend.resp.CommonResp;
import com.petfound.backend.resp.PetFoundListResp;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
public class PetFoundController {

    @Resource
    private PetFoundService petFoundService;

    @PostMapping("/api/addPetFound")
    public CommonResp<Integer> addPetFound(@RequestBody PetFound petFound) {
        return petFoundService.addPetFound(petFound);
    }

    @PostMapping("/api/updatePetFound")
    public CommonResp<String> updatePetFound(@RequestBody PetFound petFound) {
        return petFoundService.updatePetFound(petFound);
    }

    @PostMapping("/api/deletePetFound")
    public CommonResp<String> deletePetFound(@RequestBody PetFound petFound) {
        Integer petFoundId = petFound.getPetFoundId();
        return petFoundService.deletePetFound(petFoundId);
    }

    @PostMapping("/api/getPetFoundByPetFoundId")
    public CommonResp<PetFoundFill> getPetFoundByPetFoundId(@RequestBody PetFound petFound) {
        Integer petFoundId = petFound.getPetFoundId();
        return petFoundService.getPetFoundByPetFoundId(petFoundId);
    }

    @PostMapping("/api/getFoundPetListByUsername")
    public CommonResp<PetFoundListResp> getFoundByUsername(@RequestBody PageAndUsernameReq req) {
        String username = req.getUsername();
        Integer curPageNum = req.getCurPageNum();
        Integer pageSize = req.getPageSize();
        return petFoundService.getFoundPetListByUsername(username, curPageNum, pageSize);
    }

    @PostMapping("/api/getFoundPetListByShelterId")
    public CommonResp<PetFoundListResp> getFoundPetListByShelterId(@RequestBody PageAndShelterIdReq req) {
        Integer shelterId = req.getShelterId();
        Integer curPageNum = req.getCurPageNum();
        Integer pageSize = req.getPageSize();
        return petFoundService.getFoundPetListByShelterId(shelterId, curPageNum, pageSize);
    }


    @PostMapping("/api/getFoundPetListByFilter")
    public CommonResp<PetFoundListResp> getPetListByFilter(@RequestBody PetFoundListFilterReq petFoundListFilterReq) {
        return petFoundService.getPetListByFilter(petFoundListFilterReq);
    }
}
