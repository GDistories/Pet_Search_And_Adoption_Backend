package com.petfound.backend.controller;

import com.petfound.backend.Entity.ShelterPets.ShelterPets;
import com.petfound.backend.Entity.ShelterPets.ShelterPetsFill;
import com.petfound.backend.Service.ShelterPetService;
import com.petfound.backend.req.PageAndShelterIdReq;
import com.petfound.backend.resp.CommonResp;
import com.petfound.backend.resp.ListResp;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class ShelterPetController {
    @Resource
    ShelterPetService shelterPetService;

    @PostMapping("/api/addShelterPet")
    public CommonResp<Integer> addShelterPet(@RequestBody ShelterPets shelterPets) {
        return shelterPetService.addShelterPet(shelterPets);
    }

    @PostMapping("/api/deleteShelterPet")
    public CommonResp<String> deleteShelterPet(@RequestBody ShelterPets shelterPets) {
        Integer shelterPetId = shelterPets.getShelterPetsId();
        return shelterPetService.deleteShelterPet(shelterPetId);
    }

    @PostMapping("/api/getShelterAdoptionPetList")
    public CommonResp<ListResp<ShelterPetsFill>> getShelterAdoptionPetList(@RequestBody PageAndShelterIdReq pageAndShelterIdReq) {
        Integer shelterId = pageAndShelterIdReq.getShelterId();
        Integer curPageNum = pageAndShelterIdReq.getCurPageNum();
        Integer pageSize = pageAndShelterIdReq.getPageSize();
        return shelterPetService.getShelterAdoptionPetList(shelterId, curPageNum, pageSize);
    }

    @PostMapping("/api/changeShelterAdoptionPetStatus")
    public CommonResp<String> changeShelterAdoptionPetStatus(@RequestBody ShelterPets shelterPets) {
        return shelterPetService.changeShelterAdoptionPetStatus(shelterPets);
    }
}
