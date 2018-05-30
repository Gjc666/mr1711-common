package com.mr.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mr.mapper.TbItemMapper;
import com.mr.model.TbItem;
import com.mr.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ydd on 2018/5/14.
 */
@Service
@Transactional(readOnly = true)
public class ItemServiceImpl implements ItemService {

    @Autowired
    private TbItemMapper itemMapper;

    @Override
    public TbItem getItemById(Long id) {
        TbItem tbItem = itemMapper.selectByPrimaryKey(id);
        return tbItem;
    }

    @Override
    public Map<String, Object> getList(Integer page, Integer rows) {
        // 当前页和每页条数
        PageHelper.startPage(page , rows);
        List<TbItem> tbItems = itemMapper.selectByExample(null);
        PageInfo info = new PageInfo(tbItems);
        Map<String , Object> map = new HashMap<String , Object>();
        //rows：list数据
        map.put("rows",info.getList());
        map.put("total",info.getTotal());
        return map;
    }
}
