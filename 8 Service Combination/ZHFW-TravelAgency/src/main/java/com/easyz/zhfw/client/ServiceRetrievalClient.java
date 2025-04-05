package com.easyz.zhfw.client;

import com.easyz.zhfw.pojo.ServiceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class ServiceRetrievalClient {

    private static final String BASE_URL = "http://zhfw-serviceretrieval:8088";

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 搜索服务信息
     *
     * @param keywords 关键词
     * @return 服务信息列表
     */
    public List<ServiceInfo> search(String keywords) {
        ParameterizedTypeReference<List<ServiceInfo>> typeRef = new ParameterizedTypeReference<List<ServiceInfo>>() {
        };
        ResponseEntity<List<ServiceInfo>> response = restTemplate.exchange(
                BASE_URL + "/search?keywords={keywords}",
                HttpMethod.GET,
                null,
                typeRef,
                keywords
        );
        return response.getBody();
    }
}
