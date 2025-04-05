package com.easyz.zhfw.service.impl;

import com.easyz.zhfw.pojo.ServiceInfo;
import com.easyz.zhfw.service.WSSearchEngine;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WSSearchEngineImpl implements WSSearchEngine {

    private final DiscoveryClient discoveryClient;

    public WSSearchEngineImpl(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    @Override
    public List<ServiceInfo> search(String keywords) {
        List<ServiceInfo> serviceInfos = new ArrayList<>();
        discoveryClient.getServices().forEach(serviceId -> {
            if (serviceId.toLowerCase().contains(keywords.toLowerCase())) {
                discoveryClient.getInstances(serviceId).forEach(instance -> {
                    ServiceInfo serviceInfo = new ServiceInfo();
                    serviceInfo.setUrl(instance.getUri().toString());
                    serviceInfo.setDescription("Service Name: " + serviceId); // 使用 description 存储服务名称
                    serviceInfos.add(serviceInfo);
                });
            }
        });
        return serviceInfos;
    }
}
