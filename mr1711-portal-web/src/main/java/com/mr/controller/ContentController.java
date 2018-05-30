package com.mr.controller;

import com.mr.content.service.ContentService;
import com.mr.model.Ad1;
import com.mr.util.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by ydd on 2018/5/15.
 */
@Controller
public class ContentController {

    @Autowired
    private ContentService contentService;

    @Value("${AD1_CID}")
    private Long cid;

    @RequestMapping("/index")
    public String toIndexPage(ModelMap map){

        List<Ad1> list =  contentService.getContentByCid(cid);
        String ad1Json = JsonUtils.objectToJson(list);

        map.put("ad1",ad1Json);

        return "index";
    }

}
