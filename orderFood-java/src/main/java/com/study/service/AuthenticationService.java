package com.study.service;

import com.common.bean.*;
import com.study.dao.AuthenticationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationMapper authenticationMapper;

    public boolean addData(User user){
        int result = authenticationMapper.addData(user);
        if (result > 0){
            return true;
        }
        return false;
    }

    public User searchDataByOpenid(String openid){
        User user = authenticationMapper.searchDataByOpenid(openid);
        return user;
    }

    public void updateData(User user){
        authenticationMapper.updateData(user);
    }


    public List<Role> getRolePage(Map dataMap) {
        return authenticationMapper.getRolePage(dataMap);
    }

    public int getRoleCount() {
        return authenticationMapper.getRoleCount();
    }

    public List<Role> getRoleAll() {
        return authenticationMapper.getRoleAll();
    }

    public List<Menu> getMenu() {
        List<Menu> allMenuList = authenticationMapper.getMenu();
        List<Menu> topMenuList = new ArrayList<Menu>();
        setChildrenMenu(topMenuList, allMenuList);
        return topMenuList;
    }

    private void setChildrenMenu(List<Menu> topMenuList, List<Menu> allMenuList) {
        //给所有上级菜单赋予子级菜单
        for (Menu menu : topMenuList) {
            List<Menu> children = new ArrayList<Menu>();
            for (Menu childMenu : allMenuList) {
                children.add(childMenu);
            }
        }
    }

    public List<Menu> getTopLevelMenuAll() {
        return authenticationMapper.getTopLevelMenuAll();
    }

    public void setMenuTree(List<MenuTree> rtnList, List<Menu> dataList) {
        for (Menu menu : dataList) {
            MenuTree menuTree = new MenuTree();
            menuTree.setId(menu.getId());
            menuTree.setName(menu.getName());
            menuTree.setRoute(menu.getRoute());
            menuTree.setChildren(getChildrenMenuTree(menuTree.getId()));
            rtnList.add(menuTree);
        }
    }

    public void setMenuTreeByUser(List<MenuTree> rtnList, List<Menu> dataList,String userid) {
        for (Menu menu : dataList) {
            MenuTree menuTree = new MenuTree();
            menuTree.setId(menu.getId());
            menuTree.setName(menu.getName());
            menuTree.setRoute(menu.getRoute());
            rtnList.add(menuTree);
        }
    }

    public List<MenuTree> getChildrenMenuTree(String masterid) {
        return authenticationMapper.getChildrenMenuTree(masterid);
    }

    public List<MenuTree> getChildrenMenuTreeByUser(String masterid,String userid) {
        return authenticationMapper.getChildrenMenuTreeByUser(masterid,userid);
    }

    public List<RoleMenu> getRoleMenu(String roleid, int status) {
        return authenticationMapper.getRoleMenu(roleid, status);
    }


    public List<User> getUserPage(Map dataMap) {
        List<User> userList = authenticationMapper.getUserPage(dataMap);
        return userList;
    }

    public int getUserCount() {
        return authenticationMapper.getUserCount();
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void deleteUser(String id) {
        authenticationMapper.deleteUser(id);
        authenticationMapper.deleteUserRole(id);
    }

    public User getUserByUsernamePassword(User user) {
        return authenticationMapper.getUserByUsernamePassword(user);
    }

    public int updateLastLoginTime(String id, Date lastlogintime) {
        return authenticationMapper.updateLastLoginTime(id, lastlogintime);
    }

    public List<Menu> getTopLevelMenuByUser(String userid) {
        return authenticationMapper.getTopLevelMenuByUser(userid);
    }


    public User getUserById(String id) {
        return authenticationMapper.getUserById(id);
    }

    public boolean checkUsernameRepeat(String username) {
        int count = authenticationMapper.checkUsernameRepeat(username);
        return count > 0 ? true : false;
    }

    public List<Department> getDepartmentTree() {
        List<Department> allDepartmentList = authenticationMapper.getDepartmentAll();
        List<Department> topDepartmentList = new ArrayList<Department>();
        //获取所有顶级部门
        for (Department department : allDepartmentList) {
            if (department.getMasterid() == null || "".equals(department.getMasterid())) {
                topDepartmentList.add(department);
            }
        }
        setChildrenDepartment(topDepartmentList, allDepartmentList);
        return topDepartmentList;
    }

    private void setChildrenDepartment(List<Department> topDepartmentList, List<Department> allDepartmentList) {
        //给所有上级部门赋予子级部门
        for (Department department : topDepartmentList) {
            List<Department> children = new ArrayList<Department>();
            for (Department childDepartment : allDepartmentList) {
                if (childDepartment.getMasterid() != null && department.getId().equals(childDepartment.getMasterid())) {
                    children.add(childDepartment);
                }
            }
        }
    }
}
