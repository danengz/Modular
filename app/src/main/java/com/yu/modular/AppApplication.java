package com.yu.modular;

import com.yu.common.BaseApplication;
import com.yu.common.PathManager;
import com.yu.modular.order.Order_MainActivity;
import com.yu.modular.shoppingcar.ShoppingCar_MainActivity;

public class AppApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();

        PathManager.set("app", "MainActivity", MainActivity.class);
        PathManager.set("shoppingcar", "ShoppingCar_MainActivity", ShoppingCar_MainActivity.class);
        PathManager.set("order", "Order_MainActivity", Order_MainActivity.class);
    }
}
