package com.mr.content.service.impl;

import com.mr.content.service.ContentService;
import com.mr.mapper.TbContentMapper;
import com.mr.model.Ad1;
import com.mr.model.TbContent;
import com.mr.model.TbContentExample;
import com.mr.redis.JedisClient;
import com.mr.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ydd on 2018/5/15.
 */
@Service
@Transactional(readOnly = true)
public class ContentServiceImpl implements ContentService {

    @Autowired
    private JedisClient jedisClient;

    @Autowired
    private TbContentMapper contentMapper;

    @Value("${HEIGHT}")
    private Integer HEIGHT;
    @Value("${HEIGHTB}")
    private Integer HEIGHTB;
    @Value("${WIDTH}")
    private Integer WIDTH;
    @Value("${WIDTHB}")
    private Integer WIDTHB;

    @Value("${REDISCIDKEY}")
    private String REDISCIDKEY;


    /**
     * 通过cid查询数据
     * @param cid
     * @return
     */
    @Override
    public List<Ad1> getContentByCid(Long cid) {
        List<TbContent> contents = null;
        //查询缓存中是否有数据
        Boolean b = jedisClient.exists(REDISCIDKEY + cid);
        if(b){
            String contentsJson = jedisClient.get(REDISCIDKEY + cid);
            contents = JsonUtils.jsonToList(contentsJson, TbContent.class);
         //如果有数据直接返回
        }else{
            //如果没有，查询数据库
            TbContentExample example = new TbContentExample();
            TbContentExample.Criteria criteria = example.createCriteria();
            criteria.andCategoryIdEqualTo(cid);
            //通过cid查询到的数据
            contents = contentMapper.selectByExample(example);

            //查询到数据之后，将数据存放在缓存中，
            jedisClient.set(REDISCIDKEY+cid, JsonUtils.objectToJson(contents));
        }
        //定义需要返回的数据
        List<Ad1> list = new ArrayList<>();
        //循环，将数据存放在list中
        for(TbContent content : contents){
            Ad1 ad1 = new Ad1();
            ad1.setAlt(content.getTitle());
            ad1.setHref(content.getUrl());
            ad1.setSrc(content.getPic());
            ad1.setSrcB(content.getPic2());
            ad1.setHeight(HEIGHT);
            ad1.setHeightB(HEIGHTB);
            ad1.setWidth(WIDTH);
            ad1.setWidthB(WIDTHB);
            list.add(ad1);
        }




        return list;
    }
}
