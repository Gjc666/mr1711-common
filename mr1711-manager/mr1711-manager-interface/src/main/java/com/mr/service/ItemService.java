package com.mr.service;

import com.mr.model.TbItem;

import java.util.Map;


/**
 * Created by ydd on 2018/5/14.
 */
public interface ItemService {

    /**
     * 通过id查询数据
     * @param id
     * @return
     */
    TbItem getItemById(Long id);

    /**
     * 查询list
     * @param page
     * @param rows
     * @return
     */
    Map<String, Object> getList(Integer page , Integer rows);
}
