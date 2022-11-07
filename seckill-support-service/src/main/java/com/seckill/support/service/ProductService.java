package com.seckill.support.service;


import com.seckill.support.rpc.dto.ProductInfoDTO;
import com.seckill.support.rpc.dto.base.Result;

public interface ProductService {

    /**
     * 创建商品
     * @param productInfoDTO
     * @return
     */
    Result<Integer> createProduct(ProductInfoDTO productInfoDTO);


    /**
     * 查询商品
     * @param productId
     * @return
     */
    Result<ProductInfoDTO> queryProduct(String productId);

}
