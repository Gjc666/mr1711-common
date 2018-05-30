package com.mr.content.service;

import com.mr.model.Ad1;

import java.util.List;

/**
 * Created by ydd on 2018/5/15.
 */
public interface ContentService {

    /**
     * 通过类型id查询内容
     * @param cid
     * @return
     */
    List<Ad1> getContentByCid(Long cid);


}
