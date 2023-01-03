package com.petfound.backend.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import com.petfound.backend.Entity.Admin;

public interface AdminMapper {

    Admin selectByUsernameAndPassword(@Param("username")String username,@Param("password")String password);

    Admin selectByUsername(@Param("username")String username);

}