package com.easyz.zhfw.controller.v1;

import com.easyz.zhfw.service.TravelAgencyWS;
import com.easyz.zhfw.vo.TravelAgencyRequestType;
import com.easyz.zhfw.vo.TravelAgencyResponseType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@RestController
@RequestMapping("/")
@Validated
public class TravelAgencyController {

    @Autowired
    private TravelAgencyWS travelAgencyWS;

    @GetMapping("/request")
    public ResponseEntity<TravelAgencyResponseType> handleTravelAgencyRequest(
            @RequestParam @NotBlank String name,
            @RequestParam @NotBlank String address,
            @RequestParam @NotBlank String departureCity,
            @RequestParam @NotBlank String destinationCity,
            @RequestParam @NotNull String departureDate,
            @RequestParam @NotNull String returnDate
    ) {
        try {
            // 创建 TravelAgencyRequestType 对象
            TravelAgencyRequestType request = new TravelAgencyRequestType();
            request.setName(name);
            request.setAddress(address);
            request.setDepartureCity(departureCity);
            request.setDestinationCity(destinationCity);
            request.setDepartureDate(new SimpleDateFormat("yyyy-MM-dd").parse(departureDate));
            request.setReturnDate(new SimpleDateFormat("yyyy-MM-dd").parse(returnDate));

            // 调用服务层方法处理请求
            TravelAgencyResponseType response = travelAgencyWS.travelAgencyRequest(request);
            if (response.isSuccessful()) {
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            }
        } catch (ParseException e) {
            // 处理日期解析异常
            TravelAgencyResponseType errorResponse = new TravelAgencyResponseType();
            errorResponse.setSuccessful(false);
            errorResponse.setMessage("Invalid date format. Please use yyyy-MM-dd.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (Exception e) {
            // 处理其他异常情况
            TravelAgencyResponseType errorResponse = new TravelAgencyResponseType();
            errorResponse.setSuccessful(false);
            errorResponse.setMessage("An error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
