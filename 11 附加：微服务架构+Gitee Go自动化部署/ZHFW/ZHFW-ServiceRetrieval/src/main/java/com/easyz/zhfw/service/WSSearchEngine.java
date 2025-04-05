package com.easyz.zhfw.service;

import com.easyz.zhfw.pojo.ServiceInfo;

import java.util.List;

public interface WSSearchEngine {

	public List<ServiceInfo> search(String keywords);
}
