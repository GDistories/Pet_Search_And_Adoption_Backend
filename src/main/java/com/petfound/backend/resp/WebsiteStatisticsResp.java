package com.petfound.backend.resp;

import lombok.Data;

@Data
public class WebsiteStatisticsResp {

    private Integer totalPetSearchNum;

    private Integer totalPetFoundNum;

    private Integer totalPetAdoptingNum;
}
