package com.petfound.backend.controller;

import com.petfound.backend.Service.PetFoundService;
import com.petfound.backend.Service.PetSearchService;
import com.petfound.backend.Service.ShelterPetService;
import com.petfound.backend.resp.CommonResp;
import com.petfound.backend.resp.WebsiteStatisticsResp;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class Controller {
    @Resource
    private PetSearchService petSearchService;

    @Resource
    private PetFoundService petFoundService;

    @Resource
    private ShelterPetService shelterPetService;

    @GetMapping("/api/connection")
    public CommonResp<String> getHello() {
        CommonResp<String> resp = new CommonResp<>();
        resp.setMessage("Connection successful");
        return resp;
    }

    @GetMapping("/api/getWebsiteStatistics")
    public CommonResp<WebsiteStatisticsResp> getWebsiteStatistics() {
        CommonResp<WebsiteStatisticsResp> resp = new CommonResp<>();
        WebsiteStatisticsResp websiteStatisticsResp = new WebsiteStatisticsResp();
        try {
            websiteStatisticsResp.setTotalPetSearchNum(petSearchService.getAllSearchNumber());
            websiteStatisticsResp.setTotalPetFoundNum(petFoundService.getAllFoundNumber());
            websiteStatisticsResp.setTotalPetAdoptingNum(shelterPetService.getAllPetAdoptionCount());
            resp.setContent(websiteStatisticsResp);
            resp.setMessage("Get Website Statistics Success!");
        }catch (Exception e) {
            resp.setSuccess(false);
            resp.setMessage("Get Website Statistics Failed!");
        }
        return resp;
    }

}
