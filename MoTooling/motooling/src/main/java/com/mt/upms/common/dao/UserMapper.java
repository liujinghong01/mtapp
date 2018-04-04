package com.mt.upms.common.dao;

import com.mt.upms.common.model.User;
import com.mt.upms.permission.bo.URoleBo;

import java.util.List;
import java.util.Map;

public interface UserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(User record);

    User selectByPrimaryKey(Long id);

    List<User> selectAll();

    int updateByPrimaryKey(User record);



    int insertSelective(User record);


    int updateByPrimaryKeySelective(User record);


    User login(Map<String, Object> map);

    User findUserByEmail(String email);

    List<URoleBo> selectRoleByUserId(Long id);

    ///////////////////////////////////////
    User selectByUid(Long uid);

    User selectByMobile(String mobile);

    void  register(User user);

    Long  getMaxUid();

    User selectByWechat(String wechat);

    //返回本部门的user
    List<User>  selectByDepId(Map<String, Object> dataMap);
    List<Map<String, Object>> getDeptMemeberByDepId(Map<String, Object> dataMap);

    //模糊搜索用户
    List<Map<String,Object>> searchUser(String companyName);

    List<Map<String, Object>> selectSalesmanListByCompanyId(Integer companyId);

    List<Map<String, Object>> getDepIdAndNameByUid(Map map);

    int updateAvatar(Map<String, Object> map);
}