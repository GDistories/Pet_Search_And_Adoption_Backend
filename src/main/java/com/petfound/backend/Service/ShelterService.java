package com.petfound.backend.Service;

import com.petfound.backend.Entity.Shelter.Shelter;
import com.petfound.backend.Entity.Shelter.ShelterFilter;
import com.petfound.backend.Mapper.ShelterPetsMapper;
import com.petfound.backend.Utils.JWTUtils;
import com.petfound.backend.req.ListFilterReq;
import com.petfound.backend.req.ResetPasswordReq;
import com.petfound.backend.req.SearchReq;
import com.petfound.backend.resp.CommonResp;
import com.petfound.backend.resp.ListResp;
import com.petfound.backend.resp.ShelterResp;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.petfound.backend.Mapper.ShelterMapper;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShelterService {

    @Resource
    private ShelterMapper shelterMapper;

    @Resource
    private ShelterPetsMapper shelterPetsMapper;

    @Resource
    private PetFoundService petFoundService;

    @Resource
    private UserService userService;

    @Value("${shelter.registerCode}")
    String registerCode;

    public CommonResp<ShelterResp> login(String username, String password) {
        CommonResp<ShelterResp> resp = new CommonResp<>();
        Shelter shelter = shelterMapper.selectByUsernameAndPassword(username, password);
        ShelterResp shelterResp = null;
        if (shelter != null) {
            shelterResp = new ShelterResp();
            BeanUtils.copyProperties(shelter, shelterResp);
        }
        if (shelterResp == null) {
            resp.setSuccess(false);
            resp.setMessage("Username or Password is Wrong!");
        } else {
            String token = JWTUtils.getToken(username);
            resp.setMessage(token);
            resp.setContent(shelterResp);
        }
        return resp;
    }

    public CommonResp<String> register(Shelter shelter) {
        CommonResp<String> resp = new CommonResp<>();

        // Check Shelter has permission to register
        if(!shelter.getRegisterCode().equals(registerCode)) {
            resp.setSuccess(false);
            resp.setMessage("Register Code is Wrong!");
            return resp;
        }

        if(userService.isUsernameExist(shelter.getUsername())) {
            resp.setSuccess(false);
            resp.setMessage("The username has been used!");
        }
        else if(getShelterByEmail(shelter.getEmail()) != null) {
            resp.setSuccess(false);
            resp.setMessage("The email has been used!");
        }
        else if(getShelterByPhone(shelter.getPhone()) != null) {
            resp.setSuccess(false);
            resp.setMessage("The phone has been used!");
        }
        else {
            try{
                Integer result = insertShelter(shelter);
                if(result == 1) {
                    resp.setMessage("Register Success!");
                }
                else {
                    resp.setSuccess(false);
                    resp.setMessage("Register Failed!");
                }
            }catch (Exception e)
            {
                resp.setSuccess(false);
                resp.setMessage("Register Failed!");
            }
        }
        return resp;
    }

    public CommonResp<String> update(Shelter shelter) {
        CommonResp<String> resp = new CommonResp<>();
        if (shelterMapper.selectByUsername(shelter.getUsername()) == null) {
            resp.setSuccess(false);
            resp.setMessage("The shelter does not exist!");
        }
        else {
            try {
                Integer result = updateShelterByUsername(shelter);
                if (result == 1) {
                    resp.setMessage("Update Success!");
                } else {
                    resp.setSuccess(false);
                    resp.setMessage("Update Failed!");
                }
            } catch (Exception e) {
                resp.setSuccess(false);
                resp.setMessage("Update Failed!");
            }
        }
        return resp;
    }

    public CommonResp<ShelterResp> getShelterByShelterId(Integer shelterId){
        CommonResp<ShelterResp> resp = new CommonResp<>();
        Shelter shelter = shelterMapper.selectByShelterId(shelterId);
        ShelterResp shelterResp = null;
        if (shelter != null) {
            shelterResp = new ShelterResp();
            BeanUtils.copyProperties(shelter, shelterResp);
        }
        if (shelterResp == null) {
            resp.setSuccess(false);
            resp.setMessage("Shelter does not exist!");
        } else {
            resp.setMessage("Get Shelter Success!");
            resp.setContent(shelterResp);
        }
        return resp;
    }

    public CommonResp<ShelterResp> getShelterByUsername(String username){
        CommonResp<ShelterResp> resp = new CommonResp<>();
        Shelter shelter = shelterMapper.selectByUsername(username);
        ShelterResp shelterResp = null;
        if (shelter != null) {
            shelterResp = new ShelterResp();
            BeanUtils.copyProperties(shelter, shelterResp);
        }
        if (shelterResp == null) {
            resp.setSuccess(false);
            resp.setMessage("Shelter does not exist!");
        } else {
            resp.setMessage("Get Shelter Success!");
            resp.setContent(shelterResp);
        }
        return resp;
    }



    public CommonResp<String> delete(String username) {
        CommonResp<String> resp = new CommonResp<>();
        if (shelterMapper.selectByUsername(username) == null) {
            resp.setSuccess(false);
            resp.setMessage("The shelter does not exist!");
        }
        else {
            try {
                shelterPetsMapper.deleteByShelterId(getShelterIdByUsername(username));
                petFoundService.deletePetFoundByShelterId(getShelterIdByUsername(username));

                Integer result = deleteShelterByUsername(username);
                if (result == 1) {
                    resp.setMessage("Delete Success!");
                } else {
                    resp.setSuccess(false);
                    resp.setMessage("Delete Failed!");
                }
            } catch (Exception e) {
                System.out.println(e);
                resp.setSuccess(false);
                resp.setMessage("Delete Failed!");
            }
        }
        return resp;
    }

    public CommonResp<ListResp<ShelterResp>> getShelterBySearch(SearchReq<String> searchReq) {
        Integer curPageNum = searchReq.getCurPageNum();
        Integer pageSize = searchReq.getPageSize();
        String search = searchReq.getSearch();

        CommonResp<ListResp<ShelterResp>> resp = new CommonResp<>();
        Integer startRow = (curPageNum - 1) * pageSize;
        ListResp<ShelterResp> listResp = new ListResp<>();
        List<Shelter> shelters = shelterMapper.selectShelterBySearch(startRow, pageSize, search);
        List<ShelterResp> shelterResps = new ArrayList<>();
        for (Shelter shelter : shelters) {
            ShelterResp shelterResp = new ShelterResp();
            BeanUtils.copyProperties(shelter, shelterResp);
            shelterResps.add(shelterResp);
        }

        listResp.setCurPageNum(curPageNum);
        listResp.setPageSize(pageSize);
        listResp.setTotalPageNum((int) Math.ceil((double) getShelterNumberBySearch(search) / (double) pageSize));
        listResp.setList(shelterResps);
        resp.setContent(listResp);
        resp.setMessage("Get Shelters Success!");
        return resp;
    }

    public CommonResp<ListResp<ShelterResp>> listFilter(ListFilterReq<ShelterFilter> req) {
        Integer curPageNum = req.getCurPageNum();
        Integer pageSize = req.getPageSize();
        ShelterFilter filter = req.getFilter();

        CommonResp<ListResp<ShelterResp>> resp = new CommonResp<>();
        Integer startRow = (curPageNum - 1) * pageSize;
        ListResp<ShelterResp> listResp = new ListResp<>();
        List<Shelter> shelters = shelterMapper.selectShelterByFilter(startRow, pageSize, filter);
        List<ShelterResp> shelterResps = new ArrayList<>();
        for (Shelter shelter : shelters) {
            ShelterResp shelterResp = new ShelterResp();
            BeanUtils.copyProperties(shelter, shelterResp);
            shelterResps.add(shelterResp);
        }

        listResp.setCurPageNum(curPageNum);
        listResp.setPageSize(pageSize);
        listResp.setTotalPageNum((int) Math.ceil((double) getShelterNumberByFilter(filter) / (double) pageSize));
        listResp.setList(shelterResps);
        resp.setContent(listResp);
        resp.setMessage("Get Shelter List Success!");
        return resp;
    }

    public CommonResp<String> resetPassword(ResetPasswordReq resetPasswordReq){
        String username = resetPasswordReq.getUsername();
        String newPassword = resetPasswordReq.getNewPassword();
        CommonResp<String> resp = new CommonResp<>();
        if (shelterMapper.selectByUsername(username) == null) {
            resp.setSuccess(false);
            resp.setMessage("The shelter does not exist!");
        }
        else {
            try {
                int result = shelterMapper.updatePasswordByUsername(newPassword, username);
                if (result == 1) {
                    resp.setMessage("Update Success!");
                } else {
                    resp.setSuccess(false);
                    resp.setMessage("Update Failed!");
                }
            } catch (Exception e) {
                resp.setSuccess(false);
                resp.setMessage("Update Failed!");
            }
        }
        return resp;
    }

    public int getShelterNumberByFilter(ShelterFilter filter) {
        return shelterMapper.countShelterByFilter(filter);
    }

    public int getShelterNumberBySearch(String search) {
        return shelterMapper.countShelterBySearch(search);
    }

    public Shelter getShelterByEmail(String email) {
        return shelterMapper.selectByEmail(email);
    }

    public Shelter getShelterByPhone(String phone) {
        return shelterMapper.selectByPhone(phone);
    }

    public Integer insertShelter(Shelter record) {
        return shelterMapper.insertSelective(record);
    }

    public Integer updateShelterByUsername(Shelter record) {
        return shelterMapper.updateByUsername(record, record.getUsername());
    }

    public Integer getShelterIdByUsername(String username) {
        return shelterMapper.selectShelterIdByUsername(username);
    }

    public Integer deleteShelterByUsername(String username) {
        return shelterMapper.deleteByUsername(username);
    }

    public Boolean isShelterExist(String username) {
        return shelterMapper.selectByUsername(username) != null;
    }
}

