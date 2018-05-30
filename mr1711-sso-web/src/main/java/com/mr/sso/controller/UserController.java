package com.mr.sso.controller;

import com.mr.model.TbUser;
import com.mr.sso.service.UserService;
import com.mr.util.CookieUtils;
import com.mr.util.DataResult;
import com.mr.util.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * Created by ydd on 2018/5/16.
 */
@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Value("${COOKIETOKENKEY}")
    private String COOKIETOKENKEY;

    /**
     * 1.1.1.检查数据是否可用
     *
     * url: http://localhost:8087/user/check/{param}/{type}
     * get
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/user/check/{param}/{type}",method = RequestMethod.GET)
    public DataResult check(@PathVariable String param ,@PathVariable Integer type){
        DataResult dataResult = userService.checkUser(param, type);
        return dataResult;
    }


    /**
     * http://localhost:8087/user/register
     */
    @ResponseBody
    @RequestMapping(value = "/user/register",method = RequestMethod.POST)
    public DataResult register(TbUser user){
        DataResult dataResult = userService.register(user);
        return dataResult;
    }

    //http://localhost:8087/user/login
    @ResponseBody
    @RequestMapping(value = "/user/login",method = RequestMethod.POST)
    public DataResult login(String username , String password,
                            HttpServletRequest request , HttpServletResponse response){

        DataResult result = userService.login(username, password);
        if(result.getStatus() == 200){
            //将token存放在cookie中
            CookieUtils.setCookie(request, response , COOKIETOKENKEY,result.getData().toString(),true);
        }
        return result;
    }


    //http://localhost:8087/user/token/{token}
    @ResponseBody
    @RequestMapping(value = "/user/token/{token}",method = RequestMethod.GET)
    public Object getUserByToken(@PathVariable String token,String callback){
        DataResult result = userService.getUserByToken(token);
        if (StringUtils.isNotBlank(callback)) {//说明是jsonp请求
            return callback+"("+ JsonUtils.objectToJson(result)+")";
        }
        return result;
    }

    //http://localhost:8087/user/logout/{token}
    @ResponseBody
    @RequestMapping(value = "/user/logout/{token}",method = RequestMethod.GET)
    public DataResult logout(@PathVariable String token , ModelMap map){
        DataResult result = userService.logout(token);
        return result;
    }

}
