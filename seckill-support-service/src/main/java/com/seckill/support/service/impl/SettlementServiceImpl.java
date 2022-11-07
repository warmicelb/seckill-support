package com.seckill.support.service.impl;

import com.seckill.support.dao.mapper.ActivityMapper;
import com.seckill.support.dao.mapper.OrderRecordMapper;
import com.seckill.support.dao.mapper.ProductInfoMapper;
import com.seckill.support.dao.po.ActivityInfo;
import com.seckill.support.dao.po.OrderRecord;
import com.seckill.support.rpc.dto.SettlementDataDTO;
import com.seckill.support.rpc.dto.SettlementDataRequestDTO;
import com.seckill.support.rpc.dto.SettlementOrderDTO;
import com.seckill.support.service.SettlementService;
import com.seckill.support.service.tool.RedisTools;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Random;


@Service
public class SettlementServiceImpl implements SettlementService {

    Logger logger = LogManager.getLogger(SettlementServiceImpl.class);

    @Autowired
    OrderRecordMapper orderRecordMapper;

    @Autowired
    ActivityMapper activityMapper;

    @Autowired
    ProductInfoMapper productInfoMapper;

    @Autowired
    RedisTools redisTools;

    @Override
    public String submitOrder(SettlementOrderDTO orderDTO) {
        //1.校验商品标识

        //2.限购
        Long count = redisTools.evalsha("store_"+orderDTO.getProductId(),String.valueOf(orderDTO.getBuyNum()));
        logger.error(orderDTO.getUserId()+"限购结果："+count);
        if(count==null || count<=0){
            return null;
        }

        //3.下单-初始化
        Random random = new Random(10000);
        String orderId = String.valueOf(System.currentTimeMillis())+random.nextInt();
        OrderRecord orderRecord = new OrderRecord();

        ActivityInfo activityInfo = activityMapper.selectByProductId(orderDTO.getProductId());

        orderRecord.setOrderId(orderId);
        BigDecimal orderPrice = activityInfo.getActivityPrice().multiply(new BigDecimal(orderDTO.getBuyNum()));
        orderRecord.setOrderPrice(orderPrice);
        orderRecord.setOrderStatus(0);
        orderRecord.setAddress(orderDTO.getAddress());
        orderRecord.setPayType(orderDTO.getPayType());
        orderRecord.setProductId(orderDTO.getProductId());
        orderRecord.setUserId(orderDTO.getUserId());
        orderRecord.setOrderTime(new Date());
        orderRecord.setBuyNum(orderDTO.getBuyNum());

        orderRecordMapper.insert(orderRecord);

        //3.预占库存
        activityMapper.updateStockNum(activityInfo.getId(),orderDTO.getBuyNum());

        //4.更新订单-下单成功待支付
        orderRecordMapper.updateOrderStatus(orderId,1);

        return orderId;
    }

    @Override
    public SettlementDataDTO settlementData(SettlementDataRequestDTO requestDTO) {
        ActivityInfo activityInfo = activityMapper.selectByProductId(requestDTO.getProductId());

        SettlementDataDTO dataDTO = new SettlementDataDTO();

        dataDTO.setAssets("");
        dataDTO.setPayType(1);//在线支付
        dataDTO.setTotalPrice(activityInfo.getActivityPrice().multiply(new BigDecimal(requestDTO.getBuyNum())));
        dataDTO.setAddress("北京朝阳区");

        return dataDTO;
    }
}
