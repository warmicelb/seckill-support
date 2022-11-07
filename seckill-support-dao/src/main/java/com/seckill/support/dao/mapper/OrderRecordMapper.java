package com.seckill.support.dao.mapper;

import com.seckill.support.dao.po.OrderRecord;
import org.apache.ibatis.annotations.Param;

public interface OrderRecordMapper {

    /**
     * 创建订单
     * @param orderRecord
     * @return
     */
    int insert(OrderRecord orderRecord);

    /**
     * 查询活动
     * @param
     * @return
     */
    OrderRecord selectByOrderId(String orderId);

    /**
     * 更新状态
     * @param
     * @return
     */
    int updateOrderStatus(@Param("orderId") String orderId,@Param("orderStatus") Integer orderStatus);

}
