package com.seckill.support.rpc.impl;

import com.seckill.support.rpc.dto.ProductInfoDTO;
import com.seckill.support.rpc.dto.base.Result;
import com.seckill.support.rpc.service.ProductExportService;
import com.seckill.support.service.ProductService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

public class ProductExportServiceImpl implements ProductExportService {

    Logger logger = LogManager.getLogger(ProductExportServiceImpl.class);

    @Autowired
    ProductService productService;

    @Override
    public Result<Integer> createProduct(ProductInfoDTO productInfoDTO) {
        return null;
    }

    @Override
    public Result<ProductInfoDTO> queryProduct(String productId) {
        return productService.queryProduct(productId);
    }
}
