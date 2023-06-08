package com.study.service;

import com.study.bean.specific.*;
import com.common.util.UserContextHolder;
import com.study.dao.SpecificMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SpecificService {

    @Autowired
    private SpecificMapper mapper;


    public List<UserLevel> getUserLevelByPage(Map dataMap){
        return mapper.getUserLevelByPage(dataMap);
    }

    public int getUserLevelByPageCount(){
        return mapper.getUserLevelByPageCount();
    }

    public List<FoodType> getFoodTypeByPage(Map dataMap){
        return mapper.getFoodTypeByPage(dataMap);
    }

    public int getFoodTypeByPageCount(){
        return mapper.getFoodTypeByPageCount();
    }

    public List<Food> getFoodByPage(Map dataMap){
        return mapper.getFoodByPage(dataMap);
    }

    public int getFoodByPageCount(){
        return mapper.getFoodByPageCount();
    }

    public List<FoodType> getFoodTypeAll(){
        return mapper.getFoodTypeAll();
    }

    public List<FoodType> getWxFoodTypeAll(){
        return mapper.getWxFoodTypeAll();
    }

    public List<Food> getWxFoodAll(){
        return mapper.getWxFoodAll();
    }


    public int getSoldByFoodId(String foodid){
        return mapper.getSoldByFoodId(foodid);
    }

    public Map getUserLevel(){
        Map rtnMap = new HashMap();
        List<UserLevel> userLevelList = mapper.getUserLevelAll();
        System.out.println(UserContextHolder.getInstance().getId());
        int score = mapper.getUserScore(UserContextHolder.getInstance().getId());
        rtnMap.put("curVipName",userLevelList.get(userLevelList.size()-1).getName());
        rtnMap.put("curVipSize",userLevelList.size()-1);
        rtnMap.put("vipLevel",userLevelList);
        return rtnMap;
    }

    public List<Order> getUserOrder(){
        List<Order> orderList = mapper.getUserOrder(UserContextHolder.getInstance().getId());
        return orderList;
    }

    public List<Address> getAddress(){
        return mapper.getAddress(UserContextHolder.getInstance().getId());
    }

    public Order getOrderById(String id){
        Order order = mapper.getOrderById(id);
        return order;
    }

    public List<Order> getOrderByPage(Map dataMap){
        List<Order> dataList = mapper.getOrderByPage(dataMap);
        return dataList;
    }

    public int getOrderByPageCount(){
        return mapper.getOrderByPageCount();
    }

}
