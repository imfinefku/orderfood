package com.study.dao;

import com.study.bean.specific.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface SpecificMapper {

    int addUserLevel(UserLevel userLevel);

    int updateUserLevel(UserLevel userLevel);

    int deleteUserLevel(UserLevel userLevel);

    List<UserLevel> getUserLevelByPage(Map dataMap);

    int getUserLevelByPageCount();

    int addFoodType(FoodType foodType);

    int updateFoodType(FoodType foodType);

    int deleteFoodType(FoodType foodType);

    List<FoodType> getFoodTypeByPage(Map dataMap);

    int getFoodTypeByPageCount();

    int addFood(Food food);

    int updateFood(Food food);

    int deleteFood(Food food);

    List<Food> getFoodByPage(Map dataMap);

    int getFoodByPageCount();

    List<FoodType> getFoodTypeAll();

    List<FoodType> getWxFoodTypeAll();

    List<Food> getWxFoodAll();

    int addOrder(Order order);

    int addOrderInfo(List<OrderInfo> orderInfoList);

    int updateUserScore(@Param("score") Integer score,@Param("id") String id);

    int getSoldByFoodId(@Param("foodid") String foodid);

    List<UserLevel> getUserLevelAll();

    int getUserScore(@Param("id") String id);

    List<Order> getUserOrder(@Param("id") String id);

    List<OrderInfo> getOrderInfoByOrderId(@Param("orderid") String orderid);

    int addAddress(Address address);

    int updateAddress(Address address);

    List<Address> getAddress(@Param("userid") String userid);

    Order getOrderById(@Param("id") String id);

    List<Order> getOrderByPage(Map dataMap);

    int getOrderByPageCount();

    int updateOrderStatus(@Param("id") String id,@Param("status") String status);
}