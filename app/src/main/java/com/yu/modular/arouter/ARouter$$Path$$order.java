package com.yu.modular.arouter;

import com.yu.arouter_annotation.model.RouterBean;
import com.yu.modular.order.Order_MainActivity;

import java.util.HashMap;
import java.util.Map;

public class ARouter$$Path$$order {

    public Map<String, Class> loadPath() {

        Map<String, Class> pathMap = new HashMap<>();

        pathMap.put("/order/Order_MainActivity", Order_MainActivity.class);

        return pathMap;
    }

}
