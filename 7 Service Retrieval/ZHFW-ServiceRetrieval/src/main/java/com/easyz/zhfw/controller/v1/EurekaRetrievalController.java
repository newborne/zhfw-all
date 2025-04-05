package com.easyz.zhfw.controller.v1;



import com.easyz.zhfw.pojo.ServiceInfo;
import com.easyz.zhfw.service.WSSearchEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
public class EurekaRetrievalController {

    private final WSSearchEngine searchEngine;

    @Autowired
    public EurekaRetrievalController(WSSearchEngine searchEngine) {
        this.searchEngine = searchEngine;
    }

    @GetMapping("/search")
    public List<ServiceInfo> search(@RequestParam String keywords) {
        return searchEngine.search(keywords);
    }
}
