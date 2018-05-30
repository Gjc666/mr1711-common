package com.mr.sso.service.impl;

import com.mr.mapper.TbUserMapper;
import com.mr.model.TbUser;
import com.mr.model.TbUserExample;
import com.mr.redis.JedisClient;
import com.mr.sso.service.UserService;
import com.mr.util.DataResult;
import com.mr.util.JsonUtils;
import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by ydd on 2018/5/16.
 */
@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {

    @Autowired
    private TbUserMapper userMapper;
    @Autowired
    private JedisClient jedisClient;

    @Value("${USERTOKEN}")
    private String USERTOKEN;
    @Value("${EXPIRETIME}")
    private Integer EXPIRETIME;

    /**
     * 验证参数是否可用
     * @return
     */
    @Override
    public DataResult checkUser(String param , Integer type) {

        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        //1、2、3分别代表username、phone、email
        if(type == 1 ){
            criteria.andUsernameEqualTo(param);
        }else if(type == 2){
            criteria.andPhoneEqualTo(param);
        }else if(type == 3){
            criteria.andEmailEqualTo(param);
        } else{
            //参数不正确
            return DataResult.build(404 , "参数不规范！");
        }
        List<TbUser> tbUsers = userMapper.selectByExample(example);
        //true：数据可用，false：数据不可用
        if(tbUsers.size() == 0){//说明可用
            return  DataResult.ok(true);
        }
        return DataResult.ok(false);
    }

    /**
     * 注册
     * @param user
     * @return
     */
    @Transactional
    @Override
    public DataResult register(TbUser user) {
        //1：判断用户名是否可用
        if(StringUtils.isNotBlank(user.getUsername())){
            DataResult result = checkUser(user.getUsername(), 1);
            if(!(boolean) result.getData()){//false不可用
                return DataResult.build(400 ,"用户名不可用！");
            }
        }
        //2：判断邮箱是否可用
        if (StringUtils.isNotBlank(user.getEmail())) {
            DataResult result = checkUser(user.getEmail(), 3);
            if(!(boolean) result.getData()){//false不可用
                return DataResult.build(400 ,"邮箱不可用！");
            }
        }

        //3：判断手机是否可用
        if (StringUtils.isNotBlank(user.getPhone())) {
            DataResult result = checkUser(user.getPhone(), 2);
            if(!(boolean) result.getData()){//false不可用
                return DataResult.build(400 ,"手机号不可用！");
            }
        }
        try {
            user.setCreated(new Date());
            user.setUpdated(new Date());
            //spring包中自带的有MD5加密
            String md5Password = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
            user.setPassword(md5Password);
            userMapper.insert(user);
        }catch (Exception e){
            return DataResult.build(400,"注册失败. 请校验数据后请再提交数据.");
        }
        return DataResult.ok();
    }


    @Override
    public DataResult login(String username, String password) {
        /*
            1：通过用户名查询数据
            2：如果数据不存在，直接返回
            3：如果存在，验证用户密码是否正确
            4：如果密码错误。直接返回
            5：如果密码正确。
            6：生产token
            7：将数据存放在redis中，USER_TOKEN_
            8：设置redis中token的过期时间，30分钟
            9：将token返回
         */
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        List<TbUser> users = userMapper.selectByExample(example);
        if(users == null || users.size() == 0){//没有数据
            return DataResult.build(400,"用户名或密码错误！");
        }
        //加密之后的密码
        String md5Password = DigestUtils.md5DigestAsHex(password.getBytes());
        TbUser user = users.get(0);
        if(!user.getPassword().equals(md5Password)){
            return DataResult.build(400,"用户名或密码错误！");
        }

        //生产token
        String token = UUID.randomUUID().toString();
        //将密码设置为null
        user.setPassword(null);

        jedisClient.set(USERTOKEN + token , JsonUtils.objectToJson(user));
        jedisClient.expire(USERTOKEN + token ,EXPIRETIME );
        return DataResult.ok(token);
    }

    @Override
    public DataResult getUserByToken(String token) {
        if(jedisClient.exists(USERTOKEN + token)){
            String json = jedisClient.get(USERTOKEN + token);
            return DataResult.ok(JsonUtils.jsonToPojo(json, TbUser.class));
        }
        return DataResult.build(400,"token不存在！");
    }

    @Override
    public DataResult logout(String token) {
        if(jedisClient.exists(USERTOKEN + token)){
            jedisClient.del(USERTOKEN + token);
            return DataResult.ok();
        }
        return DataResult.build(400,"token不存在！");
    }


}
