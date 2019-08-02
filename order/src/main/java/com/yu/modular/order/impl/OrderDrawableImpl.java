package com.yu.modular.order.impl;

import com.yu.common.order.OrderDrawable;
import com.yu.modular.order.R;
import com.yu.router_annotation.Router;

@Router(group = "order", path = "OrderDrawable")
public class OrderDrawableImpl implements OrderDrawable {
    @Override
    public int getDrawable() {
        return R.mipmap.order_wtf;
    }
}
