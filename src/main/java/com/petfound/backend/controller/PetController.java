package com.petfound.backend.controller;

import com.petfound.backend.Entity.Pet.Pet;
import com.petfound.backend.Entity.Pet.PetFilter;
import com.petfound.backend.Service.PetService;
import com.petfound.backend.req.ListFilterReq;
import com.petfound.backend.resp.CommonResp;
import com.petfound.backend.resp.ListResp;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class PetController {
    @Resource
    private PetService petService;

    @PostMapping("/api/getPet")
    public CommonResp<Pet> getPet(@RequestBody Pet pet) {
        return petService.getPetByPetId(pet.getPetId());
    }

    @PostMapping("/api/insertPet")
    public CommonResp<Integer> insertPet(@RequestBody Pet pet) {
        return petService.insertPet(pet);
    }

    @PostMapping("/api/updatePet")
    public CommonResp<String> updatePet(@RequestBody Pet pet) {
        return petService.updatePet(pet);
    }

    @PostMapping("/api/deletePet")
    public CommonResp<String> deletePet(@RequestBody Pet pet) {
        return petService.deletePet(pet.getPetId());
    }

    @PostMapping("/api/getPetListByFilter")
    public CommonResp<ListResp<Pet>> getPetListByFilter(@RequestBody ListFilterReq<PetFilter> req) {
        return petService.getPetListByFilter(req);
    }
}
