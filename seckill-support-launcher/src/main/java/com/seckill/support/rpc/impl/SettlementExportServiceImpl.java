package com.seckill.support.rpc.impl;


import com.seckill.support.dao.mapper.OrderRecordMapper;
import com.seckill.support.dao.po.OrderRecord;
import com.seckill.support.rpc.dto.SettlementDataDTO;
import com.seckill.support.rpc.dto.SettlementDataRequestDTO;
import com.seckill.support.rpc.dto.SettlementOrderDTO;
import com.seckill.support.rpc.dto.base.Result;
import com.seckill.support.rpc.service.SettlementExportService;
import com.seckill.support.service.ActivityService;
import com.seckill.support.service.SettlementService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

public class SettlementExportServiceImpl implements SettlementExportService {

    Logger logger = LogManager.getLogger(SettlementExportServiceImpl.class);

    @Autowired
    ActivityService activityService;

    @Autowired
    SettlementService settlementService;

    @Autowired
    OrderRecordMapper orderRecordMapper;

    @Override
    public Result<SettlementDataDTO> settlementData(SettlementDataRequestDTO requestDTO) {
        SettlementDataDTO dataDTO = settlementService.settlementData(requestDTO);
        return new Result<>(dataDTO);
    }

    @Override
    public Result<String> submitOrder(SettlementOrderDTO orderDTO) {
        try{
            String orderId = settlementService.submitOrder(orderDTO);

            return new Result<>(orderId);
        }catch (Exception e){
            logger.error("提单异常",e);
        }
        return new Result<>(null);
    }

    @Override
    public Result<String> getPayPageUrl(String orderId) {
        OrderRecord orderRecord = orderRecordMapper.selectByOrderId(orderId);
        String payPageUrl = "http://localhost:8080/mock/payPage?orderId="+orderId+"&orderPrice="+orderRecord.getOrderPrice().toPlainString();
        return new Result<>(payPageUrl);
    }
}
