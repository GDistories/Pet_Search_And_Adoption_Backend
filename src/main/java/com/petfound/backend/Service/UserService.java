package com.petfound.backend.Service;

import com.petfound.backend.Entity.User.UserFilter;
import com.petfound.backend.Mapper.ShelterMapper;
import com.petfound.backend.Utils.JWTUtils;
import com.petfound.backend.req.ListFilterReq;
import com.petfound.backend.req.ResetPasswordReq;
import com.petfound.backend.resp.CommonResp;
import com.petfound.backend.resp.ListResp;
import com.petfound.backend.resp.UserResp;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.petfound.backend.Mapper.UserMapper;
import com.petfound.backend.Entity.User.User;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private PetFoundService petFoundService;
    @Resource
    private PetSearchService petSearchService;
    @Resource
    private ShelterMapper shelterMapper;
    @Resource
    private AdminService adminService;

    public CommonResp<UserResp> login(String username, String password) {
        CommonResp<UserResp> resp = new CommonResp<>();
        User user = userMapper.selectByUsernameAndPassword(username, password);
        UserResp userResp = null;
        if (user != null) {
            userResp = new UserResp();
            BeanUtils.copyProperties(user, userResp);
        }
        if (userResp == null) {
            resp.setSuccess(false);
            resp.setMessage("Username or Password is Wrong!");
        } else {
            String token = JWTUtils.getToken(username);
            resp.setMessage(token);
            resp.setContent(userResp);
        }
        return resp;
    }

    public CommonResp<String> register(User user) {
        CommonResp<String> resp = new CommonResp<>();
        if(isUsernameExist(user.getUsername())) {
            resp.setSuccess(false);
            resp.setMessage("The username has been used!");
        }
        else if(getUserByEmail(user.getEmail()) != null) {
            resp.setSuccess(false);
            resp.setMessage("The email has been used!");
        }
        else if(getUserByPhone(user.getPhone()) != null) {
            resp.setSuccess(false);
            resp.setMessage("The phone has been used!");
        }
        else {
            try{
                Integer result = insertUser(user);
                if(result == 1) {
                    resp.setMessage("Register Success!");
                }
                else {
                    resp.setSuccess(false);
                    resp.setMessage("Register Failed!");
                }
            }catch (Exception e)
            {
                System.out.println(e);
                resp.setSuccess(false);
                resp.setMessage("Register Failed!");
            }
        }
        return resp;
    }

    public CommonResp<String> update(User user) {
        CommonResp<String> resp = new CommonResp<>();
        if(getUserByUsername(user.getUsername()) == null) {
            resp.setSuccess(false);
            resp.setMessage("The user is not exist!");
        }
        else {
            try{
                Integer result = updateUserByUsername(user);
                if(result == 1) {
                    resp.setMessage("Update User Success!");
                }
                else {
                    resp.setSuccess(false);
                    resp.setMessage("Update User Failed!");
                }
            }catch (Exception e)
            {
                System.out.println(e);
                resp.setSuccess(false);
                resp.setMessage("Update User Failed!");
            }
        }
        return resp;
    }

    public CommonResp<String> resetPassword (ResetPasswordReq resetPasswordReq) {
        String username = resetPasswordReq.getUsername();
        String newPassword = resetPasswordReq.getNewPassword();

        CommonResp<String> resp = new CommonResp<>();
        if(getUserByUsername(username) == null) {
            resp.setSuccess(false);
            resp.setMessage("The user is not exist!");
        }
        else {
            try{
                int result = userMapper.updatePasswordByUsername(newPassword, username);
                if(result == 1) {
                    resp.setMessage("Update User Success!");
                }
                else {
                    System.out.println(result);
                    resp.setSuccess(false);
                    resp.setMessage("Update User Failed!");
                }
            }catch (Exception e)
            {
                System.out.println(e);
                resp.setSuccess(false);
                resp.setMessage("Update User Failed!");
            }
        }
        return resp;
    }

    public CommonResp<String> delete(String username) {
        CommonResp<String> resp = new CommonResp<>();
        if(getUserByUsername(username) == null) {
            resp.setSuccess(false);
            resp.setMessage("The user is not exist!");
        }
        else {
            try{
                petSearchService.deletePetSearchByUsername(username);
                petFoundService.deletePetFoundByUsername(username);
                Integer result = deleteUserByUsername(username);
                if(result == 1) {
                    resp.setMessage("Delete User Success!");
                }
                else {
                    resp.setSuccess(false);
                    resp.setMessage("Delete User Failed!");
                }
            }catch (Exception e)
            {
                System.out.println(e);
                resp.setSuccess(false);
                resp.setMessage("Delete User Failed!");
            }
        }
        return resp;
    }

    public CommonResp<ListResp<UserResp>> listFilter(ListFilterReq<UserFilter> req)
    {
        Integer curPageNum = req.getCurPageNum();
        Integer pageSize = req.getPageSize();
        UserFilter filter = req.getFilter();

        CommonResp<ListResp<UserResp>> resp = new CommonResp<>();
        Integer startRow = (curPageNum - 1) * pageSize;
        ListResp<UserResp> listResp = new ListResp<>();
        List<User> userList = userMapper.selectUserByFilter(startRow, pageSize, filter);
        List<UserResp> userRespList = new ArrayList<>();
        for(User user : userList)
        {
            UserResp userResp = new UserResp();
            BeanUtils.copyProperties(user, userResp);
            userRespList.add(userResp);
        }

        listResp.setCurPageNum(curPageNum);
        listResp.setPageSize(pageSize);
        listResp.setTotalPageNum((int) Math.ceil((double) getUserNumberByFilter(filter) / (double) pageSize));
        listResp.setList(userRespList);
        resp.setContent(listResp);
        return resp;
    }
//TODO: 用户的搜索

//    public CommonResp<ListResp<ShelterResp>> getUserBySearch(SearchReq<String> searchReq) {
//        Integer curPageNum = searchReq.getCurPageNum();
//        Integer pageSize = searchReq.getPageSize();
//        String search = searchReq.getSearch();
//
//        CommonResp<ListResp<UserResp>> resp = new CommonResp<>();
//        Integer startRow = (curPageNum - 1) * pageSize;
//        ListResp<UserResp> listResp = new ListResp<>();
//        List<User> userList = userMapper.selectUserBySearch
//        List<UserResp> userRespList = new ArrayList<>();
//        for(User user : userList)
//        {
//            UserResp userResp = new UserResp();
//            BeanUtils.copyProperties(user, userResp);
//            userRespList.add(userResp);
//        }
//
//        listResp.setCurPageNum(curPageNum);
//        listResp.setPageSize(pageSize);
//        listResp.setTotalPageNum((int) Math.ceil((double) / (double) pageSize));
//        listResp.setList(userRespList);
//        resp.setContent(listResp);
//        return resp;
//    }

    public int getUserNumberByFilter(UserFilter filter)
    {
        return userMapper.countUserByFilter(filter);
    }

    public User getUserByUsername(String username) {
        return userMapper.selectByUsername(username);
    }

    public User getUserByEmail(String email) {
        return userMapper.selectByEmail(email);
    }

    public User getUserByPhone(String phone) {
        return userMapper.selectByPhone(phone);
    }

    public Integer insertUser(User record) {
        return userMapper.insertUser(record);
    }

    public Integer updateUserByUsername(User record) {
        return userMapper.updateByUsername(record, record.getUsername());
    }

    public Integer deleteUserByUsername(String username) {
        return userMapper.deleteByUsername(username);
    }

    public Boolean isUserExist(String username) {
        return userMapper.selectByUsername(username) != null;
    }

    public Boolean isUsernameExist(String username) {
        return isUserExist(username) || shelterMapper.selectByUsername(username) != null || adminService.isAdminExist(username);
    }

}



