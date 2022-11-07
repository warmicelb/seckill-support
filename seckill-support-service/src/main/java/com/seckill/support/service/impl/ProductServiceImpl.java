package com.seckill.support.service.impl;


import com.seckill.support.dao.mapper.ProductInfoMapper;
import com.seckill.support.dao.po.ProductInfo;
import com.seckill.support.rpc.dto.ProductInfoDTO;
import com.seckill.support.rpc.dto.base.Result;
import com.seckill.support.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductInfoMapper productInfoMapper;

    @Override
    public Result<Integer> createProduct(ProductInfoDTO productInfoDTO) {
        ProductInfo productInfo = new ProductInfo();
        BeanUtils.copyProperties(productInfoDTO,productInfo);
        int count =  productInfoMapper.insert(productInfo);
        BeanUtils.copyProperties(productInfoDTO,productInfo);
        return new Result<>(count);
    }

    @Override
    public Result<ProductInfoDTO> queryProduct(String productId) {
        ProductInfo productInfo = productInfoMapper.selectByProductId(productId);
        ProductInfoDTO infoDTO = new ProductInfoDTO();
        BeanUtils.copyProperties(productInfo,infoDTO);
        return new Result<>(infoDTO);
    }
}
