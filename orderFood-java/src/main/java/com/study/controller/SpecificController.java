package com.study.controller;

import com.study.bean.specific.*;
import com.common.bean.CommonResult;
import com.common.util.NumberID;
import com.common.util.UserContextHolder;
import com.study.service.SpecificService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("specific")
public class SpecificController {

    @Autowired
    private SpecificService service;

    /**
     * 分页获取会员等级
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("/getUserLevelByPage")
    public CommonResult getUserLevelByPage(@RequestParam int page, @RequestParam int limit){
        int start = limit * page - limit;
        Map dataMap = new HashMap();
        dataMap.put("start", start);
        dataMap.put("limit", limit);
        List<UserLevel> dataList = service.getUserLevelByPage(dataMap);
        int count = service.getUserLevelByPageCount();
        return CommonResult.success(dataList, count);
    }

    /**
     * 分页获取菜品种类
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("/getFoodTypeByPage")
    public CommonResult getFoodTypeByPage(@RequestParam int page, @RequestParam int limit){
        int start = limit * page - limit;
        Map dataMap = new HashMap();
        dataMap.put("start", start);
        dataMap.put("limit", limit);
        List<FoodType> dataList = service.getFoodTypeByPage(dataMap);
        int count = service.getFoodTypeByPageCount();
        return CommonResult.success(dataList, count);
    }


    /**
     * 分页获取菜品
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("/getFoodByPage")
    public CommonResult getFoodByPage(@RequestParam int page, @RequestParam int limit){
        int start = limit * page - limit;
        Map dataMap = new HashMap();
        dataMap.put("start", start);
        dataMap.put("limit", limit);
        List<Food> dataList = service.getFoodByPage(dataMap);
        int count = service.getFoodByPageCount();
        return CommonResult.success(dataList, count);
    }

    /**
     * 获取所有菜品种类
     * @return
     */
    @GetMapping("/getFoodTypeAll")
    public CommonResult getFoodTypeAll(){
        List<FoodType> dataList = service.getFoodTypeAll();
        return CommonResult.success(dataList);
    }

    /**
     * wx获取所有菜品种类
     * @return
     */
    @GetMapping("/getWxFoodTypeAll")
    public CommonResult getWxFoodTypeAll(){
        List<FoodType> typeList = service.getWxFoodTypeAll();
        List<Food> foodList = service.getWxFoodAll();
        List<Map> rtnList = new ArrayList<Map>();
        for (FoodType foodType : typeList){
            Map foodTypeMap = new HashMap();
            foodTypeMap.put("id",foodType.getId());
            foodTypeMap.put("name",foodType.getName());
            foodTypeMap.put("type","0");
            rtnList.add(foodTypeMap);
            for (Food food : foodList){
                if (foodType.getId().equals(food.getTypeid())){
                    int sold = service.getSoldByFoodId(food.getId());
                    Map foodMap = new HashMap();
                    foodMap.put("id",food.getId());
                    foodMap.put("typeid",food.getTypeid());
                    foodMap.put("name",food.getName());
                    foodMap.put("image",food.getImage());
                    foodMap.put("price",food.getPrice());
                    foodMap.put("sold",sold);
                    foodMap.put("type","1");
                    rtnList.add(foodMap);
                }
            }
        }
        Map rtnMap = new HashMap();
        rtnMap.put("typeData",typeList);
        rtnMap.put("foodData",rtnList);
        return CommonResult.success(rtnMap);
    }


    /**
     * 获取用户等级
     * @return
     */
    @GetMapping("/getUserLevel")
    public CommonResult getUserLevel(){
        return CommonResult.success(service.getUserLevel());
    }

    /**
     * 获取用户订单
     * @return
     */
    @GetMapping("/getUserOrder")
    public CommonResult getUserOrder(){
        List<Order> orderList = service.getUserOrder();
        return CommonResult.success(orderList);
    }

    /**
     * 获取我的地址
     * @return
     */
    @GetMapping("/getAddress")
    public CommonResult getAddress(){
        return CommonResult.success(service.getAddress());
    }

    /**
     * 查询食品
     * @return
     */
    @GetMapping("/getFoodSearch")
    public CommonResult getFoodSearch(@RequestParam String searchWord){
        List<Food> foodList = service.getWxFoodAll();
        List<Map> rtnList = new ArrayList<Map>();
        for (Food food : foodList){
            if (food.getName().contains(searchWord)){
                int sold = service.getSoldByFoodId(food.getId());
                Map foodMap = new HashMap();
                foodMap.put("id",food.getId());
                foodMap.put("typeid",food.getTypeid());
                foodMap.put("name",food.getName());
                foodMap.put("image",food.getImage());
                foodMap.put("price",food.getPrice());
                foodMap.put("sold",sold);
                rtnList.add(foodMap);
            }
        }
        return CommonResult.success(rtnList);
    }

    /**
     * 获取订单详细
     * @param id
     * @return
     */
    @GetMapping("/getOrderById")
    public CommonResult getOrderById(@RequestParam String id){
        return CommonResult.success(service.getOrderById(id));
    }

    /**
     * 分页获取订单数据
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("/getOrderByPage")
    public CommonResult getOrderByPage(@RequestParam int page, @RequestParam int limit){
        int start = limit * page - limit;
        Map dataMap = new HashMap();
        dataMap.put("start", start);
        dataMap.put("limit", limit);
        List<Order> dataList = service.getOrderByPage(dataMap);
        int count = service.getOrderByPageCount();
        return CommonResult.success(dataList, count);
    }

}
