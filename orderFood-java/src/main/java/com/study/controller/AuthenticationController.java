package com.study.controller;

import com.alibaba.fastjson.JSONObject;
import com.common.bean.*;
import com.common.annotation.NoLogin;
import com.common.bean.User;
import com.common.util.JWTUtil;
import com.common.util.UserContextHolder;
import com.study.service.AuthenticationService;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.*;

/**
 * 鉴权controller
 */
@RestController
@RequestMapping("authority")
public class AuthenticationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationController.class);

    @Value("${study.wx.appId:}")
    public String appId;

    @Value("${study.wx.appSecret:}")
    public String appSecret;

    @Value("${study.wx.requestUrl:}")
    public String requestUrl;

    @Autowired
    private AuthenticationService authenticationService;

    /**
     * 微信小程序登录接口
     * @param code
     * @return
     */
    @NoLogin
    @PostMapping("/wxLogin")
    public CommonResult wxLogin(@RequestParam String code){
        Map<String, String> requestUrlParam = new HashMap<>();
        //小程序appId
        requestUrlParam.put("appid", appId);
        //小程序secret
        requestUrlParam.put("secret", appSecret);
        //小程序端返回的code
        requestUrlParam.put("js_code", code);
        //默认参数
        requestUrlParam.put("grant_type", "authorization_code");
        //发送post请求读取调用微信接口获取openid用户唯一标识
        JSONObject jsonObject = JSONObject.parseObject(doPost(requestUrl, requestUrlParam));
        String openid = jsonObject.getString("openid");
        if (jsonObject!=null && openid!=null && !"".equals(openid)){
            User user = authenticationService.searchDataByOpenid(openid);
            Date date = new Date();
            if (user==null){
                user = new User();
                user.setCreatetime(date);
                user.setLastlogintime(date);
                user.setId(UUID.randomUUID().toString());
                user.setOpenid(openid);
                user.setScore(0);
                authenticationService.addData(user);
            }else{
                user.setLastlogintime(date);
                authenticationService.updateData(user);
            }
            user.setName("default");
            //返回token
            String token = JWTUtil.sign(user);
            user.setToken(token);
            return CommonResult.success(user);
        }
        return CommonResult.failed();
    }

    private String doPost(String url, Map<String, String> param) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost(url);
            // 创建参数列表
            if (param != null) {
                List<NameValuePair> paramList = new ArrayList<>();
                for (String key : param.keySet()) {
                    paramList.add(new BasicNameValuePair(key, param.get(key)));
                }
                // 模拟表单
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(paramList);
                httpPost.setEntity(entity);
            }
            // 执行http请求
            response = httpClient.execute(httpPost);
            resultString = EntityUtils.toString(response.getEntity(), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultString;
    }

    /**
     * 分页获取角色
     *
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("/getRolePage")
    public CommonResult getRolePage(@RequestParam int page, @RequestParam int limit) {
        int start = limit * page - limit;
        Map dataMap = new HashMap();
        dataMap.put("start", start);
        dataMap.put("limit", limit);
        List<Role> dataList = authenticationService.getRolePage(dataMap);
        int count = authenticationService.getRoleCount();
        return CommonResult.success(dataList, count);
    }

    /**
     * 获取所有角色
     *
     * @return
     */
    @GetMapping("/getRoleAll")
    public CommonResult getRoleAll() {
        List<Role> dataList = authenticationService.getRoleAll();
        return CommonResult.success(dataList);
    }


    /**
     * 获取菜单
     * @return
     */
    @GetMapping("/getMenu")
    public CommonResult getMenu() {
        List<Menu> dataList = authenticationService.getMenu();
        return CommonResult.success(dataList);
    }

    /**
     * 获取所有顶级菜单
     *
     * @return
     */
    @GetMapping("/getTopLevelMenuAll")
    public CommonResult getTopLevelMenuAll() {
        List<Menu> dataList = authenticationService.getTopLevelMenuAll();
        return CommonResult.success(dataList);
    }

    /**
     * 获取菜单树
     *
     * @return
     */
    @GetMapping("/getMenuTree")
    public CommonResult getMenuTree() {
        List<MenuTree> rtnList = new ArrayList<MenuTree>();
        List<Menu> dataList = authenticationService.getTopLevelMenuAll();
        authenticationService.setMenuTree(rtnList, dataList);
        return CommonResult.success(rtnList);
    }

    /**
     * 根据角色id获取角色的菜单权限
     *
     * @param roleid
     * @return
     */
    @GetMapping("/getRoleMenu")
    public CommonResult getRoleMenu(@RequestParam String roleid) {
        List<RoleMenu> dataList = authenticationService.getRoleMenu(roleid, RoleMenu.ALL_SELECT);
        List<String> rtnList = new ArrayList<String>();
        for (RoleMenu roleMenu : dataList) {
            rtnList.add(roleMenu.getMenuid());
        }
        return CommonResult.success(rtnList);
    }


    /**
     * 分页获取用户信息
     *
     * @param page
     * @param limit
     * @return
     */
    @GetMapping("/getUserPage")
    public CommonResult getUserPage(@RequestParam int page, @RequestParam int limit) {
        int start = limit * page - limit;
        Map dataMap = new HashMap();
        dataMap.put("start", start);
        dataMap.put("limit", limit);
        List<User> dataList = authenticationService.getUserPage(dataMap);
        int count = authenticationService.getUserCount();
        return CommonResult.success(dataList, count);
    }

    /**
     * 登录
     *
     * @param user
     * @return
     */
    @NoLogin
    @PostMapping("/login")
    public CommonResult login(@RequestBody User user) {
        User curUser = authenticationService.getUserByUsernamePassword(user);
        if (curUser == null) {
            return CommonResult.failed("账号或密码错误");
        }
        //更新最后登录时间
        authenticationService.updateLastLoginTime(curUser.getId(), new Date());
        //返回token
        String token = JWTUtil.sign(curUser);
        curUser.setToken(token);
        return CommonResult.success(curUser);
    }



    /**
     * 获取用户的菜单树权限
     *
     * @return
     */
    @GetMapping("/getUserMenuList")
    public CommonResult getUserMenuList() {
        User curUser = UserContextHolder.getInstance();
        //获取用户的菜单树权限
        List<Menu> dataList = authenticationService.getTopLevelMenuByUser(curUser.getId());
        List<MenuTree> rtnList = new ArrayList<MenuTree>();
        authenticationService.setMenuTreeByUser(rtnList, dataList,curUser.getId());
        return CommonResult.success(rtnList);
    }


    /**
     * 获取部门树
     *
     * @return
     */
    @GetMapping("/getDepartmentTree")
    public CommonResult getDepartmentTree() {
        List<Department> rtnList = authenticationService.getDepartmentTree();
        return CommonResult.success(rtnList);
    }
}
