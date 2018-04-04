package com.mt.cms.company.service.impl;

import com.mt.cms.common.dao.*;
import com.mt.cms.common.model.*;
import com.mt.cms.company.service.CompanyService;
import com.mt.cms.company.service.CustomerService;
import com.mt.cms.company.service.SupplierService;
import com.mt.common.bo.IdType;
import com.mt.common.core.mybatis.BaseMybatisDao;
import com.mt.common.customenum.RoleEnum;
import com.mt.pm.common.dao.PmPgDeviceMapper;
import com.mt.pm.common.dao.PmPgMapper;
import com.mt.pm.common.dao.PmPgMemberMapper;
import com.mt.pm.common.model.PmPg;
import com.mt.pm.common.model.PmPgDevice;
import com.mt.pm.common.model.PmPgMember;
import com.mt.upms.common.dao.MyFriendMapper;
import com.mt.upms.common.dao.RoleMapper;
import com.mt.upms.common.dao.UserMapper;
import com.mt.upms.common.dao.UserRoleMapper;
import com.mt.upms.common.model.InvitedUser;
import com.mt.upms.common.model.MyFriend;
import com.mt.upms.common.model.User;
import com.mt.upms.common.model.UserRole;
import com.mt.upms.user.service.UserPowerService;
import com.mt.upms.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * @Author: Fhk
 * @Description:
 * @create 2017/11/25 11:02
 * @Modified By:
 */

@Service
@Transactional
public class CompanyServiceImpl extends BaseMybatisDao<CompanyMapper> implements CompanyService {

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    private SupplierMapper supplierMapper;

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MyFriendMapper myFriendMapper;

    @Autowired
    private OrigUserCompanyMapper origUserCompanyMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private PmPgMapper pmPgMapper;

    @Autowired
    private PmPgMemberMapper pmPgMemberMapper;

    @Autowired
    private PmPgDeviceMapper pmPgDeviceMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private UserPowerService userPowerService;

    @Autowired
    private SupplierWareMapper supplierWareMapper;


    @Override
    public PmPgMember selectByPgIdAndMemberId(Long pgId, Long memberId) {
        Map<String, Object> map = new HashMap<>();
        map.put("pgId", pgId);
        map.put("memberId", memberId);
        return pmPgMemberMapper.selectByPgIdAndMemberId(map);
    }

    @Override
    public int insertUserCompany(OrigUserCompany origUserCompany, StringBuilder err, boolean isPgCall) {
        ////return companyMapper.insertUserCompany(origUserCompany);
        Department department = departmentMapper.selectByPrimaryKey(origUserCompany.getDepId());
        if (department == null) {
            throw new IllegalArgumentException("没有为该员工分配部门");
        }
        if (!department.getCompanyId().equals(origUserCompany.getCompanyId())) {
            throw new IllegalArgumentException("部门与公司不匹配");
        }
        if (!isPgCall) {
            Department productionDept = this.selectProductionDept(department.getCompanyId());
            if (productionDept != null && ("|" + department.getAllUpperIds() + "|").contains("|" + productionDept.getDepId() + "|")) {
                //是生产部下的子部门的人员，则同步新增pm_pg_member
                boolean isExists;
                PmPgMember pmPgMember = this.selectByPgIdAndMemberId(origUserCompany.getDepId().longValue(), origUserCompany.getUid());
                if (pmPgMember == null) {
                    pmPgMember = new PmPgMember();
                    isExists = false;
                } else {
                    isExists = true;
                }
                pmPgMember.setPgId(origUserCompany.getDepId().longValue());
                pmPgMember.setMemberId(origUserCompany.getUid());
                pmPgMember.setMemberName((origUserCompany.getWorkName()));
                if (origUserCompany.getUid().toString().equals(department.getManager())) {
                    this.updatePgMemberIsLeader(origUserCompany.getDepId().longValue(), null, "0");
                    pmPgMember.setIsLeader("1");
                } else {
                    pmPgMember.setIsLeader("0");
                }
                pmPgMember.setMemberStatus("0");
                pmPgMember.setHandlerId(origUserCompany.getUid());
                if (isExists) {
                    pmPgMemberMapper.updateByPrimaryKeySelective(pmPgMember);
                } else {
                    pmPgMemberMapper.insertSelective(pmPgMember);
                }
            }
        }
        boolean isExists;
        if (department != null && department.getManager() != null && !department.getManager().trim().isEmpty() && Long.valueOf(department.getManager()).equals(origUserCompany.getUid())) {
            this.updateUserRole(origUserCompany.getCompanyId(), origUserCompany.getDepId(), origUserCompany.getUid(), RoleEnum.DEPARTMENT_LEADER);
        } else {
            this.updateUserRole(origUserCompany.getCompanyId(), origUserCompany.getDepId(), origUserCompany.getUid(), RoleEnum.EMPLOYEE);
        }
        OrigUserCompany oldUserCompany = this.selectUserCompanyByDepIdAndUid(origUserCompany.getCompanyId(), origUserCompany.getDepId(), origUserCompany.getUid());
        if (oldUserCompany == null) {
            return origUserCompanyMapper.insertSelective(origUserCompany);
        } else {
            origUserCompany.setId(oldUserCompany.getId());
            return origUserCompanyMapper.updateByPrimaryKeySelective(origUserCompany);
        }
    }

    @Override
    public OrigUserCompany selectUserCompanyByUid(Integer companyId, Long uid) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("uid", uid);
        paramMap.put("companyId", companyId);
        return origUserCompanyMapper.selectUserCompanyByUid(paramMap);
    }

    @Override
    public OrigUserCompany selectUserCompanyByDepIdAndUid(Integer companyId, Integer depId, Long uid) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("uid", uid);
        paramMap.put("depId", depId);
        paramMap.put("companyId", companyId);
        return origUserCompanyMapper.selectUserCompanyByDepIdAndUid(paramMap);
    }

    @Override
    public List<OrigUserCompany> selectUserCompanyListByDepIdAndUid(Integer companyId, Integer depId, Long uid) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("uid", uid);
        paramMap.put("depId", depId);
        paramMap.put("companyId", companyId);
        return origUserCompanyMapper.selectUserCompanyListByDepIdAndUid(paramMap);
    }

    /**
     * 注册公司
     *
     * @param company
     * @return
     */
    @Override
    public Integer registerCompany(Company company) {
        Integer companyId = company.getCompanyId();
        if (companyId == null || companyId < 1) {
            companyId = this.createNewId(IdType.COMPANY).intValue();
            company.setCompanyId(companyId);
        }
        companyMapper.insertSelective(company);
        return companyId;
    }

    @Override
    public Department selectByDepCode(Integer companyId, String depCode) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("companyId", companyId);
        paramMap.put("depCode", depCode);
        return departmentMapper.selectByDepCode(paramMap);
    }

    @Override
    public Department selectProductionDept(Integer companyId) {
        return selectByDepCode(companyId, "Production_Dept");
    }

    @Override
    public UserRole selectByCompanyIdAndUidAndRoleId(Integer companyId, Long uid, Long roleId) {
        Map<String, Object> map = new HashMap<>();
        map.put("companyId", companyId);
        map.put("uid", uid);
        map.put("roleId", roleId);
        return userRoleMapper.selectByCompanyIdAndUidAndRoleId(map);
    }

    /**
     * 注册公司,并添加公司人员
     *
     * @param company
     * @param uid
     * @return
     */
    @Override
    public Integer createCompanyRegister(Company company, Long uid) {
        Integer companyId = company.getCompanyId();
        Company company1 = this.selectByCompanyId(companyId);
        if (company1 == null) {
            companyId = this.registerCompany(company);
            //生成系统缺省部门及其它公司注册初始化数据
            ////this.createDefaultDepartment(companyId);
            this.createCompanyInitData(companyId, uid);
        } else {
            //如果存在，就更新信息
            //该属性不更新
            company.setPlatCompanyType(null);
            company.setId(company1.getId());
            this.updateByPrimaryKeySelective(company);
        }
        OrigUserCompany origUserCompany = this.selectUserCompanyByUid(companyId, uid);
        if (origUserCompany == null) {
            origUserCompany = new OrigUserCompany();
            User user = userService.selectByUid(uid);
            origUserCompany.setWorkName(user.getUsername());
            origUserCompany.setWorkEmail(user.getEmail());
            origUserCompany.setWorkPhone(user.getMobile());
            origUserCompany.setUid(uid);
            //1在职正常班
            origUserCompany.setWorkState("1");
            origUserCompany.setCompanyId(companyId);
            //注册公司时，将当前uid加入该公司的总经办部门
            Department department = this.selectByDepCode(companyId, "Office_Manager");
            if (department != null) {
                origUserCompany.setDepId(department.getDepId());
            }
            origUserCompanyMapper.insertSelective(origUserCompany);
           /* StringBuilder err = new StringBuilder();
            this.insertUserCompany(origUserCompany, err, false);

            Map<String, Object> map = new HashMap<>();
            map.put("company_id", companyId);
            map.put("role_code", RoleEnum.ENTERPRISE_ADMIN.getCode());
            Long roleId = roleMapper.selectRoleId(map);
            UserRole userRole = this.selectByCompanyIdAndDepIdAndUid(companyId, origUserCompany.getDepId(), uid);//, roleId);
            if (userRole == null) {
                userRole = new UserRole();
                userRole.setCompanyId(companyId);
                userRole.setCreatedAt(DateUtil.localDateToDate(LocalDate.now()));
                userRole.setUid(uid);
                userRole.setDepId(origUserCompany.getDepId());
                userRole.setRoleId(roleId);
                userRoleMapper.insertSelective(userRole);
            } else {
                userRole.setDepId(origUserCompany.getDepId());
                userRole.setRoleId(roleId);
                userRoleMapper.updateByPrimaryKey(userRole);
            }*/
            this.updateUserRole(companyId, origUserCompany.getDepId(), uid, RoleEnum.ENTERPRISE_ADMIN);
            Map<String, Object> param = new HashMap<>();
            param.put("uid", uid);
            param.put("company_id", companyId);
            userPowerService.selectPermissionis(param);
        }
        return companyId;
    }

    @Override
    public Company selectByCompanyId(Integer companyId) {
        return companyMapper.selectByCompanyId(companyId);
    }

    /**
     * 根据部门名称查询部门信息
     *
     * @param companyId
     * @param depName
     * @return
     */
    @Override
    public Department selectByDepName(Integer companyId, String depName) {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("companyId", companyId);
        dataMap.put("depName", depName);
        return departmentMapper.selectByDepName(dataMap);
    }

    @Override
    public int insertDepartment(Department department, StringBuilder err, boolean isPgCall) {
        departmentMapper.insertDepartment(department);
        Map<String, Object> allUpperDeps = this.getAllUpperDeptIds(department.getCompanyId(), department.getDepId());
        if (Integer.parseInt(allUpperDeps.get("code").toString()) == 1) {
            department.setAllUpperIds(allUpperDeps.get("allUpperIds") == null ? "" : allUpperDeps.get("allUpperIds").toString());
        } else {
            throw new IllegalArgumentException("上级部门设置出错");
        }
        int ret = departmentMapper.updateByPrimaryKeySelective(department);
        if (!isPgCall) {
            Department productionDept = this.selectProductionDept(department.getCompanyId());
            if (productionDept != null && ("|" + department.getAllUpperIds() + "|").contains("|" + productionDept.getDepId() + "|")) {
                //是生产部下的子部门，则同步新增Pg
                PmPg pmPg = new PmPg();
                pmPg.setPgId(department.getDepId().longValue());
                pmPg.setPgName(department.getName());
                pmPg.setPgDesc(department.getName());
                pmPgMapper.insertSelective(pmPg);
            }
        }
        //将部门负责人加入为该部门人员
        if (department.getManager() != null && !department.getManager().trim().isEmpty()) {
            Long managerId = Long.valueOf(department.getManager());
            OrigUserCompany origUserCompany = this.selectUserCompanyByDepIdAndUid(department.getCompanyId(), department.getDepId(), managerId);
            if (origUserCompany == null) {
                origUserCompany = new OrigUserCompany();
            }
            origUserCompany.setUid(managerId);
            origUserCompany.setCompanyId(department.getCompanyId());
            origUserCompany.setDepId(department.getDepId());
            origUserCompany.setHandlerId(department.getHandlerId());
            origUserCompany.setWorkName(department.getManagerName());
            origUserCompany.setWorkState("1");
            OrigUserCompany existsedUserCompany = this.selectUserCompanyByDepIdAndUid(department.getCompanyId(), null, managerId);
            if (existsedUserCompany != null) {
                origUserCompany.setWorkEmail(existsedUserCompany.getWorkEmail());
                origUserCompany.setWorkPhone(existsedUserCompany.getWorkPhone());
            } else {
                User user = userService.selectByUid(managerId);
                if (user != null) {
                    origUserCompany.setWorkEmail(user.getEmail());
                    origUserCompany.setWorkPhone(user.getMobile());
                }
            }
            ////origUserCompanyMapper.insertSelective(origUserCompany);
            if (this.insertUserCompany(origUserCompany, err, false) < 0) {
                throw new IllegalArgumentException("添加部门负责人出错");
            }
            /*
            if (!isPgCall) {
                Department productionDept = this.selectProductionDept(department.getCompanyId());
                if (productionDept != null && ("|" + department.getAllUpperIds() + "|").contains("|" + productionDept.getDepId()+"|")) {
                    //是生产部下的子部门的人员，则同步新增pm_pg_member
                    PmPgMember pmPgMember = new PmPgMember();
                    pmPgMember.setPgId(origUserCompany.getDepId().longValue());
                    pmPgMember.setMemberId(origUserCompany.getUid());
                    pmPgMember.setMemberName((origUserCompany.getWorkName()));
                    if (origUserCompany.getUid().toString().equals(department.getManager()))
                        pmPgMember.setIsLeader("1");
                    else
                        pmPgMember.setIsLeader("0");
                    pmPgMember.setHandlerId(origUserCompany.getUid());
                    pmPgMemberMapper.insertSelective(pmPgMember);
                }
            }*/
        }
        return ret;
    }

    /**
     * 根据上级部门Id查询部门信息
     *
     * @param companyId
     * @param depId
     * @return
     */
    @Override
    public List<Department> selectByUpperDepId(Integer companyId, Integer depId) {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("companyId", companyId);
        dataMap.put("depId", depId);
        return departmentMapper.selectByUpperDepId(dataMap);
    }

    /**
     * 获取部门人员数量
     *
     * @param companyId
     * @param depId
     * @return
     */
    @Override
    public Integer getAllUserCountByDepId(Integer companyId, Integer depId) {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("companyId", companyId);
        dataMap.put("depId", depId.toString());
        return departmentMapper.getAllUserCountByDepId(dataMap);
    }

    /**
     * 更改user_company表中的work_state时，应同步更改pm_pg_member表中的member_status
     *
     * @param workState
     * @param memberStatus
     * @return ： null 失败，表示状态不能改变
     */
    private String workStateToMemberStatus(String workState, String memberStatus) {
        //work_state 工作状态: 0离职，1在职正常班，2在职休假中, 3失效
        //member_status 人员状态：0正常待加工，1正常加工中，2休假，3离职，4失效'
        switch (workState) {
            case "0":
                if ("1".equals(memberStatus)) {
                    return null;
                } else {
                    return "3";
                }
            case "1":
                if ("0".equals(memberStatus) || "1".equals(memberStatus)) {
                    return memberStatus;
                } else {
                    return "0";
                }
            case "2":
                if ("1".equals(memberStatus)) {
                    return null;
                } else {
                    return "2";
                }
            case "3":
                if ("1".equals(memberStatus)) {
                    return null;
                } else {
                    return "4";
                }
            default:
                return null;
        }
    }

    @Override
    public int updateUserCompany(OrigUserCompany origUserCompany, StringBuilder err, boolean isPgCall) {
        //部门与生产小组信息及其人员信息要进行同步更新
        OrigUserCompany oldOrigUserCompany = origUserCompanyMapper.selectByPrimaryKey(origUserCompany.getId());

        //是否改变了所在部门
        boolean isChangeDepId;
        if (oldOrigUserCompany.getDepId() == null) {
            if (origUserCompany.getDepId() == null) {
                isChangeDepId = false;
            } else if (origUserCompany.getDepId().equals(0)) {
                isChangeDepId = false;
                origUserCompany.setDepId(null);
            } else {
                isChangeDepId = true;
            }
        } else {
            if (origUserCompany.getDepId() == null) {
                isChangeDepId = true;
            } else if (origUserCompany.getDepId().equals(0)) {
                isChangeDepId = true;
                origUserCompany.setDepId(null);
            } else if (oldOrigUserCompany.getDepId().equals(origUserCompany.getDepId())) {
                isChangeDepId = false;
            } else {
                isChangeDepId = true;
            }
        }

        if (isChangeDepId) {
            /*Map<String, Object> userRoleMap = new HashMap<>(6);
            userRoleMap.put("uid", oldOrigUserCompany.getUid());
            userRoleMap.put("dep_id", oldOrigUserCompany.getDepId());
            userRoleMap.put("company_id", oldOrigUserCompany.getCompanyId());
            userRoleMap.put("updated_at", DateUtil.getCurrentDateString());
            // 查出当前角色 code
            String roleCode = userRoleMapper.getRoleCode(userRoleMap);
            userRoleMap.put("new_dep_id", origUserCompany.getDepId());
            if(!RoleEnum.ENTERPRISE_ADMIN.getCode().equals(roleCode)) {
                userRoleMap.put("role_id",  roleMapper.getEmployee(oldOrigUserCompany.getCompanyId()));
                // 如果人员修改了部门，角色变更为普通员工
                userRoleMapper.updateDepId(userRoleMap);
            }else {
                //  如果是企业admin 移动部门 角色不变
                //userRoleMap.put("role_id", RoleEnum.ENTERPRISE_ADMIN.getCode());
                userRoleMapper.updateDepId(userRoleMap);
            }*/
            //更改角色
            UserRole userRole = this.selectByCompanyIdAndDepIdAndUid(oldOrigUserCompany.getCompanyId(), oldOrigUserCompany.getDepId(), oldOrigUserCompany.getUid());
            Long adminRoleId = this.getRoleId(oldOrigUserCompany.getCompanyId(), RoleEnum.ENTERPRISE_ADMIN);
            if (userRole != null) {
                if (!adminRoleId.equals(userRole.getRoleId())) {
                    userRole.setRoleId(this.getRoleId(oldOrigUserCompany.getCompanyId(), RoleEnum.EMPLOYEE));
                    userRole.setDepId(origUserCompany.getDepId());
                } else {
                    userRole.setDepId(origUserCompany.getDepId());
                }
                userRole.setUpdatedAt(new Date());
                userRoleMapper.updateByPrimaryKeySelective(userRole);
            } else {
                this.updateUserRole(origUserCompany.getCompanyId(), origUserCompany.getDepId(), origUserCompany.getUid(), RoleEnum.EMPLOYEE);
            }
        }
        String newMemberStatus;
        if (!isPgCall) {
            if (!isChangeDepId) {
                PmPgMember pmPgMember = this.selectByPgIdAndMemberId(origUserCompany.getDepId().longValue(), origUserCompany.getUid());
                if (pmPgMember != null) {
                    pmPgMember.setMemberName(origUserCompany.getWorkName());
                    newMemberStatus = this.workStateToMemberStatus(origUserCompany.getWorkState(), pmPgMember.getMemberStatus());
                    if (newMemberStatus == null) {
                        throw new IllegalArgumentException("该人员正在加工中，不能更改状态");
                    }
                    pmPgMember.setMemberStatus(newMemberStatus);
                    pmPgMember.setHandlerId(origUserCompany.getHandlerId());
                    pmPgMember.setUpdatedAt(new Date());
                    pmPgMemberMapper.updateByPrimaryKeySelective(pmPgMember);
                }
            } else {
                Department productionDept = this.selectProductionDept(oldOrigUserCompany.getCompanyId());
                Department oldDept = this.selectByDepId(oldOrigUserCompany.getDepId());
                Department newDept = this.selectByDepId(origUserCompany.getDepId());
                //原部门负责人为该人， 这时要清空原部门负责人
                if (oldDept.getManager() != null && !oldDept.getManager().trim().isEmpty() && Long.valueOf(oldDept.getManager()).equals(origUserCompany.getUid())) {
                    oldDept.setManager(null);
                    oldDept.setManagerName(null);
                    oldDept.setHandlerId(origUserCompany.getHandlerId());
                    oldDept.setUpdatedAt(new Date());
                    departmentMapper.updateByPrimaryKey(oldDept);
                }
                if (productionDept != null && ("|" + oldDept.getAllUpperIds() + "|").contains("|" + productionDept.getDepId() + "|")) {
                    if (("|" + newDept.getAllUpperIds() + "|").contains("|" + productionDept.getDepId() + "|")) {
                        //新旧部门都是生产子部门，只更改pgMember信息
                        PmPgMember oldPgMember = this.selectByPgIdAndMemberId(oldOrigUserCompany.getDepId().longValue(), oldOrigUserCompany.getUid());
                        if (oldPgMember != null) {
                            oldPgMember.setMemberName(origUserCompany.getWorkName());
                            newMemberStatus = this.workStateToMemberStatus(origUserCompany.getWorkState(), oldPgMember.getMemberStatus());
                            if (newMemberStatus == null) {
                                throw new IllegalArgumentException("该人员正在加工中，不能更改状态");
                            }
                            oldPgMember.setMemberStatus(newMemberStatus);
                            oldPgMember.setPgId(origUserCompany.getDepId().longValue());
                            oldPgMember.setIsLeader("0");
                            oldPgMember.setHandlerId(origUserCompany.getHandlerId());
                            oldPgMember.setUpdatedAt(new Date());
                            pmPgMemberMapper.updateByPrimaryKeySelective(oldPgMember);
                        }
                    } else {
                        //将该人移出了生产子部门
                        PmPgMember oldPgMember = this.selectByPgIdAndMemberId(oldOrigUserCompany.getDepId().longValue(), oldOrigUserCompany.getUid());
                        if (oldPgMember != null) {
                            oldPgMember.setMemberName(origUserCompany.getWorkName());
                            if ("1".equals(oldPgMember.getMemberStatus())) {
                                throw new IllegalArgumentException("该人员正在加工中，不能移出原部门");
                            }
                            //失效
                            oldPgMember.setMemberStatus("4");
                            oldPgMember.setHandlerId(origUserCompany.getHandlerId());
                            oldPgMember.setUpdatedAt(new Date());
                            pmPgMemberMapper.updateByPrimaryKeySelective(oldPgMember);
                        }
                    }
                } else {
                    if (("|" + newDept.getAllUpperIds() + "|").contains("|" + productionDept.getDepId() + "|")) {
                        //移入生产子部门，新增pgMember信息
                        PmPgMember newPgMember = this.selectByPgIdAndMemberId(origUserCompany.getDepId().longValue(), origUserCompany.getUid());
                        boolean isExists;
                        if (newPgMember == null) {
                            newPgMember = new PmPgMember();
                            isExists = false;
                        } else {
                            isExists = true;
                        }
                        newPgMember.setMemberName(origUserCompany.getWorkName());
                        newPgMember.setMemberStatus("0");
                        newPgMember.setIsLeader("0");
                        newPgMember.setPgId(origUserCompany.getDepId().longValue());
                        newPgMember.setHandlerId(origUserCompany.getHandlerId());
                        newPgMember.setUpdatedAt(new Date());
                        if (isExists) {
                            pmPgMemberMapper.updateByPrimaryKeySelective(newPgMember);
                        } else {
                            pmPgMemberMapper.insertSelective(newPgMember);
                        }
                    }
                }
            }
        }
        //return origUserCompanyMapper.updateByPrimaryKeySelective(origUserCompany);
        int ret = origUserCompanyMapper.updateByPrimaryKeySelective(origUserCompany);

        //将user_company表中几个公共信息设成一样的
        List<OrigUserCompany> origUserCompanyList = this.selectUserCompanyListByDepIdAndUid(origUserCompany.getCompanyId(), null, origUserCompany.getUid());
        for (OrigUserCompany itemUserCompany : origUserCompanyList) {
            if (itemUserCompany.getDepId().equals(origUserCompany.getDepId())) {
                continue;
            }
            itemUserCompany.setWorkEmail(origUserCompany.getWorkEmail());
            itemUserCompany.setWorkPhone(origUserCompany.getWorkPhone());
            itemUserCompany.setWorkName(origUserCompany.getWorkName());
            itemUserCompany.setWorkState(origUserCompany.getWorkState());

            PmPgMember pmPgMember = this.selectByPgIdAndMemberId(itemUserCompany.getDepId().longValue(), itemUserCompany.getUid());
            if (pmPgMember != null) {
                pmPgMember.setMemberName(itemUserCompany.getWorkName());
                newMemberStatus = this.workStateToMemberStatus(itemUserCompany.getWorkState(), pmPgMember.getMemberStatus());
                if (newMemberStatus == null) {
                    throw new IllegalArgumentException("该人员正在加工中，不能更改状态");
                }
                pmPgMember.setMemberStatus(newMemberStatus);
                pmPgMember.setHandlerId(origUserCompany.getHandlerId());
                pmPgMember.setUpdatedAt(new Date());
                pmPgMemberMapper.updateByPrimaryKeySelective(pmPgMember);
            }
            origUserCompanyMapper.updateByPrimaryKeySelective(itemUserCompany);
        }
        return ret;
    }

    /**
     * 获取某部门的所有上级部门
     *
     * @param companyId
     * @param depId
     * @param depIdMap
     * @return
     */
    @Override
    public int getAllUpperDeptIds(Integer companyId, Integer depId, List<Integer> depIdMap) {
        if (depId == null || depId == 0) {
            return 0;
        }

        //因为depId是主键，所以companyId不用
        Department department = departmentMapper.selectByPrimaryKey(depId);
        if (department == null) {
            //有部门Id，但不存在该部门
            return 0;
        } else if (department.getUpperId() == null || department.getUpperId() == 0) {
            //成功
            return 1;
        } else if (depIdMap.indexOf(department.getUpperId()) >= 0) {
            //上级部门设置存在死循环，报错
            return -1;
        } else {
            depIdMap.add(department.getUpperId());
            return getAllUpperDeptIds(companyId, department.getUpperId(), depIdMap);
        }
    }

    /**
     * 得到某部门的所有上级部门：1|2
     *
     * @param companyId
     * @param depId
     * @return
     */
    @Override
    public Map<String, Object> getAllUpperDeptIds(Integer companyId, Integer depId) {
        Map<String, Object> retMap = new HashMap<>();
        retMap.put("code", 1);

        String allUpperIds = "";
        List<Integer> upperIdsMap = new ArrayList<>();
        upperIdsMap.add(depId);
        int ret = getAllUpperDeptIds(companyId, depId, upperIdsMap);
        if (ret == 1) {
            for (Integer item : upperIdsMap) {
                if (item.equals(depId)) {
                    continue;
                }
                allUpperIds += "|" + item.toString().trim();
            }
            if (allUpperIds.length() > 0) {
                allUpperIds = allUpperIds.substring(1);
            }
        }
        //code为1是成功的
        retMap.put("code", ret);
        retMap.put("allUpperIds", allUpperIds.isEmpty() ? null : allUpperIds);
        return retMap;
    }

    /**
     * 生成系统中业务中的新Id：如用户Id，公司Id， 可修改生成规则
     *
     * @param idType
     * @return
     */
    @Override
    public Long createNewId(IdType idType) {
        Integer id = null;
        Long newId = null;
        switch (idType) {
            case USER:
                newId = userMapper.getMaxUid();
                if (newId == null || newId < 1) {
                    newId = 1L;
                } else {
                    newId++;
                }
                break;

            case COMPANY:
                id = companyMapper.getMaxCompanyId();
                if (id == null || id < 1) {
                    newId = 1L;
                } else {
                    newId = id.longValue() + 1;
                }
                break;

            case DEPARTMENT:
                id = departmentMapper.getMaxDepId();
                if (id == null || id < 1) {
                    newId = 1L;
                } else {
                    newId = id.longValue() + 1;
                }
                break;

            case SUPPLIER:
                id = supplierMapper.getMaxSupplierId();
                if (id == null || id < 1) {
                    newId = 1L;
                } else {
                    newId = id.longValue() + 1;
                }
                break;

            case CUSTOMER:
                id = customerMapper.getMaxCustomerId();
                if (id == null || id < 1) {
                    newId = 1L;
                } else {
                    newId = id.longValue() + 1;
                }
                break;

            default:
                break;
        }
        return newId;
    }

    /**
     * User: Alnwike  添加
     *
     * @param companyName
     * @param searchType
     * @param companyId
     * @param uId
     * @return
     */
    @Override
    public Map<String, Object> vagueSelectCompanyAndUser(String companyName, Integer searchType, Integer companyId, Integer uId) {
        List<Map<String, Object>> listReturn = null;
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Map<String, Object> map = new HashMap<>();
        if (searchType == 1) {
            List listSupplier = null;
            List listCompyId = null;
            if (companyId != null) {
                listCompyId = customerMapper.seletComapyId(companyId);
                listSupplier = supplierMapper.seletComapyId(companyId);
            }
            listReturn = companyMapper.searchCompany(companyName);
            for (Map<String, Object> s : listReturn) {
                Map<String, Object> hashMap = new HashMap<String, Object>();
                for (String k : s.keySet()) {
                    Object data = s.get(k);
                    if ("company_name".equals(k)) {
                        hashMap.put("company_name", data);
                    } else if ("company_id".equals(k)) {
                        hashMap.put("company_id", data);
                        if (listCompyId.contains(data)) {
                            // type =2  客户
                            hashMap.put("relation_type", 2);
                        } else if (listSupplier.contains(data)) {
                            // type =1  供应商
                            hashMap.put("relation_type", 1);
                        } else {
                            //没有 任何关系
                            hashMap.put("relation_type", 0);
                        }
                    } else if ("address".equals(k)) {
                        hashMap.put("company_address", data);
                    }
                }
                list.add(hashMap);
            }
            map.put("company_info", list);
        } else if (searchType == 2) {
            listReturn = userMapper.searchUser(companyName);
            List listUId = myFriendMapper.seletUId(uId);
            for (Map<String, Object> s : listReturn) {
                Map<String, Object> hashMap = new HashMap<String, Object>();
                for (String k : s.keySet()) {
                    Object data = s.get(k);
                    if ("uid".equals(k)) {
                        hashMap.put("user_id", data);
                        if (listUId.contains(Long.valueOf(data.toString()))) {
                            //1   是好友
                            hashMap.put("relation_type", 1);
                        } else {
                            // 0  不是好友
                            hashMap.put("relation_type", 0);
                        }
                    } else if ("avatar".equals(k)) {
                        hashMap.put("avatar", data);
                    } else if ("username".equals(k)) {
                        hashMap.put("user_name", data);
                    }
                }
                list.add(hashMap);
            }
            map.put("user_info", list);
        }

        return map;
    }

    @Override
    public int updateByPrimaryKeySelective(Company company) {
        return companyMapper.updateByPrimaryKeySelective(company);
    }

    @Override
    public Department selectByDepId(Integer depId) {
        return departmentMapper.selectByPrimaryKey(depId);
    }

    /**
     * 获取某客户公司信息
     *
     * @param companyId
     * @param customerId
     * @return
     */
    @Override
    public Customer selectByCompanyIdAndCustomerId(Integer companyId, Integer customerId) {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("companyId", companyId);
        dataMap.put("cstCompanyId", customerId);
        return customerMapper.selectByCompanyIdAndCustomerId(dataMap);
    }

    /**
     * 获取某供应商公司信息
     *
     * @param companyId
     * @param supplierId
     * @return
     */
    @Override
    public Supplier selectByCompanyIdAndSupplierId(Integer companyId, Integer supplierId) {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("companyId", companyId);
        dataMap.put("supCompanyId", supplierId);
        return supplierMapper.selectByCompanyIdAndSupplierId(dataMap);
    }

    /**
     * 为新注册公司生成缺省部门
     *
     * @param companyId
     * @return
     */
    @Override
    public List<Map<String, Object>> createDefaultDepartment(Integer companyId) {
        return companyMapper.createDefaultDepartment(companyId);
    }

    @Override
    public String createCompanyInitData(Integer companyId, Long uid) {
        Map<String, Object> map = new HashMap<>();
        map.put("companyId", companyId);
        map.put("uid", uid);
        return companyMapper.createCompanyInitData(map);
    }

    /**
     * 生成某部门的所有下级部门:树状结构
     *
     * @param companyId
     * @param depId
     * @return
     */
    @Override
    public ArrayList<Map<String, Object>> getAllSubDeps(Integer companyId, Integer depId) {
        List<Department> departmentList = selectByUpperDepId(companyId, depId);
        ArrayList depts = new ArrayList();
        String allUpperIds;
        for (Department department : departmentList) {
            Map<String, Object> dept = new HashMap<>();
            dept.put("dep_id", department.getDepId());
            dept.put("dep_name", department.getName());
            Integer memberNum = getAllUserCountByDepId(companyId, department.getDepId());
            dept.put("member_num", memberNum);
            allUpperIds = department.getAllUpperIds();
            allUpperIds = allUpperIds == null ? "" : allUpperIds.trim();
            String[] upperIds = allUpperIds.split("\\|");
            dept.put("level", upperIds[0].length() > 0 ? upperIds.length + 1 : upperIds.length);
            dept.put("is_default", department.getIsDefault());

            List<Map<String, Object>> subDeps = getAllSubDeps(companyId, department.getDepId());
            dept.put("deps", subDeps);

            depts.add(dept);
        }
        return depts;
    }


    /*
    *//**
     * 重新计算该部门及其所有子部门的 “所有上级部门”属性
     * @param companyId
     * @param depId
     * @return
     *//*
    @Override
    public  int setDepAndSubsAllUpperIds(Integer companyId, Integer depId, StringBuilder err, boolean isPgCall) {
        Department parentDept = departmentMapper.selectByPrimaryKey(depId);

        if (parentDept != null) {
            Map<String, Object> allUpperDeps = this.getAllUpperDeptIds(companyId, depId);
            if (Integer.parseInt(allUpperDeps.get("code").toString()) == 1)
                parentDept.setAllUpperIds(allUpperDeps.get("allUpperIds") == null ? "" : allUpperDeps.get("allUpperIds").toString());
            else {
               throw new RuntimeException("上级部门设置出错");
            }
            departmentMapper.updateByPrimaryKeySelective(parentDept);
            //同步Pg
        }
        List<Department> departmentList = selectByUpperDepId(companyId, depId);
        for(Department department: departmentList) {
            setDepAndSubsAllUpperIds(companyId, department.getDepId());
        }
        return  1;
    }*/


    /*
    */

    /**
     * 修改部门，注意要重新生成其所有上级部门
     *
     * @return
     *//*
    private int updateDepartment(Department department, StringBuilder err, boolean isPgCall) {
        //此方法不再使用
        ////return departmentMapper.updateByPrimaryKeySelective(department);
        if (department.getDepCode() != null && department.getUpperId() == null) {
            err.append("不能为系统默认一级部门指定上级部门");
            return  -1;
        }
        Department origDepartment = departmentMapper.selectByPrimaryKey(department.getDepId());
        //是否改变了上级部门
        boolean isChangeUpperId;
        if (origDepartment.getUpperId() == null) {
            if (department.getUpperId() == null) isChangeUpperId = false;
            else if (department.getUpperId().equals(0)) { isChangeUpperId = false; department.setUpperId(null); }
            else isChangeUpperId = true;
        } else {
            if (department.getUpperId() == null) isChangeUpperId = true;
            else if (department.getUpperId().equals(0)) { isChangeUpperId = true; department.setUpperId(null); }
            else if (origDepartment.getUpperId().equals(department.getUpperId())) isChangeUpperId = false;
            else isChangeUpperId = true;
        }
        if (!isPgCall) {
            //先在此修改部门名称
            PmPg pmPg = pmPgMapper.selectByPgId(department.getDepId().longValue());
            if (pmPg != null) {
                pmPg.setPgName(department.getName());
                pmPgMapper.updateByPrimaryKeySelective(pmPg);
            }
        }
        int ret = departmentMapper.updateByPrimaryKeySelective(department);
        if (isChangeUpperId) {
            ret = this.setDepAndSubsAllUpperIds(department.getCompanyId(), department.getDepId(), err, isPgCall);
        }
        return ret;
    }*/
    @Override
    public int updatePgMemberIsLeader(Long pgId, Long memberId, String isLeader) {
        Map<String, Object> map = new HashMap<>();
        map.put("PgId", pgId);
        map.put("memberId", memberId);
        map.put("isLeader", isLeader);
        return pmPgMemberMapper.updateIsLeader(map);
    }

    @Override
    public List<UserRole> selectByCompanyIdAndDepIdAndRoleCode(Integer companyId, Integer depId, RoleEnum roleEnum) {
        Map<String, Object> map = new HashMap<>();
        map.put("company_id", companyId);
        map.put("dep_id", depId);
        map.put("role_code", roleEnum.getCode());

        return userRoleMapper.selectByCompanyIdAndDepIdAndRoleCode(map);
    }

    @Override
    public UserRole selectByCompanyIdAndDepIdAndUid(Integer companyId, Integer depId, Long uid) {
        Map<String, Object> map = new HashMap<>();
        map.put("company_id", companyId);
        map.put("depId", depId);
        map.put("uid", uid);
        return userRoleMapper.selectByCompanyIdAndDepIdAndUid(map);
    }

    @Override
    public Long getRoleId(Integer companyId, RoleEnum roleEnum) {
        Map<String, Object> map = new HashMap<>();
        map.put("company_id", companyId);
        //公司内部邀请角色总是普通员工
        map.put("role_code", roleEnum.getCode());
        //根据 company_id 和 role 的code 查出 role_id
        return roleMapper.selectRoleId(map);
    }


    /**
     * 修改部门，注意要重新生成其所有上级部门
     *
     * @param defInfoMap
     * @param err
     * @return >=0 成功
     * <0 失败
     */
    @Override
    public int updateDepartment(Map defInfoMap, StringBuilder err, boolean isPgCall) {
        //部门与生产小组信息及其人员信息要进行同步更新
        //不允许修改上级部门
        Integer pDepId = defInfoMap.get("dep_id") == null || defInfoMap.get("dep_id").toString().isEmpty() ? null : Integer.parseInt(defInfoMap.get("dep_id").toString());
        Integer pUpperDepId = defInfoMap.get("parent_dep_id") == null || defInfoMap.get("parent_dep_id").toString().isEmpty() ? null : Integer.parseInt(defInfoMap.get("parent_dep_id").toString());
        Integer pCompanyId = defInfoMap.get("company_id") == null ? null : Integer.parseInt(defInfoMap.get("company_id").toString());
        Long managerId = defInfoMap.get("dep_admin_id") == null || defInfoMap.get("dep_admin_id").toString().isEmpty() ? null : Long.valueOf(defInfoMap.get("dep_admin_id").toString());
        Long uid = defInfoMap.get("uid") == null || defInfoMap.get("uid").toString().isEmpty() ? null : Long.valueOf(defInfoMap.get("uid").toString());

        if (pDepId == null) {
            err.append("部门不存在");
            return -1;
        }
        if (pDepId.equals(pUpperDepId)) {
            err.append("上级部门不能为自身");
            return -1;
        }
        Department department = this.selectByDepId(pUpperDepId);
        if (pUpperDepId != null && department == null) {
            err.append("不存在上级部门");
            return -1;
        }
        if (pUpperDepId != null && !department.getCompanyId().equals(pCompanyId)) {
            err.append("公司不存在该上级部门");
            return -1;
        }
        department = this.selectByDepId(pDepId);
        if (department == null) {
            err.append("不存在该部门");
            return -1;
        }
        if (!department.getCompanyId().equals(pCompanyId)) {
            err.append("公司不存在该部门");
            return -1;
        }
        //是否改变了上级部门
        boolean isChangeUpperId;
        if (department.getUpperId() == null) {
            if (pUpperDepId == null) {
                isChangeUpperId = false;
            } else if (pUpperDepId.equals(0)) {
                isChangeUpperId = false;
                pUpperDepId = null;
            } else {
                isChangeUpperId = true;
            }
        } else {
            if (pUpperDepId == null) {
                isChangeUpperId = true;
            } else if (pUpperDepId.equals(0)) {
                isChangeUpperId = true;
                pUpperDepId = null;
            } else if (department.getUpperId().equals(pUpperDepId)) {
                isChangeUpperId = false;
            } else {
                isChangeUpperId = true;
            }
        }
        if (isChangeUpperId) {
            err.append("不能改变上级部门");
            return -1;
        }
        department.setName(defInfoMap.get("dep_name") == null ? null : defInfoMap.get("dep_name").toString());
        department.setManager(managerId == null ? null : managerId.toString());
        department.setManagerName(defInfoMap.get("dep_admin_name") == null ? null : defInfoMap.get("dep_admin_name").toString());
        department.setUpper(defInfoMap.get("parent_dep_name") == null ? null : defInfoMap.get("parent_dep_name").toString());
        department.setUpperId(pUpperDepId);
        department.setHandlerId(uid);

        Long roleId;
        //处理部门负责人的变更
        if (managerId == null) {
            if (!isPgCall) {
                this.updatePgMemberIsLeader(pDepId.longValue(), null, "0");
            }
            //UserRole userRole = this.selectByCompanyIdAndDepIdAndUid(pCompanyId, pDepId, managerId);
            List<UserRole> userRoleList = this.selectByCompanyIdAndDepIdAndRoleCode(pCompanyId, pDepId, RoleEnum.DEPARTMENT_LEADER);
            roleId = this.getRoleId(pCompanyId, RoleEnum.EMPLOYEE);
            for (UserRole userRole : userRoleList) {
                //如果存在则修改为普通员工权限
                userRole.setRoleId(roleId);
                userRole.setUpdatedAt(new Date());
                userRoleMapper.updateByPrimaryKeySelective(userRole);
            }
            if (!isPgCall) {
                //先在此修改部门名称
                PmPg pmPg = pmPgMapper.selectByPgId(pDepId.longValue());
                if (pmPg != null) {
                    pmPg.setPgName(department.getName());
                    pmPgMapper.updateByPrimaryKeySelective(pmPg);
                }
            }
            return departmentMapper.updateByPrimaryKeySelective(department);
        } else {
            OrigUserCompany origUserCompany = this.selectUserCompanyByDepIdAndUid(pCompanyId, pDepId, managerId);
            if (origUserCompany == null) {
                origUserCompany = new OrigUserCompany();
            }
            origUserCompany.setUid(managerId);
            origUserCompany.setCompanyId(department.getCompanyId());
            origUserCompany.setDepId(department.getDepId());
            origUserCompany.setHandlerId(uid);
            origUserCompany.setWorkName(department.getManagerName());
            origUserCompany.setWorkState("1");
            OrigUserCompany otherOrigUserCompany = this.selectUserCompanyByDepIdAndUid(pCompanyId, null, managerId);
            if (otherOrigUserCompany != null) {
                origUserCompany.setWorkEmail(otherOrigUserCompany.getWorkEmail());
                origUserCompany.setWorkPhone(otherOrigUserCompany.getWorkPhone());
            } else {
                User user = userService.selectByUid(managerId);
                if (user != null) {
                    origUserCompany.setWorkEmail(user.getEmail());
                    origUserCompany.setWorkPhone(user.getMobile());
                }
            }
            if (!isPgCall) {
                //先在此修改部门名称
                PmPg pmPg = pmPgMapper.selectByPgId(pDepId.longValue());
                if (pmPg != null) {
                    pmPg.setPgName(department.getName());
                    pmPgMapper.updateByPrimaryKeySelective(pmPg);
                }
            }
            int ret = departmentMapper.updateByPrimaryKeySelective(department);
            ////origUserCompanyMapper.insertSelective(origUserCompany);
            this.insertUserCompany(origUserCompany, err, false);
            return ret;
        }
    }

    /**
     * 获取某公司的业务员列表
     *
     * @param companyId
     * @return
     */
    @Override
    public List<Map<String, Object>> selectSalesmanListByCompanyId(Integer companyId) {
        return userMapper.selectSalesmanListByCompanyId(companyId);
    }

    /**
     * 根据客户公司名称查询客户公司
     *
     * @param companyId
     * @param cstName
     * @return
     */
    @Override
    public List<Map<String, Object>> selectCstCompanyByCstName(Integer companyId, String cstName) {
        Map<String, Object> map = new HashMap<>();
        map.put("companyId", companyId);
        map.put("cstName", cstName);
        return customerMapper.selectCstCompanyByCstName(map);
    }

    private int updateUserRole(Integer companyId, Integer depId, Long uid, RoleEnum roleEnum) {
        //如果设置DEPARTMENT_LEADER，则要保持本部门该角色的唯一性
        if (roleEnum.getCode().equals(RoleEnum.DEPARTMENT_LEADER.getCode())) {
            List<UserRole> userRoleList = this.selectByCompanyIdAndDepIdAndRoleCode(companyId, depId, roleEnum);
            Long employeeRoleId = this.getRoleId(companyId, RoleEnum.EMPLOYEE);
            for (UserRole userRole : userRoleList) {
                if (!uid.equals(userRole.getUid())) {
                    userRole.setRoleId(employeeRoleId);
                    userRole.setUpdatedAt(new Date());
                    userRoleMapper.updateByPrimaryKeySelective(userRole);
                }
            }
        }
        Long roleId = this.getRoleId(companyId, roleEnum);
        UserRole userRole = this.selectByCompanyIdAndDepIdAndUid(companyId, depId, uid);
        boolean isExists = true;
        if (userRole == null) {
            userRole = new UserRole();
            isExists = false;
        }
        //加入角色
        userRole.setCompanyId(companyId);
        userRole.setUid(uid);
        userRole.setRoleId(roleId);
        userRole.setDepId(depId);
        if (isExists) {
            userRole.setUpdatedAt(new Date());
            return userRoleMapper.updateByPrimaryKeySelective(userRole);
        } else {
            if (1> userRoleMapper.selectRoleExistence(userRole)) {
                return userRoleMapper.insertSelective(userRole);
            }
            return 0;
        }
    }

    /**
     * 邀请内部人员：因为增加事务处理，重新调整
     *
     * @param param
     * @return
     */
    @Override
    public Boolean createInvite10(Map<String, Object> param, StringBuilder err) {
        String clientType = param.get("clientType") == null ? "" : param.get("clientType").toString().trim();
        Integer departmentId = param.get("departmentId") == null || param.get("departmentId").toString().trim().length() < 1 ? null : Integer.parseInt(param.get("departmentId").toString().trim());
        String departmentName = param.get("departmentName") == null ? "" : param.get("departmentName").toString().trim();
        String gender = param.get("gender") == null ? "0" : param.get("gender").toString().trim();
        String inviteeUserName = param.get("inviteeUserName") == null ? "" : param.get("inviteeUserName").toString().trim();
        Integer inviterCompanyId = param.get("inviterCompanyId") == null ? null : Integer.parseInt(param.get("inviterCompanyId").toString());
        String inviterCompanyName = param.get("inviterCompanyName") == null ? "" : param.get("inviterCompanyName").toString().trim();
        String inviterUserId = param.get("inviterUserId") == null ? "" : param.get("inviterUserId").toString().trim();
        String inviterUsername = param.get("inviterUsername") == null ? "" : param.get("inviterUsername").toString().trim();
        String phoneNum = param.get("phoneNum") == null || param.get("phoneNum").toString().trim().length() < 1 ? null : param.get("phoneNum").toString().trim();
        String roleName = param.get("roleName") == null ? "" : param.get("roleName").toString().trim();
        String token = param.get("token") == null ? "" : param.get("token").toString().trim();
        Long uid = param.get("uid") == null ? null : Long.parseLong(param.get("uid").toString());

        if (Long.parseLong(inviterUserId) == uid) {
            err.append("邀请人与被邀请人不能为同一人，失败！");
            return false;
        }
        if (this.selectUserCompanyByUid(inviterCompanyId, Long.parseLong(inviterUserId)) == null) {
            err.append("邀请人与邀请人公司不匹配，失败！");
            return false;
        }
        //微信端有unionId无uid,其它端有uid无unionId
        Boolean isValid = false;

        User user = userService.selectByUid(uid);
        if (user == null) {
            err.append("被邀请人不存在，失败！");
            return false;
        }
        user.setInviteStatus(Byte.parseByte("3"));
        ////OrigUserCompany origUserCompany = this.selectUserCompanyByUid(inviterCompanyId, user.getUid());
        //修改为可以通过邀请进入多个部门
        OrigUserCompany origUserCompany = this.selectUserCompanyByDepIdAndUid(inviterCompanyId, departmentId, user.getUid());
        if (origUserCompany == null) {
            OrigUserCompany oldOrigUserCompany = this.selectUserCompanyByUid(inviterCompanyId, user.getUid());
            //加入公司及部门
            origUserCompany = new OrigUserCompany();
            origUserCompany.setUid(user.getUid());
            origUserCompany.setCompanyId(inviterCompanyId);
            origUserCompany.setDepId(departmentId);
            origUserCompany.setWorkName(inviteeUserName);
            origUserCompany.setWorkPhone(phoneNum);
            origUserCompany.setWorkEmail(oldOrigUserCompany == null ? user.getEmail() : oldOrigUserCompany.getWorkEmail());
            this.insertUserCompany(origUserCompany, new StringBuilder(), false);

           /* Map<String, Object> map = new HashMap<>();
            map.put("company_id", inviterCompanyId);
            //公司内部邀请角色总是普通员工
            map.put("role_code", RoleEnum.EMPLOYEE.getCode());
            //根据 company_id 和 role 的code 查出 role_id
            Long roleId = roleMapper.selectRoleId(map);*/
            this.updateUserRole(inviterCompanyId, departmentId, user.getUid(), RoleEnum.EMPLOYEE);
            userService.updateByPrimaryKeySelective(user);

            isValid = true;
        } else {
            isValid = false;
        }
        if (isValid) {
            //生成邀请记录
            InvitedUser invitedUser = new InvitedUser();
            invitedUser.setHostUid(Long.parseLong(inviterUserId));
            invitedUser.setHostCompany(inviterCompanyId);
            invitedUser.setGuestUid(user.getUid());
            invitedUser.setGuestCompany(inviterCompanyId);
            invitedUser.setTypeCode("10");
            invitedUser.setInviteStatus(Byte.parseByte("3"));
            userService.insertInvitedUserRecord(invitedUser);

            if (userService.selectMyFriend(Long.parseLong(inviterUserId), user.getUid()) == null) {
                MyFriend myFriend = new MyFriend();
                myFriend.setMyUid(Long.parseLong(inviterUserId));
                myFriend.setMyName(inviterUsername);
                myFriend.setFriUid(user.getUid());
                myFriend.setFriName(inviteeUserName);
                myFriend.setFriAvatar(user.getAvatar());
                myFriend.setFriPhone(user.getMobile());
                myFriend.setFriEmail(user.getEmail());
                ////myFriend.setFriComId(inviterCompanyId);
                ////myFriend.setFriComName(inviterCompanyName);
                ////myFriend.setRelation("1");
                userService.insertMyFriend(myFriend);
            }

        } else {
            err.append("邀请公司内部人员(已存在)，失败！");
        }
        return isValid;
    }

    /**
     * 邀请外部人员
     *
     * @param param
     * @param err
     * @return
     */
    @Override
    public Boolean createInvite11(Map<String, Object> param, StringBuilder err) {
        String clientType = param.get("clientType") == null ? "" : param.get("clientType").toString().trim();
        String gender = param.get("gender") == null ? "0" : param.get("gender").toString().trim();
        String inviteeUserName = param.get("inviteeUserName") == null ? "" : param.get("inviteeUserName").toString().trim();
        Integer inviterCompanyId = param.get("inviterCompanyId") == null ? null : Integer.parseInt(param.get("inviterCompanyId").toString());
        String inviterCompanyName = param.get("inviterCompanyName") == null ? "" : param.get("inviterCompanyName").toString().trim();
        String inviterUserId = param.get("inviterUserId") == null ? "" : param.get("inviterUserId").toString().trim();
        String inviterUsername = param.get("inviterUsername") == null ? "" : param.get("inviterUsername").toString().trim();
        String phoneNum = param.get("phoneNum") == null || param.get("phoneNum").toString().trim().length() < 1 ? null : param.get("phoneNum").toString().trim();
        String token = param.get("token") == null ? "" : param.get("token").toString().trim();
        Long uid = param.get("uid") == null ? null : Long.parseLong(param.get("uid").toString());

        if (Long.parseLong(inviterUserId) == uid) {
            err.append("邀请人与被邀请人不能为同一人，失败！");
            return false;
        }
        if (this.selectUserCompanyByUid(inviterCompanyId, Long.parseLong(inviterUserId)) == null) {
            err.append("邀请人与邀请人公司不匹配，失败！");
            return false;
        }
        //微信端有unionId无uid,其它端有uid无unionId
        ////Boolean isValid = false;//, isUpdate = true;

        User user = userService.selectByUid(uid);
        if (user == null) {
            err.append("被邀请人不存在，失败！");
            return false;
        }
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("uid", user.getUid());
        dataMap.put("token", token);

        if (user.getInviteStatus().equals(Byte.parseByte("3")) || user.getInviteStatus().equals(Byte.parseByte("4"))) {
            err.append("邀请公司外部人员（已存在），失败！");
            return false;
        }
        user.setInviteStatus(Byte.parseByte("3"));

        //生成邀请记录
        InvitedUser invitedUser = new InvitedUser();
        invitedUser.setHostUid(Long.parseLong(inviterUserId));
        invitedUser.setHostCompany(inviterCompanyId);
        invitedUser.setGuestUid(user.getUid());
        ////invitedUser.setGuestCompany(inviterCompanyId);
        invitedUser.setTypeCode("11");
        invitedUser.setInviteStatus(Byte.parseByte("3"));
        userService.insertInvitedUserRecord(invitedUser);

        if (userService.selectMyFriend(Long.parseLong(inviterUserId), user.getUid()) == null) {
            MyFriend myFriend = new MyFriend();
            myFriend.setMyUid(Long.parseLong(inviterUserId));
            myFriend.setMyName(inviterUsername);
            myFriend.setFriUid(user.getUid());
            myFriend.setFriName(inviteeUserName);
            myFriend.setFriAvatar(user.getAvatar());
            myFriend.setFriPhone(user.getMobile());
            myFriend.setFriEmail(user.getEmail());
            ////myFriend.setRelation("1");
            userService.insertMyFriend(myFriend);
        }
        userService.updateByPrimaryKeySelective(user);
        return true;
    }

    /**
     * 邀请客户公司
     *
     * @param param
     * @param err
     * @return
     */
    @Override
    public Boolean createInvite20(Map<String, Object> param, StringBuilder err) {
        String clientType = param.get("clientType") == null ? "" : param.get("clientType").toString().trim();
        String address = param.get("address") == null ? "" : param.get("address").toString().trim();
        String contractPerson = param.get("contractPerson") == null ? "" : param.get("contractPerson").toString().trim();
        String contractPhoneNum = param.get("contractPhoneNum") == null ? "" : param.get("contractPhoneNum").toString().trim();
        Long customerCompanyId = param.get("customerCompanyId") == null || param.get("customerCompanyId").toString().trim().length() < 1 ? null : Long.parseLong(param.get("customerCompanyId").toString());
        String customerCompanyName = param.get("customerCompanyName") == null ? "" : param.get("customerCompanyName").toString().trim();
        Integer inviterCompanyId = param.get("inviterCompanyId") == null ? null : Integer.parseInt(param.get("inviterCompanyId").toString());
        String inviterCompanyName = param.get("inviterCompanyName") == null ? "" : param.get("inviterCompanyName").toString().trim();
        String inviterUserId = param.get("inviterUserId") == null ? "" : param.get("inviterUserId").toString().trim();
        String inviterUsername = param.get("inviterUsername") == null ? "" : param.get("inviterUsername").toString().trim();
        String token = param.get("token") == null ? "" : param.get("token").toString().trim();
        Long uid = param.get("uid") == null ? null : Long.parseLong(param.get("uid").toString());

        if (Long.parseLong(inviterUserId) == uid) {
            err.append("邀请人与被邀请人不能为同一人，失败！");
            return false;
        }
        if (inviterCompanyId == customerCompanyId.intValue()) {
            err.append("邀请人公司与被邀请人公司不能为同一公司，失败！");
            return false;
        }
        OrigUserCompany origUserCompany = this.selectUserCompanyByUid(inviterCompanyId, Long.parseLong(inviterUserId));
        if (origUserCompany == null) {
            err.append("邀请人与邀请人公司不匹配，失败！");
            return false;
        }
        User user = userService.selectByUid(uid);
        if (user == null) {
            err.append("被邀请人不存在，失败！");
            return false;
        }
        Boolean isValid = false;
        Company cusCompany = this.selectByCompanyId(customerCompanyId.intValue());
        if (cusCompany == null) {
            cusCompany = new Company();
            cusCompany.setAddress(address);
            cusCompany.setCompanyId(customerCompanyId.intValue());
            cusCompany.setCompanyName(customerCompanyName);
            cusCompany.setCompanyFullName(customerCompanyName);
            customerCompanyId = this.registerCompany(cusCompany).longValue();
            isValid = true;
        }
        Customer customer = this.selectByCompanyIdAndCustomerId(inviterCompanyId, customerCompanyId.intValue());
        if (customer == null) {
            customer = new Customer();
            customer.setCstCompanyId(customerCompanyId);
            customer.setCompanyId(inviterCompanyId.longValue());
            customer.setContactMan(contractPerson);
            customer.setPhone(contractPhoneNum);
            customer.setCstCompanyAddress(address);
            customer.setCstCompanyName(customerCompanyName);
            customer.setInviteStatus(Byte.parseByte("2"));
            customerService.insertCustomer(customer);

            Supplier supplier = this.selectByCompanyIdAndSupplierId(customerCompanyId.intValue(), inviterCompanyId);
            if (supplier == null) {
                supplier = new Supplier();
                supplier.setSupCompanyId(inviterCompanyId.longValue());
                supplier.setSupCompanyName(inviterCompanyName);
                supplier.setCompanyId(customerCompanyId.longValue());
                supplier.setContactMan(inviterUsername);
                supplier.setPhone(origUserCompany.getWorkPhone());
                supplier.setEmail(origUserCompany.getWorkEmail());
                Company company = this.selectByCompanyId(inviterCompanyId);
                supplier.setSupCompanyAddress(company.getAddress());
                supplier.setInviteStatus(Byte.parseByte("2"));
                supplierService.insertSupplier(supplier);
            }
            isValid = true;
        }
        if (isValid) {
            //生成邀请记录
            InvitedUser invitedUser = new InvitedUser();
            invitedUser.setHostUid(Long.parseLong(inviterUserId));
            invitedUser.setHostCompany(inviterCompanyId);
            invitedUser.setGuestUid(user.getUid());
            invitedUser.setGuestCompany(customerCompanyId.intValue());
            invitedUser.setTypeCode("20");
            invitedUser.setInviteStatus(Byte.parseByte("2"));
            userService.insertInvitedUserRecord(invitedUser);

            if (userService.selectMyFriend(Long.parseLong(inviterUserId), user.getUid()) == null) {
                MyFriend myFriend = new MyFriend();
                myFriend.setMyUid(Long.parseLong(inviterUserId));
                myFriend.setMyName(inviterUsername);
                myFriend.setFriUid(user.getUid());
                myFriend.setFriName(user.getUsername());
                myFriend.setFriAvatar(user.getAvatar());
                myFriend.setFriPhone(user.getMobile());
                myFriend.setFriEmail(user.getEmail());
                userService.insertMyFriend(myFriend);
            }
        } else {
            err.append("邀请公司客户(已存在)，失败！");
        }
        return isValid;
    }

    /**
     * 邀请供应商公司
     *
     * @param param
     * @param err
     * @return
     */
    @Override
    public Boolean createInvite30(Map<String, Object> param, StringBuilder err) {
        String clientType = param.get("clientType") == null ? "" : param.get("clientType").toString().trim();
        String address = param.get("address") == null ? "" : param.get("address").toString().trim();
        String contractPerson = param.get("contractPerson") == null ? "" : param.get("contractPerson").toString().trim();
        String contractPhoneNum = param.get("contractPhoneNum") == null ? "" : param.get("contractPhoneNum").toString().trim();
        Integer inviterCompanyId = param.get("inviterCompanyId") == null ? null : Integer.parseInt(param.get("inviterCompanyId").toString());
        String inviterCompanyName = param.get("inviterCompanyName") == null ? "" : param.get("inviterCompanyName").toString().trim();
        String inviterUserId = param.get("inviterUserId") == null ? "" : param.get("inviterUserId").toString().trim();
        String inviterUsername = param.get("inviterUsername") == null ? "" : param.get("inviterUsername").toString().trim();
        Integer invoiceType = param.get("invoiceType") == null || param.get("invoiceType").toString().isEmpty() ? null : Integer.parseInt(param.get("invoiceType").toString());
        String invoiceTypeName = param.get("invoiceTypeName") == null ? "" : param.get("invoiceTypeName").toString().trim();
        Long supplierCompanyId = param.get("supplierCompanyId") == null || param.get("supplierCompanyId").toString().trim().length() < 1 ? null : Long.parseLong(param.get("supplierCompanyId").toString());
        String supplierCompanyName = param.get("supplierCompanyName") == null ? "" : param.get("supplierCompanyName").toString().trim();
        Integer supplyType = param.get("supplyType") == null || param.get("supplyType").toString().isEmpty() ? null : Integer.parseInt(param.get("supplyType").toString());
        String supplyTypeName = param.get("supplyTypeName") == null ? "" : param.get("supplyTypeName").toString().trim();
        BigDecimal taxRate = param.get("taxRate") == null || param.get("taxRate").toString().isEmpty() ? null : new BigDecimal(param.get("taxRate").toString());
        String token = param.get("token") == null ? "" : param.get("token").toString().trim();
        Long uid = param.get("uid") == null ? null : Long.parseLong(param.get("uid").toString());
        ArrayList<Map<String, Object>> matTypeList = (ArrayList<Map<String, Object>>)param.get("mat_type_list");

        if (Long.parseLong(inviterUserId) == uid) {
            err.append("邀请人与被邀请人不能为同一人，失败！");
            return false;
        }
        if (inviterCompanyId == supplierCompanyId.intValue()) {
            err.append("邀请人公司与被邀请人公司不能为同一公司，失败！");
            return false;
        }
        OrigUserCompany origUserCompany = this.selectUserCompanyByUid(inviterCompanyId, Long.parseLong(inviterUserId));
        if (origUserCompany == null) {
            err.append("邀请人与邀请人公司不匹配，失败！");
            return false;
        }
        User user = userService.selectByUid(uid);
        if (user == null) {
            err.append("被邀请人不存在，失败！");
            return false;
        }
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("uid", user.getUid());
        dataMap.put("token", token);

        Boolean isValid = false;
        Company supCompany = this.selectByCompanyId(supplierCompanyId.intValue());
        if (supCompany == null) {
            supCompany = new Company();
            supCompany.setAddress(address);
            supCompany.setCompanyId(supplierCompanyId.intValue());
            supCompany.setCompanyName(supplierCompanyName);
            supCompany.setCompanyFullName(supplierCompanyName);
            supplierCompanyId = this.registerCompany(supCompany).longValue();
            isValid = true;
        }
        Supplier supplier = this.selectByCompanyIdAndSupplierId(inviterCompanyId, supplierCompanyId.intValue());
        if (supplier == null) {
            supplier = new Supplier();
            supplier.setSupCompanyId(supplierCompanyId);
            supplier.setSupCompanyName(supplierCompanyName);
            supplier.setCompanyId(inviterCompanyId.longValue());
            supplier.setContactMan(contractPerson);
            supplier.setPhone(contractPhoneNum);
            supplier.setSupCompanyAddress(address);
            supplier.setTaxRatio(taxRate);
            supplier.setSupplyTypeCode(supplyType);
            supplier.setSupplyType(supplyTypeName);
            supplier.setTaxType(invoiceTypeName);
            supplier.setTaxTypeCode(invoiceType);
            supplier.setInviteStatus(Byte.parseByte("2"));
            supplierService.insertSupplier(supplier);

            //增加供应商物料类型
            if (matTypeList != null) {
                for (Map<String, Object> itemMap : matTypeList) {
                    SupplierWare supplierWare = new SupplierWare();
                    supplierWare.setCompanyId(inviterCompanyId);
                    supplierWare.setSupId(supplierCompanyId);
                    supplierWare.setSupName(supplierCompanyName);
                    supplierWare.setMatType(itemMap.get("mat_type") == null ? null : itemMap.get("mat_type").toString());
                    supplierWare.setMatTypeId(itemMap.get("mat_type_id") == null ? null : Long.valueOf(itemMap.get("mat_type_id").toString()));
                    supplierWare.setMatTypeName(itemMap.get("mat_type_name") == null ? null : itemMap.get("mat_type_name").toString());
                    supplierWare.setUnit(itemMap.get("unit") == null ? null : itemMap.get("unit").toString());
                    supplierWare.setPurveyTerm(itemMap.get("purvey_term") == null ? null : itemMap.get("purvey_term").toString());
                    supplierWare.setHandlerId(uid);
                    supplierWareMapper.insertSelective(supplierWare);
                }
            }
            isValid = true;
        }
        //将邀请公司加入被敫公司的客户
        Customer customer = this.selectByCompanyIdAndCustomerId(supplierCompanyId.intValue(), inviterCompanyId);
        if (customer == null) {
            Company company = this.selectByCompanyId(inviterCompanyId);
            customer = new Customer();
            customer.setCstCompanyId(inviterCompanyId.longValue());
            customer.setCstCompanyName(inviterCompanyName);
            customer.setCstCompanyAddress(company.getAddress());
            customer.setCompanyId(supplierCompanyId);
            customer.setContactMan(inviterUsername);
            customer.setPhone(origUserCompany.getWorkName());
            customer.setEmail(origUserCompany.getWorkEmail());
            ////customer.setEmail(company.getEmail());
            customer.setInviteStatus(Byte.valueOf("2"));
            customerMapper.insertSelective(customer);
        }
        if (isValid) {
            //生成邀请记录
            InvitedUser invitedUser = new InvitedUser();
            invitedUser.setHostUid(Long.parseLong(inviterUserId));
            invitedUser.setHostCompany(inviterCompanyId);
            invitedUser.setGuestUid(user.getUid());
            invitedUser.setGuestCompany(supplierCompanyId.intValue());
            invitedUser.setTypeCode("30");
            invitedUser.setInviteStatus(Byte.parseByte("2"));
            userService.insertInvitedUserRecord(invitedUser);

            if (userService.selectMyFriend(Long.parseLong(inviterUserId), user.getUid()) == null) {
                MyFriend myFriend = new MyFriend();
                myFriend.setMyUid(Long.parseLong(inviterUserId));
                myFriend.setMyName(inviterUsername);
                myFriend.setFriUid(user.getUid());
                myFriend.setFriName(user.getUsername());
                myFriend.setFriAvatar(user.getAvatar());
                myFriend.setFriPhone(user.getMobile());
                myFriend.setFriEmail(user.getEmail());
                ////myFriend.setFriComId(supplierCompanyId.intValue());
                ////myFriend.setFriComName(supplierCompanyName);
                ////myFriend.setRelation("3");
                userService.insertMyFriend(myFriend);
            }
        } else {
            err.append("邀请公司供应商(已存在)，失败！");
        }
        return isValid;
    }

    /**
     * 新增部门
     *
     * @param param
     * @return: 返回 null 则失败
     */
    @Override
    public Map<String, Object> addDept(Map param, StringBuilder err, boolean isPgCall) {
        String pComment = param.get("comment") == null ? "" : param.get("comment").toString().trim();
        Integer pCompanyId = param.get("company_id") == null ? null : Integer.parseInt(param.get("company_id").toString());
        Integer pUpperId = param.get("upper_id") == null ? null : Integer.parseInt(param.get("upper_id").toString());
        String pDepName = param.get("dep_name") == null ? "" : param.get("dep_name").toString().trim();
        Long pManager = param.get("manager") == null ? null : Long.parseLong(param.get("manager").toString());
        String pManagerName = param.get("manager_name") == null ? null : param.get("manager_name").toString();
        String pNameEn = param.get("name_en") == null ? "" : param.get("name_en").toString().trim();
        Long pUid = param.get("uid") == null || param.get("uid").toString().trim().isEmpty() ? null : Long.valueOf(param.get("uid").toString());

        Department department = this.selectByDepName(pCompanyId, pDepName);
        if (department == null) {
            department = new Department();
            department.setComment(pComment);
            department.setCompanyId(pCompanyId);
            department.setName(pDepName);
            department.setNameEn(pNameEn);
            department.setHandlerId(pUid);
            if (pManager != null) {
                department.setManager(pManager.toString());
                department.setManagerName(pManagerName);
            }
            if (pUpperId != null) {
                Department upperDept = departmentMapper.selectByPrimaryKey(pUpperId);
                if (upperDept == null) {
                    err.append("上级部门不存在");
                    return null;
                } else {
                    if (!upperDept.getCompanyId().equals(pCompanyId)) {
                        err.append("上级部门不属于该公司");
                        return null;
                    }
                    department.setUpperId(pUpperId);
                    department.setUpper(upperDept.getName());
                    department.setUpperEn(upperDept.getNameEn());
                }
            }
            Integer maxDepId = this.createNewId(IdType.DEPARTMENT).intValue();
            department.setDepId(maxDepId);
            this.insertDepartment(department, err, isPgCall);

            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("dep_name", pDepName);
            dataMap.put("dep_id", maxDepId);
            return dataMap;
        } else {
            err.append("同名部门已经存在");
            return null;
        }
    }

    @Override
    public List<OrigUserCompany> selectValidUserCompanyByDepIdAndUid(Integer companyId, Integer depId, Long uid) {
        Map<String, Object> map = new HashMap<>();
        map.put("companyId", companyId);
        map.put("depId", depId);
        map.put("uid", uid);
        return origUserCompanyMapper.selectValidUserCompanyByDepIdAndUid(map);
    }

    @Override
    public List<PmPgDevice> selectValidByPgIdAndDeviceId(Long pgId, Long deviceId) {
        Map<String, Object> map = new HashMap<>();
        map.put("pgId", pgId);
        map.put("deviceId", deviceId);
        return pmPgDeviceMapper.selectValidByPgIdAndDeviceId(map);
    }

    @Override
    public List<Department> selectValidByUpperDepId(Integer companyId, Integer depId) {
        Map<String, Object> map = new HashMap<>();
        map.put("companyId", companyId);
        map.put("depId", depId);
        return departmentMapper.selectValidUserCompanyByDepIdAndUid(map);
    }

    /**
     * 系统默认一级部门不允许删除；
     * 除如之外其它部门，没有成员及子部门的可以删除，有成员或者子部门不能删除；
     *
     * @param depId
     * @return < 0 失败
     * >=0 成功
     */
    @Override
    public int deleteDepartment(Integer depId, Long handlerId, StringBuilder err, boolean isPgCall) {
        //部门与生产小组信息及其人员信息要进行同步更新
        Department department = departmentMapper.selectByPrimaryKey(depId);
        if (department == null) {
            err.append("部门不存在");
            return -1;
        }
        if (department.getDepCode() != null && !department.getDepCode().trim().isEmpty() && department.getUpperId() == null) {
            err.append("默认一级部门不能删除");
            return -2;
        } else {
            ////OrigUserCompany origUserCompany = this.selectUserCompanyByDepIdAndUid(department.getCompanyId(), depId, null);
            List<OrigUserCompany> origUserCompanyList = this.selectValidUserCompanyByDepIdAndUid(department.getCompanyId(), depId, null);
            if (origUserCompanyList != null && origUserCompanyList.size() > 0) {
                err.append("该部门还有成员，不能删除");
                return -3;
            }
            List<PmPgDevice> pmPgDeviceList = this.selectValidByPgIdAndDeviceId(department.getDepId().longValue(), null);
            if (pmPgDeviceList != null && pmPgDeviceList.size() > 0) {
                err.append("该部门还有设备资源，不能删除");
                return -4;
            }
            ////List<Department> deptList = this.selectByUpperDepId(department.getCompanyId(), depId);
            List<Department> deptList = this.selectValidByUpperDepId(department.getCompanyId(), depId);
            if (deptList != null && deptList.size() > 0) {
                err.append("该部门还有子部门，不能删除");
                return -5;
            }
        }
        //判断是否Pg调用
        if (!isPgCall) {
            PmPg pmPg = pmPgMapper.selectByPgId(depId.longValue());
            if (pmPg != null) {
                //无效
                pmPg.setAvailStatus("0");
                pmPg.setHandlerId(handlerId);
                pmPg.setUpdatedAt(new Date());
                pmPgMapper.updateByPrimaryKeySelective(pmPg);
            }
        }
        //无效
        department.setIsActive(false);
        department.setHandlerId(handlerId);
        department.setUpdatedAt(new Date());
        return departmentMapper.updateByPrimaryKeySelective(department);
    }


    /**
     * 删除部门成员。
     * 因为删除怕影响该人之前所做的业务的查询统计，所以现在只是设置其状态为离职，有更合理的方式以后再更改
     *
     * @param companyId
     * @param depId
     * @param memberId  部门成员
     * @param handlerId 操作员
     * @param err       出错信息
     * @param isPgCall: true - Pg 调用，false - 非Pg 调用
     * @return
     */
    @Override
    public int deleteUserCompany(Integer companyId, Integer depId, Long memberId, Long handlerId, StringBuilder err, boolean isPgCall) {
        //部门与生产小组信息及其人员信息要进行同步更新
        OrigUserCompany origUserCompany = this.selectUserCompanyByDepIdAndUid(companyId, depId, memberId);
        if (origUserCompany == null) {
            origUserCompany = this.selectUserCompanyByDepIdAndUid(companyId, null, memberId);
            if (origUserCompany == null) {
                err.append("人员不存在该公司");
                return -1;
            }
            err.append("人员不存在该部门");
            return -1;
        }
        if (!isPgCall) {
            PmPgMember pmPgMember = this.selectByPgIdAndMemberId(depId.longValue(), memberId);
            if (pmPgMember != null) {
                if ("1".equals(pmPgMember.getMemberStatus())) {
                    err.append("该人员正在加工中，不能删除");
                    return -1;
                }
                //失效
                pmPgMember.setMemberStatus("4");
                pmPgMember.setHandlerId(handlerId);
                pmPgMember.setUpdatedAt(new Date());
                pmPgMemberMapper.updateByPrimaryKeySelective(pmPgMember);
            }
        }
        //目前设置为失效，不能删除
        origUserCompany.setWorkState("3");
        origUserCompany.setHandlerId(handlerId);
        origUserCompany.setUpdatedAt(new Date());

        //删除部门Manager设置
        Department department = this.selectByDepId(depId);
        if (department != null && department.getManager() != null
                && !department.getManager().trim().isEmpty() && Long.valueOf(department.getManager()).equals(memberId)) {
            department.setManager(null);
            department.setManagerName(null);
            departmentMapper.updateByPrimaryKey(department);
        }
        //在此有意作物理删除user_role
        UserRole userRole = this.selectByCompanyIdAndDepIdAndUid(companyId, depId, memberId);
        if (userRole != null) {
            userRoleMapper.deleteByPrimaryKey(userRole.getId());
        }
        return origUserCompanyMapper.updateByPrimaryKeySelective(origUserCompany);
    }

    /**
     * 查询某部门包括其所有子部门的全部在职成员
     *
     * @param companyId
     * @param depId
     * @return
     */
    @Override
    public List<Map<String, Object>> selectAllAndSubUserCompanyByDepId(Integer companyId, Integer depId, String depCode) {
        Map<String, Object> map = new HashMap<>();
        map.put("companyId", companyId);
        map.put("depId", depId);
        map.put("depCode", depCode);
        if ((depId == null || depId.equals(0)) && (depCode == null || depCode.trim().isEmpty())) {
            depId = null;

        } else {
            Department department = departmentMapper.selectByDepCodeIdAndDepCode(map);
        /*if (depId == null || depId.equals(0)) {
            Department productionDept = this.selectProductionDept(companyId);
            if (productionDept != null) { depId = productionDept.getDepId(); }
        }*/
            if (department != null) {
                depId = department.getDepId();
            } else {
                return null;
            }
        }

        map.put("depId", depId);
        return companyMapper.selectAllAndSubUserCompanyByDepId(map);
    }

    /**
     * 查询客户公司联系人列表
     *
     * @param companyId
     * @param cstCompanyId
     * @param type:        0默认联系人，1其它联系人，2全部联系人
     * @return
     */
    @Override
    public List<Map<String, Object>> selectCstContactorByCustomerId(Integer companyId, Integer cstCompanyId, String type) {
        ArrayList retList = new ArrayList();
        Map<String, Object> map = new HashMap<>();
        if ("0".equals(type)) {
            Customer customer = this.selectByCompanyIdAndCustomerId(companyId, cstCompanyId);
            map.put("connect_man", customer.getContactMan());
            map.put("duty", "默认");
            map.put("email", customer.getEmail());
            map.put("phone", customer.getPhone());
            map.put("is_default", "1");
            retList.add(map);
        }
        if ("1".equals(type)) {
            retList = (ArrayList) customerMapper.selectContactorByCustomerId(cstCompanyId);
        }
        if ("2".equals(type)) {
            Customer customer = this.selectByCompanyIdAndCustomerId(companyId, cstCompanyId);
            map.put("connect_man", customer.getContactMan());
            map.put("duty", "默认");
            map.put("email", customer.getEmail());
            map.put("phone", customer.getPhone());
            map.put("is_default", "1");
            retList.add(map);
            retList.add(customerMapper.selectContactorByCustomerId(cstCompanyId));
        }
        return retList;
    }

    @Override
    public List<SupplierWare> selectBySupCompanyId(Integer companyId, Integer supCompanyId) {
        Map<String, Object> map = new HashMap<>();
        map.put("company_id", companyId);
        map.put("sup_company_id", supCompanyId);
        return supplierWareMapper.selectBySupCompanyId(map);
    }

    @Override
    public  int updateSupplierWare(List<SupplierWare> supplierWareList) {
        if (supplierWareList == null) {
            throw new IllegalArgumentException("没有供应商供货类型数据");
        }
        for(SupplierWare supplierWare: supplierWareList) {
            if (supplierWare.getId() == null || supplierWare.getId() < 1) {
                supplierWareMapper.insertSelective(supplierWare);
            } else {
                supplierWareMapper.updateByPrimaryKeySelective(supplierWare);
            }
        }
        return 1;
    }
}