package com.yu.common.order;


/**
 * 订单模块对外暴露接口，其他模块可以获取返回res资源
 * 具体的实现在Order模块中
 */
public interface OrderDrawable {
    int getDrawable();
}
