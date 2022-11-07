package com.seckill.support.service;


import com.seckill.support.common.exception.BizException;
import com.seckill.support.dao.po.ActivityInfo;

public interface ActivityService {
    /**
     * 创建活动
     * @param activityInfo
     * @return
     */
    int createActivity(ActivityInfo activityInfo) throws BizException;

    /**
     * 查询活动
     * @param productId
     * @return
     */
    ActivityInfo queryActivityById(String productId);

    /**
     * 查询活动
     * @param productId
     * @return
     */
    ActivityInfo queryActivityByCondition(String productId,Integer status);

    /**
     * 活动开始
     * @param productId
     * @return
     */
    Integer startActivity(String productId) throws BizException;

    /**
     * 活动关闭
     * @param productId
     * @return
     */
    Integer endActivity(String productId) throws BizException;

    /**
     * 活动库存查询
     * @param productId
     * @return
     */
    Integer queryStore(String productId);
}
