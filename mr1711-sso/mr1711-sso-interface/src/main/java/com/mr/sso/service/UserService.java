package com.mr.sso.service;

import com.mr.model.TbUser;
import com.mr.util.DataResult;

/**
 * Created by ydd on 2018/5/16.
 */
public interface UserService {

    /**
     * 验证数据是否可用
     * @param param
     * @param type
     * @return
     */
    DataResult checkUser(String param , Integer type);

    /**
     * 注册
     * @param user
     * @return
     */
    DataResult register(TbUser user);

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    DataResult login(String username , String password);


    /**
     * 通过token查询用户信息
     * @param token
     * @return
     */
    DataResult getUserByToken(String token);

    /**
     * 登出
     * @param token
     * @return
     */
    DataResult logout(String token);
}
