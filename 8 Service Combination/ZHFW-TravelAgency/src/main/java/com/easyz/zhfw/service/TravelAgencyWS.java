package com.easyz.zhfw.service;

import com.easyz.zhfw.vo.TravelAgencyRequestType;
import com.easyz.zhfw.vo.TravelAgencyResponseType;

public interface TravelAgencyWS {
    TravelAgencyResponseType travelAgencyRequest(TravelAgencyRequestType request);
}
