package com.seckill.support.service;


import com.seckill.support.rpc.dto.SettlementDataDTO;
import com.seckill.support.rpc.dto.SettlementDataRequestDTO;
import com.seckill.support.rpc.dto.SettlementOrderDTO;

public interface SettlementService {

    /**
     * 下单
     * @param orderDTO
     * @return 订单号
     */
    String submitOrder(SettlementOrderDTO orderDTO);


    SettlementDataDTO settlementData(SettlementDataRequestDTO requestDTO);
}
