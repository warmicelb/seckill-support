package com.seckill.support.rpc.impl;


import com.seckill.support.common.exception.BizException;
import com.seckill.support.dao.po.ActivityInfo;
import com.seckill.support.rpc.constant.ResultCodeConstant;
import com.seckill.support.rpc.dto.SeckillActivityDTO;
import com.seckill.support.rpc.dto.base.Result;
import com.seckill.support.rpc.service.ActivityExportService;
import com.seckill.support.service.ActivityService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class ActivityExportServiceImpl implements ActivityExportService {

    Logger logger = LogManager.getLogger(ActivityExportServiceImpl.class);

    @Autowired
    ActivityService activityService;


    @Override
    public Result<Integer> queryStore(String productId) {
        try{
            Integer count = activityService.queryStore(productId);
            return new Result<>(count);
        }catch (Exception e){
            logger.error("发生异常了",e);
        }
        return new Result<>(ResultCodeConstant.SYSTEM_ERROR,"系统异常",null);
    }

    @Override
    public Result<Integer> createActivity(SeckillActivityDTO activityDTO) {
        try{
            ActivityInfo activityInfo = new ActivityInfo();

            BeanUtils.copyProperties(activityDTO,activityInfo);

            int count = activityService.createActivity(activityInfo);

            return new Result<>(count);
        }catch (BizException e){
            logger.error("发生异常了",e);
            return new Result<>(ResultCodeConstant.SYSTEM_ERROR,e.getMessage(),null);
        }catch (Exception e){
            logger.error("发生异常了",e);
        }
        return new Result<>(ResultCodeConstant.SYSTEM_ERROR,"系统异常",null);
    }

    @Override
    public Result<SeckillActivityDTO> queryActivity(String productId) {
        ActivityInfo activityInfo = activityService.queryActivityById(productId);

        SeckillActivityDTO activityDTO = new SeckillActivityDTO();

        BeanUtils.copyProperties(activityInfo,activityDTO);

        return new Result<>(activityDTO);
    }

    @Override
    public Result<SeckillActivityDTO> queryActivityByCondition(String productId, Integer status) {
        ActivityInfo activityInfo = activityService.queryActivityByCondition(productId,status);
        if(activityInfo == null){
            return new Result<>(null);
        }

        SeckillActivityDTO activityDTO = new SeckillActivityDTO();

        BeanUtils.copyProperties(activityInfo,activityDTO);

        return new Result<>(activityDTO);
    }

    @Override
    public Result<Integer> startActivity(String productId) {
        Integer count = 0;
        try{
           count = activityService.startActivity(productId);
        }catch (BizException e){
            return new Result<>(ResultCodeConstant.SYSTEM_ERROR,e.getErrorCode(),count);
        }catch (Exception e){
            return new Result<>(ResultCodeConstant.SYSTEM_ERROR,"系统异常",null);
        }
        return new Result<>(count);
    }

    @Override
    public Result<Integer> endActivity(String productId) {
        Integer count = 0;
        try{
            count = activityService.endActivity(productId);
        }catch (BizException e){
            return new Result<>(ResultCodeConstant.SYSTEM_ERROR,e.getErrorCode(),count);
        }catch (Exception e){
            return new Result<>(ResultCodeConstant.SYSTEM_ERROR,"系统异常",null);
        }
        return new Result<>(count);
    }
}
